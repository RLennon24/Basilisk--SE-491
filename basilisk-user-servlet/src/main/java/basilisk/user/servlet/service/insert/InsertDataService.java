package basilisk.user.servlet.service.insert;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.message.BaseMessage;
import basilisk.user.servlet.message.BaseMessageBuilder;
import basilisk.user.servlet.parsing.DataUnit;
import basilisk.user.servlet.parsing.JsonParseCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insert")
public class InsertDataService {

    @PostMapping
    @ResponseBody
    public ResponseEntity<BaseMessage> insertData(@RequestBody BaseMessage transport) {
        try {
            System.out.println("Received Request to Insert Data");
            String dataUnitStr = EncrypterUtil.decodeMessage(transport);

            DataUnit dataUnit = DataUnit.fromString(dataUnitStr);
            JsonParseCache.insertData(dataUnit);

            System.out.println("User data has been successfully inserted");
            BaseMessage baseMessage = BaseMessageBuilder.encodeMessage("User data has been successfully inserted");

            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }
}
