package basilisk.web.servlet.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.crypto.SecretKey;
import java.security.PublicKey;

import static org.junit.Assert.assertEquals;


public class KeyRingTest {

    KeyRing keyRing;
    PublicKey expectedKey;

    @Before
    public void setUp() {
        expectedKey = Mockito.mock(PublicKey.class);
        keyRing = new KeyRing("", "", expectedKey);
    }

    @Test
    public void testGetPublicKey() {
        assertEquals(expectedKey, keyRing.getPublicKey());
    }

    @Test
    public void testSessionKey() {
        SecretKey key = Mockito.mock(SecretKey.class);
        keyRing.setSessionKey(key);
        assertEquals(key, keyRing.getSessionKey());
    }

    @Test
    public void testMacKey() {
        SecretKey key = Mockito.mock(SecretKey.class);
        keyRing.setMacKey(key);
        assertEquals(key, keyRing.getMacKey());
    }

    @Test
    public void testEncodingKey() {
        SecretKey key = Mockito.mock(SecretKey.class);
        keyRing.setEncodingKey(key);
        assertEquals(key, keyRing.getEncodingKey());
    }
}