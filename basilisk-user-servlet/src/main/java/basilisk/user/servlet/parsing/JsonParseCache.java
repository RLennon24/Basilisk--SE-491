package basilisk.user.servlet.parsing;

import com.google.gson.Gson;
import lombok.Setter;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JsonParseCache {

    private static final Map<String, DataUnit> idToDataMap = new HashMap<>();
    private static final Map<String, List<DataUnit>> tagToDataMap = new HashMap<>();
    private static final Map<String, List<DataUnit>> roleToDataMap = new HashMap<>();

    @Setter
    static String path;

    public static void parseFiles() {
        //read in as string
        System.out.println("Parsing files in storage for user data");

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Could not detect local storage path");
        }

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Gson gson = new Gson();
        List<DataUnit> dataFromFiles = new ArrayList<>();
        try (Stream<Path> dataFiles = Files.list(Paths.get(classLoader.getResource(path).toURI()))) {
            dataFiles.forEach(f -> {
                try (BufferedReader reader = Files.newBufferedReader(f)) {
                    DataUnit dataUnit = gson.fromJson(reader, DataUnit.class);
                    dataFromFiles.add(dataUnit);
                } catch (Exception e) {
                    throw new RuntimeException("Could not parse data");
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Could not parse data");
        }

        for (DataUnit dataUnit : dataFromFiles) {
            idToDataMap.put(dataUnit.getId(), dataUnit);

            for (String tag : dataUnit.getTags()) {
                if (tagToDataMap.containsKey(tag)) {
                    tagToDataMap.get(tag).add(dataUnit);
                } else {
                    List<DataUnit> dataUnits = new ArrayList<>();
                    dataUnits.add(dataUnit);
                    tagToDataMap.put(tag, dataUnits);
                }
            }

            for (String role : dataUnit.getRoles()) {
                if (roleToDataMap.containsKey(role)) {
                    roleToDataMap.get(role).add(dataUnit);
                } else {
                    List<DataUnit> dataUnits = new ArrayList<>();
                    dataUnits.add(dataUnit);
                    roleToDataMap.put(role, dataUnits);
                }
            }
        }
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
}