package basilisk.user.servlet.service.keyexchange.packaging;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.exception.EncryptionException;
import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;

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
    public static KeyPackage generatePublicKeyTransport() {
        KeyPackage message = null;
        try {
            // TODO: get public key
            //message = packMessage(ServerKeyGenerator.getPublicKey().getEncoded());
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
    public static KeyPackage generateSymmetricKeyTransport(String servletIpAddress) {
        KeyPackage message = new KeyPackage();
        try {
            // generate new session key to be sent to Bob
            KeyGenerator keyGen;
            keyGen = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom();
            keyGen.init(random);
            SecretKey sharedKey = keyGen.generateKey();

            // add the session key for the cache
            //TODO: KeyCache.addServiceSessionKey(servletIpAddress, sharedKey);

            // create string then byte array of message to be encoded
            String encodedSessionKey = EncrypterUtil.encrypt(sharedKey.getEncoded());

            // generate mac and encoding keys
            byte[] s = EncrypterUtil.decrypt(EncrypterUtil.hashFunction(encodedSessionKey, "enc"));
            byte[] encKey = Arrays.copyOfRange(s, 0, 32);
            SecretKeySpec macSks = new SecretKeySpec(EncrypterUtil.decrypt(EncrypterUtil.hashFunction(encodedSessionKey, "mac")), "AES");
            SecretKeySpec encSks = new SecretKeySpec(encKey, "AES");
            // TODO: add mac and session keys to cache
            // KeyCache.addServiceMacKey(servletIpAddress, macSks);
            // KeyCache.addServiceEncodingKey(servletIpAddress, encSks);

            byte[] toEncode = encodedSessionKey.getBytes();

            // create cipher and encode message with self identifier and session key
            Cipher cipher = Cipher.getInstance("RSA");
            // TODO: cipher.init(Cipher.ENCRYPT_MODE, KeyCache.getPublicKeyForService(servletIpAddress), random);
            byte[] cipherText = cipher.doFinal(toEncode);

            message = packMessage(cipherText);
        } catch (Exception e) {
            throw new EncryptionException("Could not do key transport for servlet");
        }

        return message;
    }

    private static KeyPackage packMessage(byte[] encodedKey) {
        try {
            KeyPackage message = new KeyPackage();

            // create string then byte array of message to be signed
            String cipherString = EncrypterUtil.encrypt(encodedKey);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis()));
            String messageForTransport = timeStamp + "\n" + cipherString;
            String signature = EncrypterUtil.sign(messageForTransport);

            message.setKey(cipherString);
            message.setTimestamp(timeStamp);
            message.setSignature(signature);
            return message;
        } catch (Exception e) {
            throw new EncryptionException("Could not encode message for servlet");
        }
    }
}
