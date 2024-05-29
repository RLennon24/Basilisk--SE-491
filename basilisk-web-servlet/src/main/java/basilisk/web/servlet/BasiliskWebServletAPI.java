package basilisk.web.servlet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
@Controller
public class BasiliskWebServletAPI {
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("A message", "Hello, this is the Web Servlet!");
		return "index";
	}

	@PostMapping("/submit")
	public ResponseEntity<String> submitData(@RequestBody String data) {
		return ResponseEntity.ok("Data finished: " + data);
	}

	@GetMapping("/fetch")
	@ResponseBody
	public ResponseEntity<String> fetchData() {
		String data = "Data from the web servlet";
		return ResponseEntity.ok(data);
	}
}
