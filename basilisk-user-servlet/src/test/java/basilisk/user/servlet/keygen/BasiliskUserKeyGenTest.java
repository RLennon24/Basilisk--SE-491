package basilisk.user.servlet.keygen;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;


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

	@Test
	public void testStoreKeys() {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		File folder = null;
		try {
			folder = new File(classLoader.getResource("keys").toURI());
			if (!folder.exists() || !folder.isDirectory()) {
				throw new RuntimeException("Key path: " + folder.getPath() + " is not a folder/does not exist");
			}

			// delete existing data
			File[] currFiles = folder.listFiles();
			Assertions.assertNotNull(currFiles);
			Arrays.stream(currFiles).forEach(File::delete);

			currFiles = folder.listFiles();
			Assertions.assertEquals(currFiles.length, 0);

			BasiliskUserKeyGen.storeUserKeys();

			currFiles = folder.listFiles();
			Assertions.assertNotNull(currFiles);
			Assertions.assertEquals(2, currFiles.length);
		} catch (URISyntaxException e) {
			Assertions.fail();
		}
	}

}
