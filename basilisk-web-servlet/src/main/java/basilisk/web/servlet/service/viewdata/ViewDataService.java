package basilisk.web.servlet.service.viewdata;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.message.BaseMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static basilisk.web.servlet.client.UserServletClient.sendMessageToClient;
import static basilisk.web.servlet.service.keyexchange.KeyExchangeService.DATA_OWNER_HEADER;

@Controller
@RequestMapping(path = "/viewData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ViewDataService {

    @PostMapping(value = "/id")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataForId(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data by ID for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            String dataId = EncrypterUtil.decodeMessage(transport, request.getHeader(DATA_OWNER_HEADER));

            // TODO: probably have to unpack then re-pack the request with servlet's keys
            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template, BaseMessageBuilder.packMessage(dataId, request.getHeader(DATA_OWNER_HEADER)), request.getHeader(DATA_OWNER_HEADER), "/viewData/id");
            System.out.println("Returning Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Owner: " + request.getHeader(DATA_OWNER_HEADER);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping(value = "/role")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataByRole(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Role for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            String role = EncrypterUtil.decodeMessage(transport, request.getHeader(DATA_OWNER_HEADER));

            // TODO: probably have to unpack then re-pack the request with servlet's keys
            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template, BaseMessageBuilder.packMessage(role, request.getHeader(DATA_OWNER_HEADER)), request.getHeader(DATA_OWNER_HEADER), "/viewData/role");
            System.out.println("Returning Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for Role for Owner: " + request.getHeader(DATA_OWNER_HEADER);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping(value = "/tag")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataByTag(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Tag for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            String tag = EncrypterUtil.decodeMessage(transport, request.getHeader(DATA_OWNER_HEADER));

            // TODO: probably have to unpack then re-pack the request with servlet's keys
            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template, BaseMessageBuilder.packMessage(tag, request.getHeader(DATA_OWNER_HEADER)), request.getHeader(DATA_OWNER_HEADER), "/viewData/tag");
            System.out.println("Returning Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for Tag for Owner: " + request.getHeader(DATA_OWNER_HEADER);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }
}
