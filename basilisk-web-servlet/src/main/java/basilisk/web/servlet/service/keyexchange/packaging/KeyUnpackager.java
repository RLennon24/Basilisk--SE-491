package basilisk.web.servlet.service.keyexchange.packaging;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class KeyUnpackager {

    public static void processPublicKeyPackage(KeyPackage transport) {
        try {
            checkMessageIsValid(transport);
            String sessionKey = transport.getKey();

            byte[] decodedKey = EncrypterUtil.decrypt(sessionKey);
            SecretKeySpec sks = new SecretKeySpec(decodedKey, "AES");
            // TODO: add session key to cache
            KeyCache.addServiceEncodingKey("", sks);

            // generate mac and encoding keys
            byte[] s = EncrypterUtil.decrypt(EncrypterUtil.hashFunction(sessionKey, "enc"));
            byte[] decKey = Arrays.copyOfRange(s, 0, 32);
            SecretKeySpec macSks = new SecretKeySpec(EncrypterUtil.decrypt(EncrypterUtil.hashFunction(sessionKey, "mac")), "AES");
            SecretKeySpec decSks = new SecretKeySpec(decKey, "AES");
            // TODO: add mac and session keys to cache
            KeyCache.addServiceMacKey("", sks);
            KeyCache.addServicePublicKey("", sks);

        } catch (Exception e) {
            throw new EncryptionException("Could not unpackage shared key");
        }
    }

    private static void checkMessageIsValid(KeyPackage transport) {
        Date messageTime = null;
        try {
            messageTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(transport.getTimestamp());
        } catch (ParseException e) {
            throw new EncryptionException("Could not parse timestamp. Bad Message.");
        }
        Date currentTime = new Date(System.currentTimeMillis());
        boolean isRecent = (((currentTime.getTime() - messageTime.getTime()) / (1000 * 60)) % 60) < 2;

        if (!isRecent) {
            throw new EncryptionException("Message Validity could not be verified. Bad message.");
        }
    }
}
