package basilisk.web.servlet.service.insert;

import basilisk.web.servlet.domain.DataUnit;
import basilisk.web.servlet.idgen.IdGeneration;
import basilisk.web.servlet.message.BaseMessageBuilder;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static basilisk.web.servlet.client.UserServletClient.sendMessageToClient;
import static basilisk.web.servlet.service.keyexchange.KeyExchangeService.DATA_OWNER_HEADER;

@RestController
@RequestMapping("/insert")
public class InsertDataService {

    @PostMapping
    public ResponseEntity<String> sendDataToUserServlet(@RequestBody DataUnit dataUnit, HttpServletRequest request) {
        JsonObject object = new JsonObject();
        HttpStatus status = HttpStatus.OK;

        try {
            System.out.println("Received Request to Insert Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));
            dataUnit.setId(IdGeneration.generateId(dataUnit));
            String jsonDataUnit = dataUnit.toString();

            RestTemplate template = new RestTemplate();
            sendMessageToClient(template,
                    BaseMessageBuilder.packMessage(jsonDataUnit, request.getHeader(DATA_OWNER_HEADER)),
                    request.getHeader(DATA_OWNER_HEADER), "/insert");
            System.out.println("Inserted Data for Owner: " + request.getHeader(DATA_OWNER_HEADER));

            object.addProperty("status", "Success");
        } catch (Exception e) {
            String error = "Could not insert data for Owner: " + request.getHeader(DATA_OWNER_HEADER);
            object.addProperty("status", error);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(object.toString());
    }
}
