//The Test code for the Web Servlet. -Zach
package basilisk.web.servlet.service.keyexchange;

import basilisk.web.servlet.message.BaseMessage;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static basilisk.web.servlet.service.keyexchange.KeyExchangeService.DATA_OWNER_HEADER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KeyExchangeService.class)
//This is to indicate that the test class is specifically for testing the BasiliskWebServletApp class
public class KeyExchangeServiceTest {

    @Autowired
    private MockMvc mockMvc;
    //The MockMvc gives methods that can be used to test endpoints without a complete server startup

    @Test
    //For the POST endpoint handling key Exchange
    public void testExchangePublicKey() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxNS4nB5Ochyob2u2d02tQGe5tNaN/YpLuyb2aZwWppAl4tPz3i6iqlGlRbHrf241kRJYPLuf4Bi+DcY+f5zuQRyY7aK0yC2FywDpw98EP3OIFlbsVwe7SkPxAZuj3owhl/JjXvFwk/CRDbsVGJZACf2Ekq2aprc/K5WjQEYWJUlzhWGe8dIvJtbNoBR+ozDJ5euXtnMgVKddR0PM60tw9mDl/mPbjNF/gz8I/hX0rlK6+HXLHWXkdl+uJjWxJo5hApkcAAdaCEAO0u+jan+a2NgCPwNaUM02hDgKc+a5dXLGg80MpGMHgvGpdqODBv/856CPbuYUNjNBZx6MLnnp6QIDAQAB");
        message.setSignature("CVA5wVSFlK55P2dFbZ2h0kwtEDegDe7UgB5o0CeSV5rS2ggtqep6T8CaES7ymC8Zn8VcmeUYXR+w3EdD6LYNulGEFWy0gEVNMxFOmbvEkh/WzYAnv1Xn52B070RV2Saz4r4VKmGudzZmQ2tDVmqPMyoxGD5O7FPsHE/Wt+pe6w0Ugs6E7BL8R8YXzHP+6s5jg32n0rf8tny6Aojs9plDv0VeiunM94193dJW5UD4bTJrREYWf5AnTQja3Gi5OvTydQtz+1Wa5wT/twUftA46nFIQTl4MBsgoQvwDuTfgXHPk25+Unee9RatLOIKS2aGccbGJBkO0tlSSvEigSTF9kQ==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/keyexchange/publicKey")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(DATA_OWNER_HEADER, "TEST")
                        .content(gson.toJson(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());

    }

    @Test
    //For the POST endpoint handling key Exchange
    public void testExchangeKey() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("aw6FHmUkkjrbMAPSBYl0jtyO5mO0TTQF28Zou8dycl+tgaOZEjg5UMFeQZyLdoVMtX8CzrU4ne/EhRjyTWgTab1c9qOSqRB2lE43rqXZKtLp9+VZ2q4rFu8GCDjgotqhf7I2UugIgAw6PBUtEgQtJCTY+czUa3MB5tygOy3XxuFEuhP8NoV4wQd8VkPkiQQ8+0Phy28+jjKnWZJFV8qoNbE+Mao4DS6whalTvIAeD58WUudKBb7QH7Z58M9f3Cvl6+0ILTFNnFjmHZPO8zoj8fh4z+A266h3d7iy54uCqxETW4HeICIi0pKUsQnshQwsu0eN4WOKzehEPSG5j/4klA==");
        message.setSignature("JEcNGua81/C5zE5yqnIG+qYDlu0b3MHpK3fqWLOLfpFT4IghYasgUxNTPstPsV5EEo5I/uqKoHBR07v8wSsdbzUtYHZ9EELPmDPPd+wnxAIMtTWNteAELb/3l9jqVeIGd6OMU3uY4GKmfwkDMHHX1wI3EUkSP4HHMRKv7YIoODnXImmnOdo6eFiPtcBYHnZ/+jHNeu4vBmZtQyulSscH682mmSVcqPLLMTEjtV0eIssF1WWzIH9290MDjK2wwrhqw17eP+wzz57bJlBgzEVQjIUGtIAeRbq2REFjcKqqxk+xwHCyBCo0n9QwkkE2gdLR8xN0CkaMHadb/Tfxhnbynw==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/keyexchange/symmetricKey")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(DATA_OWNER_HEADER, "TEST")
                        .content(gson.toJson(message)))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").exists());

    }
}

