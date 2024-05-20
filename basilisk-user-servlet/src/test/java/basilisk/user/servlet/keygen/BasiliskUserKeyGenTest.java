package basilisk.user.servlet.keygen;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;


class BasiliskUserKeyGenTest {

	@Test
	public void testGetUserKeys() {
		BasiliskUserKeyGen.generateKeyPair();
		Assert.assertNotNull(BasiliskUserKeyGen.getUserPrivateKey());
		Assert.assertEquals(RSAPrivateCrtKeyImpl.class, BasiliskUserKeyGen.getUserPrivateKey().getClass());
		Assert.assertNotNull(BasiliskUserKeyGen.getUserPublicKey());
		Assert.assertEquals(RSAPublicKeyImpl.class, BasiliskUserKeyGen.getUserPublicKey().getClass());

		BasiliskUserKeyGen.clearKeyPair();
		Assert.assertNotNull(BasiliskUserKeyGen.getUserPublicKey());
		Assert.assertEquals(RSAPublicKeyImpl.class, BasiliskUserKeyGen.getUserPublicKey().getClass());
		Assert.assertNotNull(BasiliskUserKeyGen.getUserPrivateKey());
		Assert.assertEquals(RSAPrivateCrtKeyImpl.class, BasiliskUserKeyGen.getUserPrivateKey().getClass());
	}

}
