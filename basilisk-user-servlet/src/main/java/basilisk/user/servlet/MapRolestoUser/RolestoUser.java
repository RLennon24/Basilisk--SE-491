package basilisk.user.servlet.MapRolestoUser;

import basilisk.user.servlet.parsing.DataUnit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
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

    private static final Map<String , List<String>> RoletoUser= new ConcurrentHashMap<>();
    String User= "Maheen";

    //check if list has roles
    public boolean doesUserHaveRole(String user, String role) throws IOException {
        List <String> listUsers= RoletoUser.get(role);
        if(listUsers==null){
            System.out.println("Role not found");
            return false;
        }
        if(listUsers.contains(user)){
            System.out.println("User found with role");
                return true;
        }else{
            System.out.println("User does not have role");
            return false;
        }
    }

    public void RoletoUser(DataUnit dataUnit) {
            //get roles data assign to user
            for (String role : dataUnit.getRoles()) {
                RoletoUser.computeIfAbsent(role, s -> Collections.synchronizedList(new ArrayList<>())).add(User);
            }
    }

    public static void writeFiles(){
        //map to gson
        Gson gson= new Gson();
        Type typeObject= new TypeToken<Map>(){}.getType();
        String gsonData = gson.toJson(RoletoUser,typeObject);

        //save to file
        Path folderPath= Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + "RolestoUserJSON");
        try (FileWriter jsonFile= new FileWriter((folderPath.toFile()))){
            jsonFile.write(gsonData);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}






