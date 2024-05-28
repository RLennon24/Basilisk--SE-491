package basilisk.web.servlet.message;

import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.keygen.ServerKeyGenerator;
import basilisk.web.servlet.util.GenKeysForTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class BaseMessageBuilderTest {

    @BeforeAll
    public static void setUp() {
        ServerKeyGenerator.getPublicKey();
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            //generate keys
            KeyCache.addServicePublicKey("test", "",keyGen.generateKeyPair().getPublic());
        } catch (Exception e) {
            System.out.println("Could not generate User Keys");
        }

        GenKeysForTestUtil.generateSymmetricKeys("test");
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
        BaseMessage message = BaseMessageBuilder.packMessage("test", "test");
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(message.getMessage());
        Assertions.assertNotNull(message.getSignature());
        Assertions.assertNotNull(message.getTimestamp());
        Assertions.assertNotNull(message.getMac());
    }
}