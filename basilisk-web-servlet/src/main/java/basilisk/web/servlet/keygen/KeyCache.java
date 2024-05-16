package basilisk.web.servlet.keygen;

import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.util.KeyRing;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyCache {

    private static final Map<String, KeyRing> serviceKeys = new ConcurrentHashMap<>();

    public static void addServicePublicKey(String serviceIp, PublicKey publicKey) {
        serviceKeys.put(serviceIp, new KeyRing(publicKey));
    }

    public static void addServiceSessionKey(String serviceIp, SecretKey sessionKey) {
        if (serviceKeys.containsKey(serviceIp)) {
            serviceKeys.get(serviceIp).setSessionKey(sessionKey);
        } else {
            throw new EncryptionException("Could not find key in storage for service with IP: " + serviceIp);
        }
    }

    public static void addServiceMacKey(String serviceIp, SecretKey macKey) {
        if (serviceKeys.containsKey(serviceIp)) {
            serviceKeys.get(serviceIp).setMacKey(macKey);
        } else {
            throw new EncryptionException("Could not find key in storage for service with IP: " + serviceIp);
        }
    }

    public static void addServiceEncodingKey(String serviceIp, SecretKey encodingKey) {
        if (serviceKeys.containsKey(serviceIp)) {
            serviceKeys.get(serviceIp).setEncodingKey(encodingKey);
        } else {
            throw new EncryptionException("Could not find key in storage for service with IP: " + serviceIp);
        }
    }

    public static PublicKey getPublicKeyForService(String serviceIp) {
        if (serviceKeys.containsKey(serviceIp)) {
            return serviceKeys.get(serviceIp).getPublicKey();
        } else {
            throw new EncryptionException("Could not find public key in storage for service with IP: " + serviceIp);
        }
    }

    public static SecretKey getSessionKeyForService(String serviceIp) {
        if (serviceKeys.containsKey(serviceIp)) {
            return serviceKeys.get(serviceIp).getSessionKey();
        } else {
            throw new EncryptionException("Could not find session key in storage for service with IP: " + serviceIp);
        }
    }

    public static SecretKey getMacKeyForService(String serviceIp) {
        if (serviceKeys.containsKey(serviceIp)) {
            return serviceKeys.get(serviceIp).getMacKey();
        } else {
            throw new EncryptionException("Could not find mac key in storage for service with IP: " + serviceIp);
        }
    }

    public static SecretKey getEncodingKeyForService(String serviceIp) {
        if (serviceKeys.containsKey(serviceIp)) {
            return serviceKeys.get(serviceIp).getEncodingKey();
        } else {
            throw new EncryptionException("Could not find encoding key in storage for service with IP: " + serviceIp);
        }
    }

    public static void removeKeyForService(String serviceIp) {
        serviceKeys.remove(serviceIp);
    }
}
