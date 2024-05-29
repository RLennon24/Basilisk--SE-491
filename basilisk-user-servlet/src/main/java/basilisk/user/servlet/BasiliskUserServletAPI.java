package basilisk.user.servlet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import basilisk.user.servlet.service.BasiliskUserService;
import basilisk.user.servlet.model.BasiliskUser;

@RestController
@RequestMapping("/user")
public class BasiliskUserServletAPI {

	@Autowired
	private BasiliskUserService BasiliskuserService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody BasiliskUser Basiliskuser) {
		boolean result = BasiliskuserService.register(Basiliskuser);
		if (result) {
			return ResponseEntity.ok("Successfully registered this user");
		} else {
			return ResponseEntity.badRequest().body("Failed to register");
	}
}
@PostMapping("/login")
public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
	boolean result = BasiliskuserService.login(username, password);
	if (result) {
		return ResponseEntity.ok("Successfully logged user in");
	} else {
		return ResponseEntity.status(401).body("Login failed");
	}
}
@GetMapping("/profile/{userId}")
public ResponseEntity<User> getUserProfile(@PathVariable String userId) {
	BasiliskUser Basiliskuser = BasiliskuserService.getUserDetails(userId);
	if (user != null) {
		return ResponseEntity.ok(user);
	} else {
		return ResponseEntity.notFound().build();
	}
	}
}
