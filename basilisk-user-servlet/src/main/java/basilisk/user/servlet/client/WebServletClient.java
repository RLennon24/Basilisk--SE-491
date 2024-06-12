package basilisk.user.servlet.client;

import basilisk.user.servlet.message.BaseMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class WebServletClient {

    public static BaseMessage sendRequest(RestTemplate template, String owner, String endpoint, BaseMessage keyTransport) {
        HttpEntity<BaseMessage> request = new HttpEntity<>(keyTransport, createHeaders(owner));
        ResponseEntity<BaseMessage> responseEntity = template.postForEntity(endpoint, request, BaseMessage.class);
        final BaseMessage keyPackage = Optional.ofNullable(responseEntity.getBody()).orElseThrow(() -> new IllegalArgumentException("Response cannot be null"));
        return keyPackage;
    }

    private static MultiValueMap<String, String> createHeaders(String owner) {
        MultiValueMap<String, String> defaultHeaders = new LinkedMultiValueMap<>();
        defaultHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        defaultHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        defaultHeaders.add("DATA-OWNER", owner);
        return defaultHeaders;
    }
}
