package basilisk.web.servlet;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class BasiliskWebServletAPI {

    @PostMapping("/insert")
    public ResponseEntity<String> sendDataToUserServlet(@RequestBody String dataJson) {
        String userServletUrl = "http://localhost:8001/insert";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(dataJson, headers);
        
        // Sending data to the User Servlet
        String response = restTemplate.postForObject(userServletUrl, request, String.class);
        return ResponseEntity.ok("Data forwarded to User Servlet and received response: " + response);
    }
}
