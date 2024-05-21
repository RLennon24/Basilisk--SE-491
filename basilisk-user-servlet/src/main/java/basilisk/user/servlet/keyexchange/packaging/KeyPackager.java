package basilisk.user.servlet.keyexchange.packaging;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class KeyPackager {

    /**
     * method to create string to be transported with signed encrypted message
     * note that RSA with OAEP padding is used, and the message is "\n" + timeStamp + "\n" + cipherString,
     * where timeStamp is the time from web servlet's position and cipherString is "Alice" + "\n" + encodedSessionKey
     *
     * @return message string to be initially sent with session key
     */
    public static BaseMessage generatePublicKeyTransport() {
        BaseMessage message;
        try {
            message = packMessage(BasiliskUserKeyGen.getUserPublicKey().getEncoded());
        } catch (Exception e) {
            throw new EncryptionException("Could not do key transport for servlet");
        }

        return message;
    }

    /**
     * method to create string to be transported with signed encrypted message
     * note that RSA with OAEP padding is used, and the message is "\n" + timeStamp + "\n" + cipherString,
     * where timeStamp is the time from web servlet's position and cipherString is "Alice" + "\n" + encodedSessionKey
     *
     * @return message string to be initially sent with session key
     */
    public static BaseMessage generateSymmetricKeyTransport() {
        String encodedSessionKey;
        SecureRandom random = new SecureRandom();

        if (KeyCache.getSessionKey() != null) {
            // create string then byte array of message to be encoded
            encodedSessionKey = EncrypterUtil.encrypt(KeyCache.getSessionKey().getEncoded());
        } else {
            try {
                // generate new session key to be sent to Bob
                KeyGenerator keyGen;
                keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(random);
                SecretKey sharedKey = keyGen.generateKey();

                // add the session key for the cache
                KeyCache.setSessionKey(sharedKey);

                // create string then byte array of message to be encoded
                encodedSessionKey = EncrypterUtil.encrypt(sharedKey.getEncoded());

                // generate mac and encoding keys
                byte[] s = EncrypterUtil.decrypt(EncrypterUtil.hashFunction(encodedSessionKey, "enc"));
                byte[] encKey = Arrays.copyOfRange(s, 0, 32);
                SecretKeySpec macSks = new SecretKeySpec(EncrypterUtil.decrypt(EncrypterUtil.hashFunction(encodedSessionKey, "mac")), "AES");
                SecretKeySpec encSks = new SecretKeySpec(encKey, "AES");

                KeyCache.setMacKey(macSks);
                KeyCache.setEncodingKey(encSks);

            } catch (Exception e) {
                throw new EncryptionException("Could not do key transport for servlet", e);
            }
        }

        try {
            byte[] toEncode = encodedSessionKey.getBytes();

            // create cipher and encode message with self identifier and session key
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, KeyCache.getServerPublicKey(), random);
            byte[] cipherText = cipher.doFinal(toEncode);

            return packMessage(cipherText);
        } catch (Exception e) {
            throw new EncryptionException("Could not do key transport for servlet", e);
        }
    }

    private static BaseMessage packMessage(byte[] encodedKey) {
        try {
            BaseMessage message = new BaseMessage();

            // create string then byte array of message to be signed
            String cipherString = EncrypterUtil.encrypt(encodedKey);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis()));
            String signature = EncrypterUtil.sign(cipherString);

            message.setMessage(cipherString);
            message.setTimestamp(timeStamp);
            message.setSignature(signature);
            return message;
        } catch (Exception e) {
            throw new EncryptionException("Could not encode message for servlet");
        }
    }
}
