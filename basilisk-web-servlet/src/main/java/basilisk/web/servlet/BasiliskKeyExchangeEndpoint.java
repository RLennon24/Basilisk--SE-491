import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasiliskKeyExchangeEndpoint {
	
	public String keyExchange (@RequestParam(value = "thePublicKey") String thePublicKey) {
		return "Got the public key: " + thePublicKey;
	}
}
