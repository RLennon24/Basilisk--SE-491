import java.util.ArrayList;

public class DataUnit {
		private String id;
		private String data;
		//private int ip;
		private ArrayList <String> tags;
		private ArrayList <String> roles;

		public DataUnit(){
			
		}

		public DataUnit(String id, String data, ArrayList<String> tags, ArrayList<String> roles){
			this.id= id;
			this.data=data;
			//this.ip= ip;
			this.tags= tags;
			this.roles= roles;
		}
		
		
		public String getId() {return id;}
		public String getData() {return data;}
		//public int getIp() {return ip;}
		public ArrayList <String> getTags() {return tags;}
		public ArrayList <String> getRoles(){return roles;}
		
		
	}
