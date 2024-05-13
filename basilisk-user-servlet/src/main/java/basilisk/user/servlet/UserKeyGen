import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


public class BasiliskUserKeyGen {
	
	private static KeyPair kp;
	private static PrivateKey userPrivate;
	private static PublicKey userPublic;
	
	public static void generateKeyPair() {
		
	try{
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048, SecureRandom.getInstanceStrong());
		
		//generate keys
		kp= keyGen.generateKeyPair();
		userPrivate = kp.getPrivate();
		userPublic = kp.getPublic();
		
		
	}catch (Exception e){
		System.out.println("Could not generate User Keys");
		}
	}
	
	public static Key getUserPrivateKey() {
		if(kp == null) {
			generateKeyPair();
		}
		return kp.getPrivate();
	}
	
	public static Key getUserPublicKey() {
		if(kp == null) {
			generateKeyPair();
		}
		return kp.getPublic();
	}
	
	public static void clearKeyPair() {
		kp = null;
	}
	
	/*public static void storeUserKeys() {
		if(kp == null) {
			generateKeyPair();
		}
		
		
		//store private
		try(FileOutputStream outPrivate = new FileOutputStream("userKey.priv")){
				outPrivate.write(userPrivate.getEncoded());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		//store public
		try(FileOutputStream outPublic = new FileOutputStream("userKey.pub")){
			outPublic.write(userPublic.getEncoded());
		} catch (IOException e) {
			e.printStackTrace();
		}
		}*/
	}
