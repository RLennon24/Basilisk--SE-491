package basilisk.user.servlet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import basilisk.user.servlet.service.UserService;
import basilisk.user.servlet.model.User;

@RestController
@RequestMapping("/user")
public class BasiliskWebServletAPI {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		boolean result = userService.register(user);
		if (result) {
			return ResponseEntity.ok("Successfully registered this user");
		} else {
			return ResponseEntity.badRequest().body("Failed to register");
	}
}
@PostMapping("/login")
public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
	boolean result = userService.login(username, password);
	if (result) {
		return ResponseEntity.ok("Successfully logged user in");
	} else {
		return ResponseEntity.status(401).body("Login failed");
	}
}
@GetMapping("/profile/{userId}")
public ResponseEntity<User> getUserProfile(@PathVariable String userId) {
	User user = userService.getUserDetails(userId);
	if (user != null) {
		return ResponseEntity.ok(user);
	} else {
		return ResponseEntity.notFound().build();
	}
	}
}
