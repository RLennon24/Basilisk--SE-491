import java.util.ArrayList;

public class ParsingCachingJSON {
	Gson gson = new Gson();
  DataUnit data= new DataUnit();

  String fileName= "dataparser/JSONdata.json";

  
	
	
	
	
}

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
