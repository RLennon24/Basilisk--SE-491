package basilisk.user.servlet.service.insertdata;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.message.BaseMessage;
import basilisk.user.servlet.message.BaseMessageBuilder;
import basilisk.user.servlet.parsing.DataUnit;
import basilisk.user.servlet.parsing.JsonParseCache;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "/insertData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class InsertDataService {

    @PostMapping
    @ResponseBody
    public ResponseEntity<BaseMessage> insertData(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to Insert Data");
            String dataUnitStr = EncrypterUtil.decodeMessage(transport);
            DataUnit unit = DataUnit.fromString(dataUnitStr);
            BaseMessage baseMessage = BaseMessageBuilder.encodeMessage(unit.toString());
            System.out.println("Successfully inserted data. Returning Data for ID: " + unit.getId());
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not insert data";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }
}
