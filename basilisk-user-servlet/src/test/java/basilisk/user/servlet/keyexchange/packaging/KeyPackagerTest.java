package basilisk.user.servlet.keyexchange.packaging;

import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeyPackagerTest {

    @Test
    public void testGeneratePublicKeyTransport() {
        BaseMessage message = KeyPackager.generatePublicKeyTransport();
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getMessage());
        Assertions.assertNotNull(message.getSignature());
        Assertions.assertNotNull(message.getTimestamp());

        Assertions.assertNotNull(BasiliskUserKeyGen.getUserPublicKey());
        Assertions.assertNotNull(BasiliskUserKeyGen.getUserPrivateKey());
    }

    @Test
    public void testGenerateSymmetricKeyTransport() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            //generate keys
            KeyCache.setServerPublicKey(keyGen.generateKeyPair().getPublic());
        } catch (Exception e) {
            System.out.println("Could not generate User Keys");
        }

        BaseMessage message = KeyPackager.generateSymmetricKeyTransport();
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getMessage());
        Assertions.assertNotNull(message.getSignature());
        Assertions.assertNotNull(message.getTimestamp());

        Assertions.assertNotNull(KeyCache.getSessionKey());
        Assertions.assertNotNull(KeyCache.getMacKey());
        Assertions.assertNotNull(KeyCache.getEncodingKey());

        KeyCache.setServerPublicKey(null);
        EncryptionException ex = assertThrows(EncryptionException.class, KeyPackager::generateSymmetricKeyTransport);
        assertEquals("Could not do key transport for servlet", ex.getMessage());
    }
}