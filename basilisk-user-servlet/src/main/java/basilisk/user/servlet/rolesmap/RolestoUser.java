package basilisk.user.servlet.rolesmap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RolestoUser {

    private static Map<String, List<String>> RoletoUser = new ConcurrentHashMap<>();

    //check if user has roles
    public static boolean doesUserHaveRole(String User, String role) throws IOException {
        List<String> listUsers = RoletoUser.get(role);
        if (listUsers == null) {
            System.out.println("Role not found");
            return false;
        }
        if (listUsers.contains(User)) {
            System.out.println("User found with role");
            return true;
        } else {
            System.out.println("User does not have role");
            return false;
        }
    }

    //insert data
    public static void giveUserRole(String User, String role) {
        RoletoUser.computeIfAbsent(role, s -> Collections.synchronizedList(new ArrayList<>()));
        RoletoUser.get(role).add(User);

    }

    public static void writeFiles() {
        //map to gson
        Gson gson = new Gson();
        Type typeObject = new TypeToken<Map>() {
        }.getType();
        String gsonData = gson.toJson(RoletoUser, typeObject);
        Path folderPath = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + "RolestoUser.json");

        //save to file
        try (FileWriter jsonFile = new FileWriter((folderPath.toFile()))) {
            jsonFile.write(gsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read from file
    public static void readFromFiles() {
        Path folderPath = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + "RolestoUser.json");

        System.out.println("Fetching Roles/User files in: " + folderPath.toAbsolutePath());

        Map<String, List<String>> rolesFromFiles = new ConcurrentHashMap<>();
        Gson gson = new Gson();
        try {
            RoletoUser = gson.fromJson(new FileReader(folderPath.toFile()), new TypeToken<Map<String, Object>>() {
            }.getType());
            System.out.println("Read roles/user data from file");
        } catch (IOException e) {
            throw new RuntimeException("Could not find data at: " + folderPath.toAbsolutePath());
        }

    }
}






