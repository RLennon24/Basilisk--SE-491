package basilisk.user.servlet.service.viewdata;

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
import java.util.Collections;
import java.util.Set;

@Controller
@RequestMapping(path = "/viewData", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ViewDataService {

    @PostMapping(value = "/id")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataForId(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data by ID");
            String dataId = EncrypterUtil.decodeMessage(transport);
            Set<DataUnit> datUnit = Collections.singleton(JsonParseCache.getById(dataId));
            Gson gson = new Gson();

            BaseMessage baseMessage = BaseMessageBuilder.encodeMessage(gson.toJson(datUnit));
            System.out.println("Returning Data for ID: " + dataId);
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for ID";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping(value = "/role")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataByRole(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Role");
            String roles = EncrypterUtil.decodeMessage(transport);
            String[] rolesArr = roles.split(",");
            Set<DataUnit> units = JsonParseCache.getByRole(rolesArr);
            System.out.println("Found " + units + " for Roles: " + roles);

            Gson gson = new Gson();
            BaseMessage baseMessage = BaseMessageBuilder.encodeMessage(gson.toJson(units));
            System.out.println("Returning Data for Roles: " + roles);
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Role";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }

    @PostMapping(value = "/tag")
    @ResponseBody
    public ResponseEntity<BaseMessage> getDataByTag(@RequestBody BaseMessage transport, HttpServletRequest request) {
        try {
            System.out.println("Received Request to View Data By Tag");
            String tags = EncrypterUtil.decodeMessage(transport);
            String[] tagsArr = tags.split(",");

            Set<DataUnit> units = JsonParseCache.getByTag(tagsArr);
            System.out.println("Found " + units + " for Tag: " + tags);

            Gson gson = new Gson();
            BaseMessage baseMessage = BaseMessageBuilder.encodeMessage(gson.toJson(units));
            System.out.println("Returning Data for Tag: " + tags);
            return ResponseEntity.ok(baseMessage);
        } catch (Exception e) {
            String error = "Could not retrieve data for Tag";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseMessageBuilder.packMessage(error));
        }
    }
}
