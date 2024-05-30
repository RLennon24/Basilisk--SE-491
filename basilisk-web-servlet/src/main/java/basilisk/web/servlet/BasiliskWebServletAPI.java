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
//These are the necessary HTTP classes and Spring classes

@RestController
@RequestMapping("/api")

public class BasiliskWebServletAPI {
    @PostMapping("/insert") //This is the POST method, which is here to send data to the user servlet

    public ResponseEntity<String> sendDataToUserServlet(@RequestBody String dataJson) {
        String userServletUrl = "http://localhost:8001/insert"; //This is the URL of the User Servlet
        RestTemplate restTemplate = new RestTemplate(); //Creates a RestTemplate instance. This is for sending HTTP requests
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(dataJson, headers);
        
        // Sending data to the User Servlet
        String response = restTemplate.postForObject(userServletUrl, request, String.class);
        return ResponseEntity.ok("Data successfully reached User Servlet and got a response: " + response); //This is to indicate success from the User servlet
    }
}
