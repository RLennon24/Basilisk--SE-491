package basilisk.web.servlet.keygen;

import junit.framework.TestCase;
import org.junit.Assert;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

public class KeyGeneratorTest extends TestCase {

    public void testGetPrivateKey() {
        Assert.assertNotNull(KeyGenerator.getPrivateKey());
        Assert.assertEquals(RSAPrivateCrtKeyImpl.class, KeyGenerator.getPrivateKey().getClass());
    }

    public void testGetPublicKey() {
        Assert.assertNotNull(KeyGenerator.getPublicKey());
        Assert.assertEquals(RSAPublicKeyImpl.class, KeyGenerator.getPublicKey().getClass());
    }
}