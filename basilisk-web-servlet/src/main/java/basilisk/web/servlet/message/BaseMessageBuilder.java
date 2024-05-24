package basilisk.web.servlet.message;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseMessageBuilder {

    public static BaseMessage packMessage(String msg, String serverIp) {
        try {
            BaseMessage message = new BaseMessage();

            // create string then byte array of message to be signed
            String encodedMsg = EncrypterUtil.encrypt(msg, KeyCache.getEncodingKeyForService(serverIp));
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis()));
            String signature = EncrypterUtil.sign(encodedMsg);
            String mac = EncrypterUtil.createMac(encodedMsg, KeyCache.getMacKeyForService(serverIp));

            message.setMessage(encodedMsg);
            message.setTimestamp(timeStamp);
            message.setSignature(signature);
            message.setMac(mac);

            return message;
        } catch (Exception e) {
            throw new EncryptionException("Could not encode message for servlet");
        }
    }

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
}
