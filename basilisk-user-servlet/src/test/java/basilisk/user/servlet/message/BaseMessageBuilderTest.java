package basilisk.user.servlet.message;

import basilisk.user.servlet.keyexchange.packaging.KeyPackager;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.keygen.KeyCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class BaseMessageBuilderTest {

    @BeforeAll
    public static void setUp() {
        BasiliskUserKeyGen.generateKeyPair();
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            //generate keys
            KeyCache.setServerPublicKey(keyGen.generateKeyPair().getPublic());
        } catch (Exception e) {
            System.out.println("Could not generate User Keys");
        }

        KeyPackager.generateSymmetricKeyTransport();
    }

    @Test
    public void testPackMessage() {
        BaseMessage message = BaseMessageBuilder.packMessage("test");
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getMessage());
        Assertions.assertNotNull(message.getSignature());
        Assertions.assertNotNull(message.getTimestamp());

        message = BaseMessageBuilder.packMessage("test".getBytes(StandardCharsets.UTF_8));
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getMessage());
        Assertions.assertNotNull(message.getSignature());
        Assertions.assertNotNull(message.getTimestamp());
    }

    @Test
    public void testEncodeMessage() {
        BaseMessage message = BaseMessageBuilder.encodeMessage("test");
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getMessage());
        Assertions.assertNotNull(message.getSignature());
        Assertions.assertNotNull(message.getTimestamp());
        Assertions.assertNotNull(message.getMac());
    }
}