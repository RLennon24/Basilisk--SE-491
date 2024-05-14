package basilisk.web.servlet.service.keyexchange;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.security.PublicKey;
import java.security.Key;
import basilisk.web.servlet.keygen.KeyGenerator;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.exception.EncryptionException;
import java.util.Map;
import java.util.HashMap;

@RestController
public class BasiliskKeyExchangeEndpoint {

    @PostMapping("/exchange-keys")
    public ResponseEntity<Map<String, Object>> exchangeKeys(@RequestBody Map<String, String> requestData) {
        Map<String, Object> response = new HashMap<>();
        Key key = KeyGenerator.getPublicKey(); // Retrieve the key as its superclass type
        if (key instanceof PublicKey) {
            PublicKey publicKey = (PublicKey) key; // Safely cast to PublicKey
            response.put("serverPublicKey", publicKey.toString());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Expected a PublicKey but received a different type.");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestParam("id") String serviceIp) {
        try {
            PublicKey key = (PublicKey) KeyCache.getKeyForService(serviceIp); // Assuming KeyCache returns PublicKey, cast if necessary
            return ResponseEntity.ok("Public key for service IP " + serviceIp + ": " + key.toString());
        } catch (EncryptionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Key type error: " + e.getMessage());
        }
    }
}


