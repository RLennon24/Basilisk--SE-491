package basilisk.web.servlet.encryption;

import basilisk.web.servlet.exception.EncryptionException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class EncrypterUtil {
    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();
    private static SecureRandom random = new SecureRandom();

    public static String encrypt(String message, SecretKey encodingKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytesIV = new byte[16];
            random.nextBytes(bytesIV);
            IvParameterSpec ivspec = new IvParameterSpec(bytesIV);

            cipher.init(Cipher.ENCRYPT_MODE, encodingKey, ivspec);
            byte[] encrypted = cipher.doFinal(message.getBytes());
            return encoder.encodeToString(encrypted);
        } catch (Exception e) {
            throw new EncryptionException("Could not encrypt message with key");
        }
    }

    public static String encrypt(byte[] object) {
        try {
            return encoder.encodeToString(object);
        } catch (Exception e) {
            throw new EncryptionException("Could not encrypt message with key");
        }
    }

    public static byte[] decrypt(String message) {
        if (message == null || message.isEmpty()) {
            throw new EncryptionException("Cannot decrypt null or empty message");
        }

        try {
            return decoder.decode(message);
        } catch (Exception e) {
            throw new EncryptionException("Cannot decrypt message");
        }
    }

    public static byte[] decrypt(byte[] message) {
        if (message == null || message.length == 0) {
            throw new EncryptionException("Cannot decrypt null or empty message");
        }

        try {
            return decoder.decode(message);
        } catch (Exception e) {
            throw new EncryptionException("Cannot decrypt message");
        }
    }

    public String createMac(String input, SecretKey macKey) {
        String encrypted = "";

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(macKey);
            byte[] stringBytes = input.getBytes();
            byte[] macBytes = mac.doFinal(stringBytes);
            encrypted = new String(macBytes);
        } catch (Exception e) {
            throw new EncryptionException("Could not create MAC Hashing for input");
        }

        return encrypted;
    }
}
