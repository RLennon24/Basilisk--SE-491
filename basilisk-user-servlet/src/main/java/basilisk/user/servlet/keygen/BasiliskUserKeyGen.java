package basilisk.user.servlet.keygen;

import java.security.*;


public class BasiliskUserKeyGen {

    private static KeyPair kp;

    public static void generateKeyPair() {

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            //generate keys
            kp = keyGen.generateKeyPair();
        } catch (Exception e) {
            System.out.println("Could not generate User Keys");
        }
    }

    public static PrivateKey getUserPrivateKey() {
        if (kp == null) {
            generateKeyPair();
        }
        return kp.getPrivate();
    }

    public static PublicKey getUserPublicKey() {
        if (kp == null) {
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
