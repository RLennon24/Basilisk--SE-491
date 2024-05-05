package basilisk.web.servlet.keygen;

import basilisk.web.servlet.exception.EncryptionException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.PublicKey;

public class KeyCacheTest extends TestCase {

    @Test
    public void testKeyCache() {
        PublicKey expectedKey = Mockito.mock(PublicKey.class);

        EncryptionException ex = Assert.assertThrows(EncryptionException.class, () -> {
            KeyCache.getKeyForService("1.1.1.1");
        });
        assertEquals("Could not find public key in storage for service with IP: 1.1.1.1", ex.getMessage());

        KeyCache.addServiceKey("1.1.1.1", expectedKey);
        PublicKey actualKey = KeyCache.getKeyForService("1.1.1.1");
        assertEquals(expectedKey, actualKey);

        KeyCache.removeKeyForService("1.1.1.1");
        ex = Assert.assertThrows(EncryptionException.class, () -> {
            KeyCache.getKeyForService("1.1.1.1");
        });
        assertEquals("Could not find public key in storage for service with IP: 1.1.1.1", ex.getMessage());
    }
}