package basilisk.web.servlet; 
import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.keygen.KeyGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BasiliskKeyExchangeEndpoint {

	@PostMapping("/exchange-keys")
	public Map<String, String> keyExchange (@RequestBody Map<String, String> request) {
		String clientPublicKey = request.get("Got the public key");
		Key key = KeyGenerator.getPublicKey();
		if (key instanceof PublicKey) {
			PublicKey serverPublicKey = (PublicKey) key;
			KeyCache.addServiceKey("client", serverPublicKey);
		Map<String, String> response = new HashMap<>();
		response.put("The server public key", serverPublicKey.toString());
		return response;
	} else {
		throw new IllegalStateException("Retrieved key isn't of the type PublicKey");
	}
}

	@GetMapping("/api/data/1")
	public String getData(@RequestParam(value = "id", defaultValue = "1") String id) {
		try {
			PublicKey key = KeyCache.getKeyForService("client");
			return "Data for ID: " + id + " with the key: " + key.toString();
		} catch (EncryptionException e) {
		return "The Error: " + e.getMessage();
		}
	}
}

