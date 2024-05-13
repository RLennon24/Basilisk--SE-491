import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class BasiliskUserKeyGenTest {

	@Test
	public void testGetUserKeys() {
		BasiliskUserKeyGen.generateKeyPair();
		Assert.assertNotNull(BasiliskUserKeyGen.getUserPrivateKey());
		Assert.assertNotNull(BasiliskUserKeyGen.getUserPublicKey());
		
		BasiliskUserKeyGen.clearKeyPair();
		Assert.assertNotNull(BasiliskUserKeyGen.getUserPrivateKey());
		Assert.assertNotNull(BasiliskUserKeyGen.getUserPublicKey());

	}

}
