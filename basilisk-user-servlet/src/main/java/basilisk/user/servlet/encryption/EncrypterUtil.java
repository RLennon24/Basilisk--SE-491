package basilisk.user.servlet.encryption;

import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;

public class EncrypterUtil {
    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();
    private static SecureRandom random = new SecureRandom();

    public static String decodeMessage(BaseMessage message) {
        if (!EncrypterUtil.checkMac(message.getMac(), message.getMessage(), KeyCache.getMacKey())) {
            throw new EncryptionException("Incorrect MAC Detected. Closing channel.");
        }

        return EncrypterUtil.decrypt(message.getMessage(), KeyCache.getEncodingKey());
    }

    public static String encrypt(String message, SecretKey encodingKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, encodingKey, ivspec);
            byte[] encrypted = cipher.doFinal(message.getBytes());
            return encoder.encodeToString(encrypted);
        } catch (Exception e) {
            throw new EncryptionException("Could not encrypt message with key");
        }
    }

    public static String encrypt(byte[] object) {
        if (object == null || object.length == 0) {
            throw new EncryptionException("Cannot encrypt null or empty message");
        }

        return encoder.encodeToString(object);
    }

    public static String decrypt(String cipherText, SecretKey encodingKey) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            IvParameterSpec ivspec = new IvParameterSpec(iv);

            cipher.init(Cipher.DECRYPT_MODE, encodingKey, ivspec);
            byte[] decrypted = cipher.doFinal(decoder.decode(cipherText));

            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new EncryptionException("Could not decrypt message with key");
        }
    }

    public static byte[] decrypt(String message) {
        if (message == null || message.isEmpty()) {
            throw new EncryptionException("Cannot decrypt null or empty message");
        }

        return decoder.decode(message);
    }

    public static String createMac(String input, SecretKey macKey) {
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

    public static Boolean checkMac(String macStr, String msg, SecretKey macKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(macKey);
            byte[] stringBytes = msg.getBytes();
            byte[] macBytes = mac.doFinal(stringBytes);
            String newMacStr = new String(macBytes);
            return macStr.equals(newMacStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkSignature(String signedObj, String signature) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(KeyCache.getServerPublicKey());
            sign.update(signedObj.getBytes());
            return sign.verify(decoder.decode(signature));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String sign(String message) {
        if (message == null || message.isEmpty()) {
            throw new EncryptionException("Could not sign null or empty bytes");
        }

        // create signature with SHA256 and RSA then sign message
        try {
            byte[] toSign = message.getBytes();

            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(BasiliskUserKeyGen.getUserPrivateKey());
            sign.update(toSign);
            return EncrypterUtil.encrypt(sign.sign());
        } catch (Exception e) {
            throw new EncryptionException("Could not sign bytes");
        }
    }

    public static String hashFunction(String input, String concat) throws NoSuchAlgorithmException {
        // getInstance() method is called with algorithm SHA-512
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        // digest() method is called
        // to calculate message digest of the input string
        // returned as array of byte
        byte[] messageDigest = md.digest((input + concat).getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);

        // Add preceding 0s to make it 32 bit
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
