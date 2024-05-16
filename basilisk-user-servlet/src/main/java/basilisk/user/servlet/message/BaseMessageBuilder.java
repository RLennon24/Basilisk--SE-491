package basilisk.user.servlet.message;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keygen.KeyCache;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseMessageBuilder {

    public static BaseMessage packMessage(String message) {
        return packMessage(message.getBytes(StandardCharsets.UTF_8));
    }

    public static BaseMessage packMessage(byte[] messageBytes) {
        try {
            BaseMessage message = new BaseMessage();

            // create string then byte array of message to be signed
            String cipherString = EncrypterUtil.encrypt(messageBytes);
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

    public static BaseMessage encodeMessage(String msg) {
        try {
            BaseMessage message = new BaseMessage();

            // create string then byte array of message to be signed
            String cipherString = EncrypterUtil.encrypt(msg, KeyCache.getEncodingKey());
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis()));
            String signature = EncrypterUtil.sign(cipherString);
            String mac = EncrypterUtil.createMac(msg, KeyCache.getMacKey());

            message.setMessage(cipherString);
            message.setTimestamp(timeStamp);
            message.setSignature(signature);
            message.setMac(mac);
            return message;
        } catch (Exception e) {
            throw new EncryptionException("Could not encode message for servlet");
        }
    }
}
