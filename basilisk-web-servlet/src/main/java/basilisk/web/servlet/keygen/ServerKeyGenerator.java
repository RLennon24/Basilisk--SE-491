package basilisk.web.servlet.keygen;

import basilisk.web.servlet.exception.EncryptionException;

import java.security.*;

public class ServerKeyGenerator {

    private static KeyPair keyPair;

    private static void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            keyPair = keyGen.generateKeyPair();
        } catch (Exception e) {
            throw new EncryptionException("Could not generate keys", e);
        }
    }

    public static PrivateKey getPrivateKey() {
        if (keyPair == null) {
            generateKeyPair();
        }

        return keyPair.getPrivate();
    }

    public static PublicKey getPublicKey() {
        if (keyPair == null) {
            generateKeyPair();
        }

        return keyPair.getPublic();
    }

    public static void clearKeyPair() {
        keyPair = null;
    }
}
