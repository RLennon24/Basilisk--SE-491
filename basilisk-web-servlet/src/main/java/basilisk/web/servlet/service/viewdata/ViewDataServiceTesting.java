package basilisk.web.servlet.service.viewdata;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.message.BaseMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static basilisk.web.servlet.client.UserServletClient.sendMessageToClient;
import static basilisk.web.servlet.service.keyexchange.KeyExchangeService.DATA_OWNER_HEADER;

@Controller
@RequestMapping(path = "/viewData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ViewDataServiceTesting {

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataForId(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data by ID for Owner: " + request.getHeader(DATA_OWNER_HEADER));

            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(id, request.getHeader(DATA_OWNER_HEADER)),
                    request.getHeader(DATA_OWNER_HEADER), "/viewData/id");
            System.out.println("Returning Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Owner: " + request.getHeader(DATA_OWNER_HEADER);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @GetMapping(value = "/idStr/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataForIdStr(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data by ID for Owner: " + request.getHeader(DATA_OWNER_HEADER));

            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(id, request.getHeader(DATA_OWNER_HEADER)),
                    request.getHeader(DATA_OWNER_HEADER), "/viewData/id");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, request.getHeader(DATA_OWNER_HEADER));
            System.out.println("Returning Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Owner: " + request.getHeader(DATA_OWNER_HEADER);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping(value = "/role/{role}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataByRole(@PathVariable("role") String role, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Role for Owner: " + request.getHeader(DATA_OWNER_HEADER));

            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(role, request.getHeader(DATA_OWNER_HEADER)),
                    request.getHeader(DATA_OWNER_HEADER), "/viewData/role");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, request.getHeader(DATA_OWNER_HEADER));
            System.out.println("Returning Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Role for Owner: " + request.getHeader(DATA_OWNER_HEADER);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping(value = "/tag/{tag}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataByTag(@PathVariable("tag") String tag, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Tag for Owner: " + request.getHeader(DATA_OWNER_HEADER));

            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(tag, request.getHeader(DATA_OWNER_HEADER)),
                    request.getHeader(DATA_OWNER_HEADER), "/viewData/tag");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, request.getHeader(DATA_OWNER_HEADER));
            System.out.println("Returning Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Tag for Owner: " + request.getHeader(DATA_OWNER_HEADER);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
