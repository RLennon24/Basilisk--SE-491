package basilisk.web.servlet.keygen;

import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.util.KeyRing;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyCache {

    private static final Map<String, KeyRing> serviceKeys = new ConcurrentHashMap<>();

    public static void addServicePublicKey(String owner, String ownerIp, PublicKey publicKey) {
        serviceKeys.put(owner, new KeyRing(owner, ownerIp, publicKey));
    }

    public static void addServiceSessionKey(String owner, SecretKey sessionKey) {
        if (serviceKeys.containsKey(owner)) {
            serviceKeys.get(owner).setSessionKey(sessionKey);
        } else {
            throw new EncryptionException("Could not find key in storage for Owner: " + owner);
        }
    }

    public static void addServiceMacKey(String owner, SecretKey macKey) {
        if (serviceKeys.containsKey(owner)) {
            serviceKeys.get(owner).setMacKey(macKey);
        } else {
            throw new EncryptionException("Could not find key in storage for Owner: " + owner);
        }
    }

    public static void addServiceEncodingKey(String owner, SecretKey encodingKey) {
        if (serviceKeys.containsKey(owner)) {
            serviceKeys.get(owner).setEncodingKey(encodingKey);
        } else {
            throw new EncryptionException("Could not find key in storage for Owner: " + owner);
        }
    }

    public static PublicKey getPublicKeyForService(String owner) {
        if (serviceKeys.containsKey(owner)) {
            return serviceKeys.get(owner).getPublicKey();
        } else {
            throw new EncryptionException("Could not find public key in storage for Owner: " + owner);
        }
    }

    public static SecretKey getSessionKeyForService(String owner) {
        if (serviceKeys.containsKey(owner)) {
            return serviceKeys.get(owner).getSessionKey();
        } else {
            throw new EncryptionException("Could not find session key in storage for Owner: " + owner);
        }
    }

    public static SecretKey getMacKeyForService(String owner) {
        if (serviceKeys.containsKey(owner)) {
            return serviceKeys.get(owner).getMacKey();
        } else {
            throw new EncryptionException("Could not find mac key in storage for Owner: " + owner);
        }
    }

    public static SecretKey getEncodingKeyForService(String owner) {
        if (serviceKeys.containsKey(owner)) {
            return serviceKeys.get(owner).getEncodingKey();
        } else {
            throw new EncryptionException("Could not find encoding key in storage for Owner: " + owner);
        }
    }

    public static String getServiceIpForOwner(String owner) {
        if (serviceKeys.containsKey(owner)) {
            return serviceKeys.get(owner).getOwnerIp();
        } else {
            throw new EncryptionException("Could not find encoding key in storage for Owner: " + owner);
        }
    }

    public static void removeKeyForService(String owner) {
        serviceKeys.remove(owner);
    }
}
