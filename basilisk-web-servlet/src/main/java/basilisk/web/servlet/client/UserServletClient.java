package basilisk.web.servlet.client;

import basilisk.web.servlet.message.BaseMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class UserServletClient {

    public static BaseMessage sendMessageToClient(RestTemplate template, BaseMessage message, String serviceAddress, String endpoint) {
        System.out.println("Sending message to User Server");
        return sendRequestMessage(template, serviceAddress, endpoint, message);
    }

    private static BaseMessage sendRequestMessage(RestTemplate template, String serviceAddress, String endpoint, BaseMessage keyTransport) {
        HttpEntity<BaseMessage> request = new HttpEntity<>(keyTransport, createHeaders());
        ResponseEntity<BaseMessage> responseEntity = template.postForEntity("http://" + serviceAddress + endpoint, request, BaseMessage.class);
        return Optional.ofNullable(responseEntity.getBody()).orElseThrow(() -> new IllegalArgumentException("Response cannot be null"));
    }

    private static MultiValueMap<String, String> createHeaders() {
        MultiValueMap<String, String> defaultHeaders = new LinkedMultiValueMap<>();
        defaultHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        defaultHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return defaultHeaders;
    }
}
