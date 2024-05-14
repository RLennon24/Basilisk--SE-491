//The Test code for the Web Servlet. -Zach
package basilisk.web.servlet.service.keyexchange;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import basilisk.web.servlet.keygen.KeyGenerator;
import java.security.PublicKey;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BasiliskKeyExchangeEndpoint.class)
//This is to indicate that the test class is specifically for testing the BasiliskWebServletApp class
public class BasiliskKeyExchangeEndpointTest {

    @Autowired
    private MockMvc mockMvc;  
    //The MockMvc gives methods that can be used to test endpoints without a complete server startup

    @Test 
    //For the POST endpoint handling key Exchange
    public void testExchangeKey() throws Exception {
        try (MockedStatic<KeyGenerator> mockedKeyGenerator = Mockito.mockStatic(KeyGenerator.class)) {
            PublicKey mockPublicKey = mock(PublicKey.class);
            mockedKeyGenerator.when(KeyGenerator::getPublicKey).thenReturn(mockPublicKey);
             //Simulates a POST request to the api/exchange-keys

            mockMvc.perform(MockMvcRequestBuilders.post("/exchange-keys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"publicKey\":\"examplePublicKey\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serverPublicKey").exists());
        }
    }
}

