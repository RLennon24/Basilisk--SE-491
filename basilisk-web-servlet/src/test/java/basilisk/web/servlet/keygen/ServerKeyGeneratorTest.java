package basilisk.web.servlet.keygen;

import org.junit.Assert;
import org.junit.Test;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

public class ServerKeyGeneratorTest {

    @Test
    public void testGetKeys() {
        Assert.assertNotNull(ServerKeyGenerator.getPrivateKey());
        Assert.assertEquals(RSAPrivateCrtKeyImpl.class, ServerKeyGenerator.getPrivateKey().getClass());
        Assert.assertNotNull(ServerKeyGenerator.getPublicKey());
        Assert.assertEquals(RSAPublicKeyImpl.class, ServerKeyGenerator.getPublicKey().getClass());

        ServerKeyGenerator.clearKeyPair();
        Assert.assertNotNull(ServerKeyGenerator.getPublicKey());
        Assert.assertEquals(RSAPublicKeyImpl.class, ServerKeyGenerator.getPublicKey().getClass());
        Assert.assertNotNull(ServerKeyGenerator.getPrivateKey());
        Assert.assertEquals(RSAPrivateCrtKeyImpl.class, ServerKeyGenerator.getPrivateKey().getClass());

    }
}