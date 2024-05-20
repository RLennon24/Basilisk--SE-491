//This is my code for the user API Integration.
package basilisk.user.servlet;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import basilisk.user.servlet.service.UserService;
import org.springframewwork.beans.facotry.annotation.Autowired;


@RestController("/user")
public class BasiliskServletUserControl {
	@Autowired
	private UserService userService;
}


