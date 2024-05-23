package basilisk.web.servlet.service.insertdata;

import basilisk.web.servlet.domain.DataUnit;
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

@Controller
@RequestMapping(path = "/insertData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class InsertDataService {

    @PostMapping
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataForId(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            String serviceAddress = request.getRemoteAddr() + ":" + request.getRemotePort();

            System.out.println("Received Request to Insert Data");
            String dataUnitStr = EncrypterUtil.decodeMessage(transport, serviceAddress);
            DataUnit unit = DataUnit.fromString(dataUnitStr);

            // TODO: probably have to unpack then re-pack the request with servlet's keys
            RestTemplate template = new RestTemplate();
            BaseMessage userServletResponse = sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(unit.toString(), serviceAddress), serviceAddress, "/insertData/id");

            System.out.println("Successfully inserted data. Returning Data for ID: " + unit.getId());
            return ResponseEntity.ok(userServletResponse);
        } catch (Exception e) {
            String error = "Could not insert data";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }
}
