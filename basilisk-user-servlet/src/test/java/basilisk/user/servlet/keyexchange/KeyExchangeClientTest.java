package basilisk.user.servlet.keyexchange;

import basilisk.user.servlet.keyexchange.packaging.KeyPackager;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;
import basilisk.user.servlet.message.BaseMessageBuilder;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class KeyExchangeClientTest {

    @Test
    public void testExchangePublicKey() {
        RestTemplate restTemplate = new RestTemplate();
        BasiliskUserKeyGen.generateKeyPair();

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            //generate keys
            KeyCache.setServerPublicKey(keyGen.generateKeyPair().getPublic());
        } catch (Exception e) {
            System.out.println("Could not generate User Keys");
        }

        KeyPackager.generateSymmetricKeyTransport();

        BaseMessage message = new BaseMessage();
        message.setMessage("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxNS4nB5Ochyob2u2d02tQGe5tNaN/YpLuyb2aZwWppAl4tPz3i6iqlGlRbHrf241kRJYPLuf4Bi+DcY+f5zuQRyY7aK0yC2FywDpw98EP3OIFlbsVwe7SkPxAZuj3owhl/JjXvFwk/CRDbsVGJZACf2Ekq2aprc/K5WjQEYWJUlzhWGe8dIvJtbNoBR+ozDJ5euXtnMgVKddR0PM60tw9mDl/mPbjNF/gz8I/hX0rlK6+HXLHWXkdl+uJjWxJo5hApkcAAdaCEAO0u+jan+a2NgCPwNaUM02hDgKc+a5dXLGg80MpGMHgvGpdqODBv/856CPbuYUNjNBZx6MLnnp6QIDAQAB");
        message.setSignature("CVA5wVSFlK55P2dFbZ2h0kwtEDegDe7UgB5o0CeSV5rS2ggtqep6T8CaES7ymC8Zn8VcmeUYXR+w3EdD6LYNulGEFWy0gEVNMxFOmbvEkh/WzYAnv1Xn52B070RV2Saz4r4VKmGudzZmQ2tDVmqPMyoxGD5O7FPsHE/Wt+pe6w0Ugs6E7BL8R8YXzHP+6s5jg32n0rf8tny6Aojs9plDv0VeiunM94193dJW5UD4bTJrREYWf5AnTQja3Gi5OvTydQtz+1Wa5wT/twUftA46nFIQTl4MBsgoQvwDuTfgXHPk25+Unee9RatLOIKS2aGccbGJBkO0tlSSvEigSTF9kQ==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));
        Gson gson = new Gson();

        MockRestServiceServer mockServer =
                MockRestServiceServer.bindTo(restTemplate).build();
        mockServer.expect(requestTo("http://localhost:8001/keyexchange/publicKey"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(gson.toJson(message)));


        BaseMessage response = BaseMessageBuilder.encodeMessage("Received");
        mockServer.expect(requestTo("http://localhost:8001/keyexchange/symmetricKey"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(gson.toJson(response)));

        KeyExchangeClient.exchangePublicKey(restTemplate, "me", "http://localhost:8001/keyexchange");

        mockServer.verify();
    }
}