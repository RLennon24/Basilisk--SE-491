package basilisk.user.servlet.keyexchange;

import basilisk.user.servlet.client.WebServletClient;
import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keyexchange.packaging.KeyPackager;
import basilisk.user.servlet.keyexchange.packaging.KeyUnpackager;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class KeyExchangeClient {
    @Autowired
    private static Environment env;

    public static void exchangePublicKey(RestTemplate template, String owner, String keyExchangeEndpoint) {
        try {
            System.out.println("Sending Public Key to Web Server for Public Key Exchange");

            // send public key to server and await for response
            BaseMessage publicKeyPackage = WebServletClient.sendRequest(template, owner, keyExchangeEndpoint + "/publicKey", KeyPackager.generatePublicKeyTransport());
            KeyUnpackager.processPublicKeyPackage(publicKeyPackage);

            if (KeyCache.getServerPublicKey() == null) {
                throw new IllegalStateException("Could not exchange public keys");
            }

            System.out.println("Successfully exchanged public keys with the Server");
            System.out.println("Attempting to exchange symmetric keys with the Server");

            BaseMessage symmetricKeyPackage = WebServletClient.sendRequest(template, owner, keyExchangeEndpoint + "/symmetricKey", KeyPackager.generateSymmetricKeyTransport());
            System.out.println("Server: " + EncrypterUtil.decodeMessage(symmetricKeyPackage));
        } catch (Exception e) {
            throw new EncryptionException("Could not exchange keys. Closing connection");
        }
    }


}