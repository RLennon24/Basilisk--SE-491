package basilisk.user.servlet.keyexchange.packaging;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyUnpackager {

    public static void processPublicKeyPackage(BaseMessage transport) {
        try {
            checkMessageIsValid(transport);

            String publicKey = transport.getMessage();
            byte[] decodedKey = EncrypterUtil.decrypt(publicKey);

            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            RSAPublicKey key = (RSAPublicKey) kf.generatePublic(keySpec);
            KeyCache.setServerPublicKey(key);
        } catch (Exception e) {
            throw new EncryptionException("Could not unpackage shared key", e);
        }
    }

    private static void checkMessageIsValid(BaseMessage transport) {
        Date messageTime = null;
        try {
            messageTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(transport.getTimestamp());
        } catch (ParseException e) {
            throw new EncryptionException("Could not parse timestamp. Bad Message.");
        }
        Date currentTime = new Date(System.currentTimeMillis());
        boolean isRecent = Math.abs(((currentTime.getTime() - messageTime.getTime()) / (1000 * 60)) % 60) < 2;

        if (!isRecent) {
            throw new EncryptionException("Message Validity could not be verified. Bad message.");
        }
    }
}
