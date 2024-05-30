package basilisk.user.servlet.parsing;

import com.google.gson.Gson;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class JsonParseCache {

    private static final Map<String, DataUnit> idToDataMap = new ConcurrentHashMap<>();
    private static final Map<String, List<DataUnit>> tagToDataMap = new ConcurrentHashMap<>();
    private static final Map<String, List<DataUnit>> roleToDataMap = new ConcurrentHashMap<>();

    @Setter
    static String path;
    @Setter
    static boolean isEncryptionModeEnabled;

    public static void parseFiles() {
        //read in as string
        System.out.println("Parsing files in storage for user data");

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Could not detect local storage path");
        }
        Path folderPath = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + path);

        if (!Files.exists(folderPath)) {
            folderPath.toFile().mkdirs();
        }

        List<DataUnit> dataFromFiles = new ArrayList<>();
        Gson gson = new Gson();
        System.out.println("Fetching files in: " + folderPath);

        try (Stream<Path> dataFiles = Files.list(folderPath)) {
            dataFiles.forEach(f -> {
                try (BufferedReader reader = Files.newBufferedReader(f)) {
                    if (isEncryptionModeEnabled) {
                        dataFromFiles.add(DataUnit.fromEncryptedString(reader.readLine()));
                    } else {
                        dataFromFiles.add(gson.fromJson(reader, DataUnit.class));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Could not parse file: " + f.getFileName());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Could not parse data");
        }

        dataFromFiles.parallelStream().forEach(JsonParseCache::insertData);
        System.out.println("Successfully cached user data");
    }

    public static DataUnit getById(String id) {
        return idToDataMap.getOrDefault(id, new DataUnit());
    }

    public static List<DataUnit> getByTag(String tag) {
        return tagToDataMap.getOrDefault(tag, new ArrayList<>());
    }

    public static List<DataUnit> getByRole(String role) {
        return roleToDataMap.getOrDefault(role, new ArrayList<>());
    }

    public static void insertData(DataUnit dataUnit) {
        if (idToDataMap.containsKey(dataUnit.getId()) && idToDataMap.get(dataUnit.getId()).equals(dataUnit)) {
            return;
        }

        idToDataMap.put(dataUnit.getId(), dataUnit);

        for (String tag : dataUnit.getTags()) {
            tagToDataMap.computeIfAbsent(tag, s -> Collections.synchronizedList(new ArrayList<>())).add(dataUnit);
        }

        for (String role : dataUnit.getRoles()) {
            roleToDataMap.computeIfAbsent(role, s -> Collections.synchronizedList(new ArrayList<>())).add(dataUnit);
        }
    }

    public static void writeToFiles() {
        Path folderPath = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + path);

        if (!Files.exists(folderPath)) {
            throw new RuntimeException("Storage path: " + folderPath + " is not a folder/does not exist");
        }

        // delete existing data
        File[] currFiles = folderPath.toFile().listFiles();
        if (currFiles != null && currFiles.length > 0) {
            Arrays.stream(currFiles).forEach(File::delete);
        }

        for (DataUnit unit : idToDataMap.values()) {
            System.out.println("Storing to: " + folderPath + unit.getId());
            try (FileWriter unitFile = new FileWriter(new File(folderPath.toFile(), unit.getId()))) {
                unitFile.write(isEncryptionModeEnabled ? unit.toEncryptedString() : unit.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Completed Persistence of Data");
    }
}