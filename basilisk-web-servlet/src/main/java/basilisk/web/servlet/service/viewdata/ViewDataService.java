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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/viewData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ViewDataService {

    @PostMapping(value = "/id")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataForId(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data by ID for Service IP: " + request.getRemoteAddr());
            String dataId = EncrypterUtil.decodeMessage(transport, request.getRemoteAddr());
            // TODO: get data from riley
            BaseMessage baseMessage = null;
            System.out.println("Returning Data for ID: " + dataId + " and Service IP " + request.getRemoteAddr());
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID for Service IP: " + request.getRemoteAddr();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping(value = "/role")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataByRole(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Role for Service IP: " + request.getRemoteAddr());
            String role = EncrypterUtil.decodeMessage(transport, request.getRemoteAddr());
            // TODO: get data from riley
            BaseMessage baseMessage = null;
            System.out.println("Returning Data for Role: " + role + " and Service IP " + request.getRemoteAddr());
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Role for Service IP: " + request.getRemoteAddr();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping(value = "/tag")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataByTag(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Tag for Service IP: " + request.getRemoteAddr());
            String role = EncrypterUtil.decodeMessage(transport, request.getRemoteAddr());
            // TODO: get data from riley
            BaseMessage baseMessage = null;
            System.out.println("Returning Data for Tag: " + role + " and Service IP " + request.getRemoteAddr());
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Tag for Service IP: " + request.getRemoteAddr();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }
}
