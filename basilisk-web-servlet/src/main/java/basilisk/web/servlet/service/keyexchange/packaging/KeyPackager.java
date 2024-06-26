package basilisk.web.servlet.service.keyexchange.packaging;

import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.ServerKeyGenerator;
import basilisk.web.servlet.message.BaseMessage;

import static basilisk.web.servlet.message.BaseMessageBuilder.packMessage;

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
            message = packMessage(ServerKeyGenerator.getPublicKey().getEncoded());
        } catch (Exception e) {
            throw new EncryptionException("Could not do key transport for servlet");
        }

        return message;
    }
}
