package basilisk.user.servlet.parsing;

import com.google.gson.Gson;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
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

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<DataUnit> dataFromFiles = new ArrayList<>();
        Gson gson = new Gson();

        try (Stream<Path> dataFiles = Files.list(Paths.get(classLoader.getResource(path).toURI()))) {
            dataFiles.forEach(f -> {
                try (BufferedReader reader = Files.newBufferedReader(f)) {
                    if (isEncryptionModeEnabled) {
                        dataFromFiles.add(DataUnit.fromEncryptedString(reader.readLine()));
                    } else {
                        dataFromFiles.add(gson.fromJson(reader, DataUnit.class));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Could not parse data");
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Could not parse data");
        }

        dataFromFiles.parallelStream().forEach(JsonParseCache::insertData);
        System.out.println("Successfully cached user data");
    }

    public static DataUnit getById(String id) {
        return idToDataMap.get(id);
    }

    public static List<DataUnit> getByTag(String tag) {
        return tagToDataMap.get(tag);
    }

    public static List<DataUnit> getByRole(String role) {
        return roleToDataMap.get(role);
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
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File folder = null;
        try {
            folder = new File(classLoader.getResource(path).toURI());

            if (!folder.exists() || !folder.isDirectory()) {
                throw new RuntimeException("Storage path: " + folder.getPath() + " is not a folder/does not exist");
            }

            // delete existing data
            File[] currFiles = folder.listFiles();
            if (currFiles != null && currFiles.length > 0) {
                Arrays.stream(currFiles).forEach(File::delete);
            }

            for (DataUnit unit : idToDataMap.values()) {
                System.out.println("Storing to: " + folder.getPath() + unit.getId());
                try (FileWriter unitFile = new FileWriter(new File(folder.getPath(), unit.getId()))) {
                    unitFile.write(isEncryptionModeEnabled ? unit.toEncryptedString() : unit.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Completed Persistence of Data");
    }
}