package basilisk.web.servlet.keygen;

import basilisk.web.servlet.exception.EncryptionException;

import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyCache {

    private static final Map<String, PublicKey> serviceKeys = new ConcurrentHashMap<>();

    public static void addServiceKey(String serviceIp, PublicKey serviceKey) {
        serviceKeys.put(serviceIp, serviceKey);
    }

    public static PublicKey getKeyForService(String serviceIp) {
        if (serviceKeys.containsKey(serviceIp)) {
            return serviceKeys.get(serviceIp);
        } else {
            throw new EncryptionException("Could not find public key in storage for service with IP: " + serviceIp);
        }
    }

    public static void removeKeyForService(String serviceIp) {
        serviceKeys.remove(serviceIp);
    }
}
