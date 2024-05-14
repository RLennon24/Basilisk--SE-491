package basilisk.web.servlet.service.keyexchange;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.security.PublicKey;
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
        PublicKey publicKey = (PublicKey) KeyGenerator.getPublicKey();  // Casting as safely assumed to be PublicKey
        response.put("serverPublicKey", publicKey.toString());  // Assuming toString gives a suitable representation
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestParam("id") String id) {
        try {
            String data = KeyCache.getData(id);
            return ResponseEntity.ok(data);
        } catch (EncryptionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving data: " + e.getMessage());
        }
    }
}



