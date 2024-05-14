package basilisk.web.servlet.service.keyexchange.packaging;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.keygen.ServerKeyGenerator;

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
        KeyPackage message;
        try {
            message = packMessage(ServerKeyGenerator.getPublicKey().getEncoded());
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
