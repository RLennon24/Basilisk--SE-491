package basilisk.web.servlet.service.keyexchange;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.keygen.ServerKeyGenerator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyPackager {
    /**
     * method to create string to be transported with signed encrypted message
     * note that RSA with OAEP padding is used, and the message is "Bob" + "\n" + timeStamp + "\n" + cipherString,
     * where timeStamp is the time from Alice's position and cipherString is "Alice" + "\n" + encodedSessionKey
     *
     * @return message string to be initially sent with session key
     */
    public String keyTransport(String servletIpAddress) {
        String message = null;
        try {
            // generate new session key to be sent to Bob
            KeyGenerator keyGen;
            keyGen = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom();
            keyGen.init(random);
            SecretKey sharedKey = keyGen.generateKey();

            // create string then byte array of message to be encoded
            String encodedSessionKey = EncrypterUtil.encrypt(sharedKey.getEncoded());

//            byte[] s = EncrypterUtil.decrypt(hashFunction(encodedSessionKey, "enc"));
//            byte[] encKey = Arrays.copyOfRange(s, 0,32);
//            SecretKeySpec macSks = new SecretKeySpec(EncrypterUtil.decrypt(hashFunction(encodedSessionKey, "mac")), "AES");
//            SecretKeySpec encSks = new SecretKeySpec(encKey, "AES");
//            macKey = macSks;
//            encodingKey = encSks;

            byte[] toEncode = encodedSessionKey.getBytes();

            // create cipher and encode message with Alice identifier and session key
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, bobKey, random);
            byte[] cipherText = cipher.doFinal(toEncode);

            // create string then byte array of message to be signed
            String cipherString = EncrypterUtil.encrypt(cipherText);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis()));
            String msg2 = "Bob" + "\n" + timeStamp + "\n" + cipherString;
            byte[] toSign = msg2.getBytes();

            // create signature with SHA256 and RSA then sign message
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(ServerKeyGenerator.getPrivateKey());
            sign.update(toSign);
            String signature = EncrypterUtil.encrypt(sign.sign());

            toSign = (msg2 + "\r\n" + signature).getBytes();

            // update message to new string
            message = new String(toSign);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    private String hashFunction(String input, String concat) throws NoSuchAlgorithmException {
        // getInstance() method is called with algorithm SHA-512
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        // digest() method is called
        // to calculate message digest of the input string
        // returned as array of byte
        byte[] messageDigest = md.digest((input+concat).getBytes());

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
