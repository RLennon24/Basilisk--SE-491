package basilisk.web.servlet.util;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.message.BaseMessage;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

import static basilisk.web.servlet.message.BaseMessageBuilder.packMessage;

public class GenKeysForTestUtil {

    public static BaseMessage generateSymmetricKeys(String service) {
        String encodedSessionKey;
        SecureRandom random = new SecureRandom();

        if (KeyCache.getSessionKeyForService(service) != null) {
            // create string then byte array of message to be encoded
            encodedSessionKey = EncrypterUtil.encrypt(KeyCache.getSessionKeyForService(service).getEncoded());
        } else {
            try {
                // generate new session key to be sent to Bob
                KeyGenerator keyGen;
                keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(random);
                SecretKey sharedKey = keyGen.generateKey();

                // add the session key for the cache
                KeyCache.addServiceSessionKey(service, sharedKey);

                // create string then byte array of message to be encoded
                encodedSessionKey = EncrypterUtil.encrypt(sharedKey.getEncoded());

                // generate mac and encoding keys
                byte[] s = EncrypterUtil.decrypt(EncrypterUtil.hashFunction(encodedSessionKey, "enc"));
                byte[] encKey = Arrays.copyOfRange(s, 0, 32);
                SecretKeySpec macSks = new SecretKeySpec(EncrypterUtil.decrypt(EncrypterUtil.hashFunction(encodedSessionKey, "mac")), "AES");
                SecretKeySpec encSks = new SecretKeySpec(encKey, "AES");

                KeyCache.addServiceMacKey(service, macSks);
                KeyCache.addServiceEncodingKey(service, encSks);

            } catch (Exception e) {
                throw new EncryptionException("Could not do key transport for servlet", e);
            }
        }

        try {
            byte[] toEncode = encodedSessionKey.getBytes();

            // create cipher and encode message with self identifier and session key
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, KeyCache.getPublicKeyForService(service), random);
            byte[] cipherText = cipher.doFinal(toEncode);

            return packMessage(cipherText);
        } catch (Exception e) {
            throw new EncryptionException("Could not do key transport for servlet", e);
        }
    }
}
