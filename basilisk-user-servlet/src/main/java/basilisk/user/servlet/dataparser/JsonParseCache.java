import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParseCache {
	public void ParseJSON(){
		//read in as string
		try{
			BufferedReader reader= Files.newBufferedReader(Paths.get("basilisk-user-servlet/src/main/java/basilisk/user/servlet/dataparser/DataUnit.java"));
		
		//get data --> Java Object
		Gson gson= new Gson();
		DataUnit dataUnit = gson.fromJson(reader,DataUnit.class);
		
		}catch (Exception e) {
			e.printStackTrace();
	}
		

	}
}
