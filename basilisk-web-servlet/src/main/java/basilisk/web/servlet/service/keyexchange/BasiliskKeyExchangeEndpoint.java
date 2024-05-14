package basilisk.web.servlet.service.keyexchange;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;

@RestController
public class BasiliskKeyExchangeEndpoint {

    private final KeyGenerator keyGenerator; // Assuming there's a KeyGenerator service

    public BasiliskKeyExchangeEndpoint(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @PostMapping("/exchange-keys")
    public ResponseEntity<Map<String, Object>> keyExchange(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        PublicKey publicKey = keyGenerator.getPublicKey(); // This method gets the server's public key
        // The Public Key is now more suitable for JSON
        response.put("serverPublicKey", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        return ResponseEntity.ok(response);
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

