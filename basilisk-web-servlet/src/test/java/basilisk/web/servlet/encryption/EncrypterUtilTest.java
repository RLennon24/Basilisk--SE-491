package basilisk.web.servlet.encryption;

import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.keygen.ServerKeyGenerator;
import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.message.BaseMessageBuilder;
import basilisk.web.servlet.util.GenKeysForTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

public class EncrypterUtilTest {

    KeyPair webKp;
    PublicKey serverPk;

    @BeforeEach
    public void setUp() {
        serverPk = ServerKeyGenerator.getPublicKey();
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            //generate keys
            webKp = keyGen.generateKeyPair();
            KeyCache.addServicePublicKey("test", webKp.getPublic());
        } catch (Exception e) {
            System.out.println("Could not generate User Keys");
        }

        GenKeysForTestUtil.generateSymmetricKeys("test");
    }

    @Test
    public void testDecodeMessage() {
        BaseMessage message = BaseMessageBuilder.packMessage("test", "test");
        message.setMac(EncrypterUtil.createMac(message.getMessage(), KeyCache.getMacKeyForService("test")));
        String val = EncrypterUtil.decodeMessage(message, "test");
        assertTrue(!val.isEmpty());
    }

    @Test
    public void testEncryption() {
        String encrypted = EncrypterUtil.encrypt("Test", KeyCache.getEncodingKeyForService("test"));
        assertEquals("Test", EncrypterUtil.decrypt(encrypted, KeyCache.getEncodingKeyForService("test")));

        EncryptionException ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.encrypt("Test", KeyCache.getMacKeyForService("test"));
        });
        assertEquals("Could not encrypt message with key", ex.getMessage());

        ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.decrypt(encrypted, KeyCache.getMacKeyForService("test"));
        });
        assertEquals("Could not decrypt message with key", ex.getMessage());

        byte[] encryptedBytes = EncrypterUtil.decrypt("Test");
        assertEquals("Test", EncrypterUtil.encrypt(encryptedBytes));

        ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.decrypt("");
        });
        assertEquals("Cannot decrypt null or empty message", ex.getMessage());

        ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.decrypt(null);
        });
        assertEquals("Cannot decrypt null or empty message", ex.getMessage());

        ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.encrypt(new byte[0]);
        });
        assertEquals("Cannot encrypt null or empty message", ex.getMessage());

        ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.encrypt(null);
        });
        assertEquals("Cannot encrypt null or empty message", ex.getMessage());
    }

    @Test
    public void testMac() {
        String macStr = EncrypterUtil.createMac("Test", KeyCache.getMacKeyForService("test"));
        assertTrue(EncrypterUtil.checkMac(macStr, "Test", KeyCache.getMacKeyForService("test")));
        assertFalse(EncrypterUtil.checkMac(macStr, "Tested", KeyCache.getMacKeyForService("test")));
    }

    @Test
    public void testSignature() {
        KeyCache.addServicePublicKey("test", serverPk);
        String signature = EncrypterUtil.sign("Test");
        assertTrue(EncrypterUtil.checkSignature("test", "Test", signature));

        KeyCache.addServicePublicKey("test", webKp.getPublic());
        assertFalse(EncrypterUtil.checkSignature("test", "Teste", signature));

        EncryptionException ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.sign("");
        });
        assertEquals("Could not sign null or empty bytes", ex.getMessage());

        ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.sign(null);
        });
        assertEquals("Could not sign null or empty bytes", ex.getMessage());
    }

    @Test
    public void testHashFunction() throws NoSuchAlgorithmException {
        String testStrOne = "testStrOne";
        String testStrTwo = "testStrTwo";
        String testAppendOne = "testAppendOne";
        String testAppendTwo = "testAppendTwo";

        assertEquals(EncrypterUtil.hashFunction(testStrOne, testAppendOne), EncrypterUtil.hashFunction(testStrOne, testAppendOne));
        assertNotEquals(EncrypterUtil.hashFunction(testStrOne, testAppendOne), EncrypterUtil.hashFunction(testStrTwo, testAppendTwo));
        assertNotEquals(EncrypterUtil.hashFunction(testStrOne, testAppendOne), EncrypterUtil.hashFunction(testStrOne, testAppendTwo));
        assertNotEquals(EncrypterUtil.hashFunction(testStrOne, testAppendOne), EncrypterUtil.hashFunction(testStrTwo, testAppendOne));
    }
}