import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class BasiliskKeyExchangeEndpoint {
	
	@GetMapping("/exchange-keys")
	public String keyExchange (@RequestParam(value = "thePublicKey") String thePublicKey) {
		return "Got the public key: " + thePublicKey;
	}
}
