package basilisk.web.servlet.keygen;

import basilisk.web.servlet.exception.EncryptionException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.crypto.SecretKey;
import java.security.PublicKey;

import static org.junit.Assert.assertEquals;

public class KeyCacheTest {

    @Test
    public void testKeyCache() {
        // PUBLIC KEY
        PublicKey expectedPKey = Mockito.mock(PublicKey.class);

        EncryptionException ex = Assert.assertThrows(EncryptionException.class, () -> {
            KeyCache.getPublicKeyForService("1.1.1.1");
        });
        assertEquals("Could not find public key in storage for Owner: 1.1.1.1", ex.getMessage());

        KeyCache.addServicePublicKey("1.1.1.1","", expectedPKey);
        PublicKey actualPKey = KeyCache.getPublicKeyForService("1.1.1.1");
        assertEquals(expectedPKey, actualPKey);

        // REMOVE
        KeyCache.removeKeyForService("1.1.1.1");
        ex = Assert.assertThrows(EncryptionException.class, () -> {
            KeyCache.getPublicKeyForService("1.1.1.1");
        });
        assertEquals("Could not find public key in storage for Owner: 1.1.1.1", ex.getMessage());

        // SESSION KEY
        SecretKey expectedSKey = Mockito.mock(SecretKey.class);

        ex = Assert.assertThrows(EncryptionException.class, () -> {
            KeyCache.getSessionKeyForService("1.1.1.1");
        });
        assertEquals("Could not find session key in storage for Owner: 1.1.1.1", ex.getMessage());

        KeyCache.addServicePublicKey("1.1.1.1", "",expectedPKey);
        KeyCache.addServiceSessionKey("1.1.1.1", expectedSKey);
        SecretKey actualSKey = KeyCache.getSessionKeyForService("1.1.1.1");
        assertEquals(expectedSKey, actualSKey);

        // REMOVE
        KeyCache.removeKeyForService("1.1.1.1");

        // MAC KEY
        ex = Assert.assertThrows(EncryptionException.class, () -> {
            KeyCache.getEncodingKeyForService("1.1.1.1");
        });
        assertEquals("Could not find encoding key in storage for Owner: 1.1.1.1", ex.getMessage());

        KeyCache.addServicePublicKey("1.1.1.1", "",expectedPKey);
        KeyCache.addServiceEncodingKey("1.1.1.1", expectedSKey);
        actualSKey = KeyCache.getEncodingKeyForService("1.1.1.1");
        assertEquals(expectedSKey, actualSKey);

        // REMOVE
        KeyCache.removeKeyForService("1.1.1.1");

        // ENCODING KEY
        ex = Assert.assertThrows(EncryptionException.class, () -> {
            KeyCache.getMacKeyForService("1.1.1.1");
        });
        assertEquals("Could not find mac key in storage for Owner: 1.1.1.1", ex.getMessage());

        KeyCache.addServicePublicKey("1.1.1.1", "",expectedPKey);
        KeyCache.addServiceMacKey("1.1.1.1", expectedSKey);
        actualSKey = KeyCache.getMacKeyForService("1.1.1.1");
        assertEquals(expectedSKey, actualSKey);
    }
}