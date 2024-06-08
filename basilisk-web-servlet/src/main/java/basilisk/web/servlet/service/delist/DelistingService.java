package basilisk.web.servlet.service.delist;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.message.BaseMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static basilisk.web.servlet.service.keyexchange.KeyExchangeService.DATA_OWNER_HEADER;

@RestController
@RequestMapping(path = "/delist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class DelistingService {

    @PostMapping()
    public ResponseEntity<BaseMessage> delistKeys(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to Delist Keys for owner: " + request.getHeader(DATA_OWNER_HEADER));
            String message = EncrypterUtil.decodeMessage(transport, request.getHeader(DATA_OWNER_HEADER));

            BaseMessage response;
            if (message.equals("DELIST")) {
                response = BaseMessageBuilder.packMessage("Delisted Keys", request.getHeader(DATA_OWNER_HEADER));
                KeyCache.removeKeyForService(request.getHeader(DATA_OWNER_HEADER));
                System.out.println("De-Listed Keys for owner: " + request.getHeader(DATA_OWNER_HEADER));
            } else {
                response = BaseMessageBuilder.packMessage("Received Non De-List request", request.getHeader(DATA_OWNER_HEADER));
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            String error = "Could not parse request";
            System.err.println(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }
}
