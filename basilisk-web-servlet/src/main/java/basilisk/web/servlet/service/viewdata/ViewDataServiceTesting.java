package basilisk.web.servlet.service.viewdata;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.message.BaseMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static basilisk.web.servlet.client.UserServletClient.sendMessageToClient;

@Controller
@RequestMapping(path = "/viewData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ViewDataServiceTesting {

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataForId(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data by ID for Service IP: ");

            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(id, request.getRemoteAddr()), request.getRemoteAddr(), "/viewData/id");
            System.out.println("Returning Data for ID: " + id + " and Service IP " + request.getRemoteAddr());
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Service IP: localhost:8009";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @GetMapping(value = "/idStr/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataForIdStr(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data by ID for Service IP: " + request.getRemoteAddr());

            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(id, request.getRemoteAddr()), request.getRemoteAddr(), "/viewData/id");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, request.getRemoteAddr());
            System.out.println("Returning Data for ID: " + id + " and Service IP " + request.getRemoteAddr());
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Service IP: localhost:8009";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping(value = "/role/{role}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataByRole(@PathVariable("role") String role, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Role for Service IP: " + request.getRemoteAddr());

            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(role, request.getRemoteAddr()), request.getRemoteAddr(), "/viewData/role");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, request.getRemoteAddr());
            System.out.println("Returning Data for Role: " + role + " and Service IP " + request.getRemoteAddr());
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Role for Service IP: localhost:8009";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping(value = "/tag/{tag}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataByTag(@PathVariable("tag") String tag, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Tag for Service IP: " + request.getRemoteAddr());

            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(tag, request.getRemoteAddr()), request.getRemoteAddr(), "/viewData/tag");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, request.getRemoteAddr());
            System.out.println("Returning Data for Tag: " + tag + " and Service IP " + request.getRemoteAddr());
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Tag for Service IP: localhost:8009";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
