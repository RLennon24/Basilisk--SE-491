package basilisk.web.servlet.service.keyexchange.packaging;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.keygen.ServerKeyGenerator;
import basilisk.web.servlet.message.BaseMessage;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class KeyUnpackager {

    public static void processPublicKeyPackage(String serviceIp, BaseMessage transport) {
        try {
            checkMessageIsValid(transport);
            System.out.println("Unpackaging Public Keys for address: " + serviceIp);

            String publicKey = transport.getMessage();
            byte[] decodedKey = EncrypterUtil.decrypt(publicKey);

            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            RSAPublicKey key = (RSAPublicKey) kf.generatePublic(keySpec);
            KeyCache.addServicePublicKey(serviceIp, key);
            System.out.println("Unpackaged and Stored Public Key for address: " + serviceIp);

        } catch (Exception e) {
            throw new EncryptionException("Could not unpackage public keys for address: " + serviceIp);
        }
    }

    public static void processSymmetricKeyPackage(String serviceIp, BaseMessage transport) {
        try {
            checkMessageIsValid(transport);
            System.out.println("Unpackaging Symmetric Keys for address: " + serviceIp);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, ServerKeyGenerator.getPrivateKey());
            String sessionKey = new String(cipher.doFinal(EncrypterUtil.decrypt(transport.getMessage())),
                    "UTF-8");

            byte[] decodedKey = EncrypterUtil.decrypt(sessionKey);
            SecretKeySpec sks = new SecretKeySpec(decodedKey, "AES");
            KeyCache.addServiceSessionKey(serviceIp, sks);
            System.out.println("Stored Session Key for address: " + serviceIp);

            byte[] s = EncrypterUtil.decrypt(EncrypterUtil.hashFunction(sessionKey, "enc"));
            byte[] decKey = Arrays.copyOfRange(s, 0, 32);
            SecretKeySpec macSks = new SecretKeySpec(EncrypterUtil.decrypt(EncrypterUtil.hashFunction(sessionKey, "mac")), "AES");
            SecretKeySpec decSks = new SecretKeySpec(decKey, "AES");
            KeyCache.addServiceMacKey(serviceIp, macSks);
            KeyCache.addServiceEncodingKey(serviceIp, decSks);
            System.out.println("Stored Mac and Encoding Keys for address: " + serviceIp);

            System.out.println("Successfully Unpackaged Symmetric Keys for address: " + serviceIp);

        } catch (Exception e) {
            throw new EncryptionException("Could not unpackage symmetric keys for address: " + serviceIp);
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
        boolean isRecent = (((currentTime.getTime() - messageTime.getTime()) / (1000 * 60)) % 60) < 2;

        if (!isRecent) {
            throw new EncryptionException("Message Validity could not be verified. Bad message.");
        }
    }
}
