package basilisk.web.servlet.service.viewdata;

import basilisk.web.servlet.encryption.EncrypterUtil;
import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.message.BaseMessageBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static basilisk.web.servlet.client.UserServletClient.sendMessageToClient;

@Controller
@RequestMapping(path = "/viewData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ViewDataService {

    @PostMapping(value = "/id")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataForId(@RequestBody BaseMessage transport, HttpServletRequest request) {
        String serviceAddress = request.getRemoteAddr() + ":" + request.getRemotePort();

        try {
            System.out.println("Received Request to View Data by ID for Service IP: " + serviceAddress);
            String dataId = EncrypterUtil.decodeMessage(transport, serviceAddress);

            // TODO: probably have to unpack then re-pack the request with servlet's keys
            BaseMessage userServletResponse = sendMessageToClient(BaseMessageBuilder.packMessage(dataId, serviceAddress), serviceAddress, "/viewData/id");
            System.out.println("Returning Data for ID: " + dataId + " and Service IP " + serviceAddress);
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Service IP: " + serviceAddress;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping(value = "/role")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataByRole(@RequestBody BaseMessage transport, HttpServletRequest request) {
        String serviceAddress = request.getRemoteAddr() + ":" + request.getRemotePort();

        try {
            System.out.println("Received Request to View Data By Role for Service IP: " + serviceAddress);
            String role = EncrypterUtil.decodeMessage(transport, serviceAddress);

            // TODO: probably have to unpack then re-pack the request with servlet's keys
            BaseMessage userServletResponse = sendMessageToClient(BaseMessageBuilder.packMessage(role, serviceAddress), serviceAddress, "/viewData/role");
            System.out.println("Returning Data for Role: " + role + " and Service IP " + serviceAddress);
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for Role for Service IP: " + serviceAddress;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping(value = "/tag")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataByTag(@RequestBody BaseMessage transport, HttpServletRequest request) {
        String serviceAddress = request.getRemoteAddr() + ":" + request.getRemotePort();

        try {
            System.out.println("Received Request to View Data By Tag for Service IP: " + serviceAddress);
            String tag = EncrypterUtil.decodeMessage(transport, serviceAddress);

            // TODO: probably have to unpack then re-pack the request with servlet's keys
            BaseMessage userServletResponse = sendMessageToClient(BaseMessageBuilder.packMessage(tag, serviceAddress), serviceAddress, "/viewData/tag");
            System.out.println("Returning Data for Tag: " + tag + " and Service IP " + serviceAddress);
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for Tag for Service IP: " + serviceAddress;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataForId(@PathVariable("id") String id) {
        try {
            System.out.println("Received Request to View Data by ID for Service IP: 127.0.0.1:50349");

            BaseMessage userServletResponse = sendMessageToClient(BaseMessageBuilder.packMessage(id, "localhost:8009"), "localhost:8009", "/viewData/id");
            System.out.println("Returning Data for ID: " + id + " and Service IP " + "localhost:8009");
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Service IP: localhost:8009";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @GetMapping(value = "/idStr/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataForIdStr(@PathVariable("id") String id) {
        try {
            System.out.println("Received Request to View Data by ID for Service IP: localhost:8009");

            BaseMessage userServletResponse = sendMessageToClient(BaseMessageBuilder.packMessage(id, "localhost:8009"), "localhost:8009", "/viewData/id");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, "localhost:8009");
            System.out.println("Returning Data for ID: " + id + " and Service IP " + "localhost:8009");
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Service IP: localhost:8009";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping(value = "/role/{role}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataByRole(@PathVariable("role") String role) {
        try {
            System.out.println("Received Request to View Data By Role for Service IP: localhost:8009");

            BaseMessage userServletResponse = sendMessageToClient(BaseMessageBuilder.packMessage(role, "localhost:8009"), "localhost:8009", "/viewData/role");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, "localhost:8009");
            System.out.println("Returning Data for Role: " + role + " and Service IP " + "localhost:8009");
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Role for Service IP: localhost:8009";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping(value = "/tag/{tag}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> getDataByTag(@PathVariable("tag") String tag) {
        try {
            System.out.println("Received Request to View Data By Tag for Service IP: localhost:8009");

            BaseMessage userServletResponse = sendMessageToClient(BaseMessageBuilder.packMessage(tag, "localhost:8009"), "localhost:8009", "/viewData/tag");
            String decodedMessage = EncrypterUtil.decodeMessage(userServletResponse, "localhost:8009");
            System.out.println("Returning Data for Tag: " + tag + " and Service IP " + "localhost:8009");
            return ResponseEntity.ok(decodedMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Tag for Service IP: localhost:8009";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
