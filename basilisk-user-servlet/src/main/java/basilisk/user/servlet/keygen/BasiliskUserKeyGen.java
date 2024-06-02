package basilisk.user.servlet.keygen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class BasiliskUserKeyGen {
    private static final String KEYS_FOLDER_PATH = "keys";
    private static final String PRIVATE_KEY_FILENAME = "userKey.priv";
    private static final String PUBLIC_KEY_FILENAME = "userKey.pub";

    private static KeyPair kp;

    public static void generateKeyPair() {
        PublicKey rsaPublicKey = readPublicKeyFromFile(KEYS_FOLDER_PATH + File.separator + PUBLIC_KEY_FILENAME);

        if (rsaPublicKey != null) {
            PrivateKey rsaPrivateKey = readPrivateKeyFromFile(KEYS_FOLDER_PATH + File.separator + PRIVATE_KEY_FILENAME);
            if (rsaPrivateKey != null) {
                kp = new KeyPair(rsaPublicKey, rsaPrivateKey);
            } else {
                throw new RuntimeException("Could not find both public and private keys from files.");
            }
        } else {
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(2048, SecureRandom.getInstanceStrong());

                //generate keys
                kp = keyGen.generateKeyPair();
            } catch (Exception e) {
                System.out.println("Could not generate User Keys");
            }
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

    private static RSAPublicKey readPublicKeyFromFile(String filePath) {
        RSAPublicKey key = null;

        Path folderPath = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + filePath);
        try {
            byte[] encodedPublicKey = Files.readAllBytes(folderPath);

            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedPublicKey);
            key = (RSAPublicKey) kf.generatePublic(keySpec);

        } catch (Exception e) {
            System.out.println("Could not find public key file. Generating");
        }
        return key;
    }

    private static RSAPrivateKey readPrivateKeyFromFile(String filePath) {
        RSAPrivateKey key = null;

        Path folderPath = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + filePath);

        try {
            byte[] encodedPrivateKey = Files.readAllBytes(folderPath);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            key = (RSAPrivateKey) kf.generatePrivate(keySpec);

        } catch (Exception e) {
            System.out.println("Could not find private key file. Generating");
        }
        return key;
    }

    public static void storeUserKeys() {
        if (kp == null) {
            generateKeyPair();
        }

        Path folderPath = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + KEYS_FOLDER_PATH);

        //store private
        try (FileOutputStream outPrivate = new FileOutputStream(new File(folderPath.toFile(), PRIVATE_KEY_FILENAME))) {
            outPrivate.write(kp.getPrivate().getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //store public
        try (FileOutputStream outPublic = new FileOutputStream(new File(folderPath.toFile(), PUBLIC_KEY_FILENAME))) {
            outPublic.write(kp.getPublic().getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
