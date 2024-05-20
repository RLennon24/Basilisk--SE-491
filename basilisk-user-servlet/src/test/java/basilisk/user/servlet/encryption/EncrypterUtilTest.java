package basilisk.user.servlet.encryption;

import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keyexchange.packaging.KeyPackager;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;
import basilisk.user.servlet.message.BaseMessageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

public class EncrypterUtilTest {

    KeyPair webKp;
    PublicKey userPk;

    @BeforeEach
    public void setUp() {
        BasiliskUserKeyGen.generateKeyPair();
        userPk = BasiliskUserKeyGen.getUserPublicKey();
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            //generate keys
            webKp = keyGen.generateKeyPair();
            KeyCache.setServerPublicKey(webKp.getPublic());
        } catch (Exception e) {
            System.out.println("Could not generate User Keys");
        }

        KeyPackager.generateSymmetricKeyTransport();
    }

    @Test
    public void testDecodeMessage() {
        BaseMessage message = BaseMessageBuilder.encodeMessage("test");
        message.setMac(EncrypterUtil.createMac(message.getMessage(), KeyCache.getMacKey()));
        String val = EncrypterUtil.decodeMessage(message);
        assertTrue(!val.isEmpty());
    }

    @Test
    public void testEncryption() {
        String encrypted = EncrypterUtil.encrypt("Test", KeyCache.getEncodingKey());
        assertEquals("Test", EncrypterUtil.decrypt(encrypted, KeyCache.getEncodingKey()));

        EncryptionException ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.encrypt("Test", KeyCache.getMacKey());
        });
        assertEquals("Could not encrypt message with key", ex.getMessage());

        ex = assertThrows(EncryptionException.class, () -> {
            EncrypterUtil.decrypt(encrypted, KeyCache.getMacKey());
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
        String macStr = EncrypterUtil.createMac("Test", KeyCache.getMacKey());
        assertTrue(EncrypterUtil.checkMac(macStr, "Test", KeyCache.getMacKey()));
        assertFalse(EncrypterUtil.checkMac(macStr, "Tested", KeyCache.getMacKey()));
    }

    @Test
    public void testSignature() {
        KeyCache.setServerPublicKey(userPk);
        String signature = EncrypterUtil.sign("Test");
        assertTrue(EncrypterUtil.checkSignature("Test", signature));

        KeyCache.setServerPublicKey(webKp.getPublic());
        assertFalse(EncrypterUtil.checkSignature("Teste", signature));

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