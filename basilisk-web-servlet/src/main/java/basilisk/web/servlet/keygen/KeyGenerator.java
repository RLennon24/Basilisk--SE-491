package basilisk.web.servlet.keygen;

import basilisk.web.servlet.exception.EncryptionException;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class KeyGenerator {

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

    public static Key getPrivateKey() {
        if (keyPair == null) {
            generateKeyPair();
        }

        return keyPair.getPrivate();
    }

    public static Key getPublicKey() {
        if (keyPair == null) {
            generateKeyPair();
        }

        return keyPair.getPublic();
    }
}
