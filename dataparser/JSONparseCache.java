import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JSONparseCache {
	
	class DataUnit{
		private String id;
		private String data;
		private int ip;
		private ArrayList<DataUnit> tags;
		private ArrayList<DataUnit> roles;
		
		
		public String getId() {return id;}
		public String getData() {return data;}
		public int getIp() {return ip;}
		public ArrayList <DataUnit> getTags() {return tags;}
		public ArrayList <DataUnit> getRoles(){return roles;}
		
		
	}

	public static void main(String[] args) throws IOException {
		//read in as string
		String file= "dataparser/JSONdata.json";
		String json= readFileAsString(file);
		
		//get data --> Java Object
		Gson gson= new Gson();
		DataUnit DataUnit = gson.fromJson(json,DataUnit.getClass());
		
		
	}

	private static String readFileAsString(String file) throws IOException {
		return new String (Files.readAllBytes(Paths.get(file)));
	}

}
