import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParseCache {
		//read in as string
		String file= "dataparser/JSONdata.json";
		String json= readFileAsString(file);
		
		//get data --> Java Object
		Gson gson= new Gson();
		DataUnit dataUnit = gson.fromJson(json,DataUnit.class);
		
	
	private String readFileAsString(String file) throws IOException {
		return new String (Files.readAllBytes(Paths.get(file)));
	}

}
