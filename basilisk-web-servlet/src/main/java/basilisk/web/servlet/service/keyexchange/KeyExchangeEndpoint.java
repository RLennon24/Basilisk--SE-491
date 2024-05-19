//Below is my code for the Web Servlet - Zachary Wile
package basilisk.web.servlet.service.keyexchange;

import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.message.BaseMessageBuilder;
import basilisk.web.servlet.service.keyexchange.packaging.KeyPackager;
import basilisk.web.servlet.service.keyexchange.packaging.KeyUnpackager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/keyexchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class KeyExchangeEndpoint {

    @PostMapping("/publicKey")
    public ResponseEntity<BaseMessage> exchangePublicKey(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to Exchange Public Keys for address: " + request.getRemoteAddr() + ":" + request.getRemotePort());
            KeyUnpackager.processPublicKeyPackage(request.getRemoteAddr() + ":" + request.getRemotePort(), transport);

            BaseMessage baseMessage = KeyPackager.generatePublicKeyTransport();
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not parse Public Key";
            System.err.println(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping("/symmetricKey")
    public ResponseEntity<BaseMessage> exchangeKeys(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to Exchange Symmetric Keys for address: " + request.getRemoteAddr() + ":" + request.getRemotePort());
            KeyUnpackager.processSymmetricKeyPackage(request.getRemoteAddr() + ":" + request.getRemotePort(), transport);
            return ResponseEntity.ok(BaseMessageBuilder.packMessage("Received Keys", request.getRemoteAddr() + ":" + request.getRemotePort()));
        } catch (Exception e) {
            String error = "Could not parse Symmetric Key";
            System.err.println(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }
}
