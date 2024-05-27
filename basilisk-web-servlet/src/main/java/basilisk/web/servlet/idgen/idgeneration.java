public class idgeneration {
    public static void idgen(){
        String strKey= DataUnit.getName() + DataUnit.getDataCreator() + DataUnit.getTimestamp();
               String regex= "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)";

               //tohex
               StringBuilder hexString= new StringBuilder();
               for(char c: strKey.toCharArray()) {
            	   hexString.append(String.format("%02x", (int) c));
            	   strKey= hexString.toString();
               }


               //check length
               while(strKey.length()< 32) {
            	  strKey= "a" + strKey;
               }
            	   strKey = strKey.substring(0,32);

            //format
            	   String strKeyformat = strKey.substring(0,8) + "-" + strKey.substring(8,12) + "-" + strKey.substring(12,16) + "-" + strKey.substring(16,20) + "-" + strKey.substring(20,32);


                UUID idKey= UUID.fromString(strKeyformat);
                String StrIdKey = idKey.toString();
                DataUnit.setId(StrIdKey);

    }
    
}
