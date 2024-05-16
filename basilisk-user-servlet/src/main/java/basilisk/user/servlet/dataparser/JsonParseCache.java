import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParseCache {
	public void ParseJSON(){
		//read in as string
		try{
			Gson gson= new Gson();
			BufferedReader reader= Files.newBufferedReader(Paths.get("basilisk-user-servlet/src/main/java/basilisk/user/servlet/dataparser/JSONdata.json"));
			
		//get data --> Java Object --> store in map
		Map<String, JsonParseCache.DataUnit> map= new HashMap<String,DataUnit>();
		DataUnit dataUnit = gson.fromJson(reader,DataUnit.class);
		map= (Map<String,DataUnit>)gson.fromJson(reader, DataUnit.class);
		
		}catch (Exception e) {
			e.printStackTrace();
	}
		

	}
}
