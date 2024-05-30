package basilisk.user.servlet;
import basilisk.user.servlet.parsing.DataUnit;
import basilisk.user.servlet.parsing.JsonParseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/insert")
public class BasiliskUserAPI {
	@Autowired
	private JsonParseCache jsonParseCache;

	@PostMapping("/insert")
	public ResponseEntity<String> insertUser(@RequestBody DataUnit dataUnit) {
		jsonParseCache.insertData(dataUnit);
		return ResponseEntity.ok("User data has been successfully inserted");
	}
}
