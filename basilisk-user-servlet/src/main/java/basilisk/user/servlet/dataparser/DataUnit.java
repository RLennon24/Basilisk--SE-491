import java.util.ArrayList;

public class DataUnit{
	private String id;
	private String data;
	private int ip;
	private ArrayList<String> tags;
	private ArrayList<String> roles;

	public DataUnit(){
		
	}

	public DataUnit(String id, String data, int ip, String[] tags, String[] roles){
		this.id= id;
		this.data=data;
		this.ip= ip;
		this.tags= tags;
		this.roles= roles;
	}
	
	
	public String getId() {return id;}
	public String getData() {return data;}
	public int getIp() {return ip;}
	public ArrayList <DataUnit> getTags() {return tags;}
	public ArrayList <DataUnit> getRoles(){return roles;}
	
	
}
