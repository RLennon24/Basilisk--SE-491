package basilisk.user.servlet.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import basilisk.user.servlet.model.BasiliskUser;
import basilisk.user.servlet.repository.BasiliskUserRepository;
@Service
public class BasiliskUserService {
	@Autowired
	private BasiliskUserRepository BasiliskuserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	public boolean register(BasiliskUser Basiliskuser) {
	if (BasiliskuserRepository.findByUsername(Basiliskuser.getUsername()) != null) {
		return false; //This is if the username already exists
	}
	Basiliskuser.setPassword(passwordEncoder.encode(Basiliskuser.getPassword()));
	BasiliskuserRepository.save(Basiliskuser);
	return true;
	}
	public boolean login(String username, String password) {
	BasilsikUser Basiliskuser = BasiliskuserRepository.findByUsername(username);
	if (Basiliskuser != null && passwordEncoder.matches(password, Basiliskuser.getPassword())) {
	return true; //This if if the login is successful
	}
	return false; //This is if the login failed
	}

	public BasiliskUser getUserDetails(String userId) {
		return BasiliskuserRepository.findById(userId).orElse(null);
	}
}


