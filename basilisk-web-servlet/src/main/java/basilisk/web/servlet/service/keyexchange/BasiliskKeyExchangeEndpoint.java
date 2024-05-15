//Below is my code for the Web Servlet - Zachary Wile
package basilisk.web.servlet.service.keyexchange;

import basilisk.web.servlet.exception.EncryptionException;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.service.keyexchange.packaging.KeyPackager;
import basilisk.web.servlet.service.keyexchange.packaging.KeyUnpackager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

@RestController
@RequestMapping(path = "/keyexchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BasiliskKeyExchangeEndpoint {

    @PostMapping("/publicKey")
    public ResponseEntity<BaseMessage> exchangePublicKey(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to Exchange Public Keys for address: " + request.getRemoteAddr());
            KeyUnpackager.processPublicKeyPackage(request.getRemoteAddr(), transport);
            BaseMessage baseMessage = KeyPackager.generatePublicKeyTransport();
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/symmetricKey")
    public ResponseEntity<String> exchangeKeys(@RequestBody BaseMessage transport) {
        try {
            KeyUnpackager.processSymmetricKeyPackage("", transport);
            return ResponseEntity.ok("Received Symmetric Key");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not receive symmetric key");
        }
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
