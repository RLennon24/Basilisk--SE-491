package basilisk.web.servlet.keygen;

import org.junit.Assert;
import org.junit.Test;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

public class KeyGeneratorTest {

    @Test
    public void testGetKeys() {
        Assert.assertNotNull(KeyGenerator.getPrivateKey());
        Assert.assertEquals(RSAPrivateCrtKeyImpl.class, KeyGenerator.getPrivateKey().getClass());
        Assert.assertNotNull(KeyGenerator.getPublicKey());
        Assert.assertEquals(RSAPublicKeyImpl.class, KeyGenerator.getPublicKey().getClass());

        KeyGenerator.clearKeyPair();
        Assert.assertNotNull(KeyGenerator.getPublicKey());
        Assert.assertEquals(RSAPublicKeyImpl.class, KeyGenerator.getPublicKey().getClass());
        Assert.assertNotNull(KeyGenerator.getPrivateKey());
        Assert.assertEquals(RSAPrivateCrtKeyImpl.class, KeyGenerator.getPrivateKey().getClass());

    }
}