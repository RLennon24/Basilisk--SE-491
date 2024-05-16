package basilisk.user.servlet.keyexchange;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keyexchange.packaging.KeyPackager;
import basilisk.user.servlet.keyexchange.packaging.KeyUnpackager;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class KeyExchangeClient {

    public static void exchangePublicKey() {
        try {
            System.out.println("Sending Public Key to Web Server for Public Key Exchange");
            RestTemplate template = new RestTemplate();

            // send public key to server and await for response
            BaseMessage publicKeyPackage = sendKeyRequest(template, "/publicKey", KeyPackager.generatePublicKeyTransport());
            KeyUnpackager.processPublicKeyPackage(publicKeyPackage);

            if (KeyCache.getServerPublicKey() == null) {
                throw new IllegalStateException("Could not exchange public keys");
            }

            System.out.println("Successfully exchanged public keys with the Server");
            System.out.println("Attempting to exchange symmetric keys with the Server");

            BaseMessage symmetricKeyPackage = sendKeyRequest(template, "/symmetricKey", KeyPackager.generateSymmetricKeyTransport());
            System.out.println("Server: " + EncrypterUtil.decodeMessage(symmetricKeyPackage));
        } catch (Exception e) {
            throw new EncryptionException("Could not exchange keys. Closing connection");
        }
    }

    private static BaseMessage sendKeyRequest(RestTemplate template, String endpoint, BaseMessage keyTransport) {
        HttpEntity<BaseMessage> request = new HttpEntity<>(keyTransport, createHeaders());
        ResponseEntity<BaseMessage> responseEntity = template.postForEntity("http://localhost:8001/keyexchange" + endpoint, request, BaseMessage.class);
        final BaseMessage keyPackage = Optional.ofNullable(responseEntity.getBody()).orElseThrow(() -> new IllegalArgumentException("Response cannot be null"));
        return keyPackage;
    }

    private static MultiValueMap<String, String> createHeaders() {
        MultiValueMap<String, String> defaultHeaders = new LinkedMultiValueMap<>();
        defaultHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        defaultHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return defaultHeaders;
    }
}