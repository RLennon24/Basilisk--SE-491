package basilisk.user.servlet.service.keyexchange;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.exception.EncryptionException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class KeyUnpackager {

    public static void processKeyTransport(String transport) {
        try {
            checkMessageIsValid(transport);

            String[] transportComponents = transport.substring(0, transport.indexOf("\r\n")).split("\\n");

            // create cipher and encode message with identifier and session key
            Cipher cipher = Cipher.getInstance("RSA");
            // TODO: get the private key from Riley's code
            cipher.init(Cipher.DECRYPT_MODE, null);
            String decodedSessionKey = new String(cipher.doFinal(EncrypterUtil.decrypt(transportComponents[1])),
                    "UTF-8");
            String[] splitSessionKey = decodedSessionKey.split("\\n");
            String sessionKey = splitSessionKey[1];

            byte[] decodedKey = EncrypterUtil.decrypt(sessionKey);
            SecretKeySpec sks = new SecretKeySpec(decodedKey, "AES");
            // TODO: add session key to cache

            // generate mac and encoding keys
            byte[] s = EncrypterUtil.decrypt(EncrypterUtil.hashFunction(sessionKey, "enc"));
            byte[] decKey = Arrays.copyOfRange(s, 0, 32);
            SecretKeySpec macSks = new SecretKeySpec(EncrypterUtil.decrypt(EncrypterUtil.hashFunction(sessionKey, "mac")), "AES");
            SecretKeySpec decSks = new SecretKeySpec(decKey, "AES");
            // TODO: add mac and session keys to cache

        } catch (Exception e) {
            throw new EncryptionException("Could not unpackage shared key");
        }
    }

    private static void checkMessageIsValid(String transport) {
        String signature = transport.substring(transport.indexOf("\r\n") + 2);
        transport = transport.substring(0, transport.indexOf("\r\n"));
        String[] transportComponents = transport.split("\\n");

        Date messageTime = null;
        try {
            messageTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(transportComponents[0]);
        } catch (ParseException e) {
            throw new EncryptionException("Could not parse timestamp. Bad Message.");
        }
        Date currentTime = new Date(System.currentTimeMillis());
        boolean isRecent = (((currentTime.getTime() - messageTime.getTime()) / (1000 * 60)) % 60) < 2;

        if (!isRecent || !EncrypterUtil.checkSignature(transport, signature)) {
            throw new EncryptionException("Message Validity could not be verified. Bad message.");
        }
    }
}
