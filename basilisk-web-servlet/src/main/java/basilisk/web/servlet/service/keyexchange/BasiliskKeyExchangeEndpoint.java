//Below is my code for the Web Servlet - Zachary Wile
package basilisk.web.servlet.service.keyexchange;

import basilisk.web.servlet.keygen.ServerKeyGenerator;
import basilisk.web.servlet.service.keyexchange.packaging.KeyPackage;
import basilisk.web.servlet.service.keyexchange.packaging.KeyPackager;
import basilisk.web.servlet.service.keyexchange.packaging.KeyUnpackager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.security.PublicKey;
import java.security.Key;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.exception.EncryptionException;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping(path = "/keyexchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BasiliskKeyExchangeEndpoint {

    @PostMapping("/publicKey")
    public ResponseEntity<KeyPackage> exchangePublicKey(@RequestBody KeyPackage transport) {
        try {
            KeyUnpackager.processPublicKeyPackage(transport);
            KeyPackage keyPackage = KeyPackager.generatePublicKeyTransport();
            return ResponseEntity.ok(keyPackage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/symmetricKey")
    public ResponseEntity<String> exchangeKeys(@RequestBody KeyPackage transport) {
        KeyUnpackager.processPublicKeyPackage(transport);
        Key key = ServerKeyGenerator.getPublicKey(); // This will retrieve the key as its superclass type
        if (!(key instanceof PublicKey)) {
            // This will be used to specify explicity the generic type for ResponseEntity
            return ResponseEntity.<String>status(HttpStatus.INTERNAL_SERVER_ERROR).body("Expected a PublicKey but received a different type.");
        }
        PublicKey publicKey = (PublicKey) key; // Safely cast to PublicKey
        Map<String, Object> response = new HashMap<>();
        response.put("serverPublicKey", publicKey.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestParam("id") String serviceIp) {
        try {
            PublicKey key = (PublicKey) KeyCache.getEncodingKeyForService(serviceIp); // KeyCache returns PublicKey and then cast if necessary.
            return ResponseEntity.ok("Public key for service IP " + serviceIp + ": " + key.toString());
        } catch (EncryptionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Key type error: " + e.getMessage());
        }
    }
}
