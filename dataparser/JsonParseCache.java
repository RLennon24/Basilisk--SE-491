import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonParseCache {
		//read in as string
		String file= "dataparser/JSONdata.json";
		String json= readFileAsString(file);
		
		//get data --> Java Object
		Gson gson= new Gson();
		DataUnit DataUnit = gson.fromJson(json,DataUnit.getClass());
		
	
	private static String readFileAsString(String file) throws IOException {
		return new String (Files.readAllBytes(Paths.get(file)));
	}

}