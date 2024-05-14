package basilisk.web.servlet.service.keyexchange;
import org.mockito.MockedStatic;
import org.mcokito.Mockito;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import basilisk.web.servlet.keygen.KeyGenerator;
import basilisk.web.servlet.keygen.KeyCache;
import static org.mockito.Mockito.*;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(BasiliskKeyExchangeEndpoint.class)
//This is to indicate that the test class is specifically for testing the BasiliskWebServletApp class
//@AutoConfigureMockMvc(addFilters = false)
public class BasiliskKeyExchangeEndpointTest {

    @Autowired
    private MockMvc mockMvc;    //The MockMvc gives methods that can be used to test endpoints without a complete server startup

	@MockBean
	private KeyGenerator keyGenerator;
	@MockBean
	private KeyCache keyCache;

    @Test //For the POST endpoint handling key Exchange
    public void testExchangeKey() throws Exception {
        try (MockedStatic<KeyCache> mockedKeyCache = Mockito.mockStatic(KeyCache.class)) {
            PublicKey mockPublicKey = mock(PublicKey.class);
            mockedKeyCache.when(() -> KeyCache.getKeyForService("client")).thenReturn(mockPublicKey);    
            String requestBody = "{\"publicKey\":\"ClientPublicKey\"}";
    
        //Simulates a POST request to the api/exchange-keys
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/exchange-keys")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serverPublicKey").exists());
        }
    }
    //Test method for the GET endpoint, then it expects a succesful response status. Then it verifies that the returned content matches "The data for ID: 1"
    @Test
    public void getTestData() throws Exception {
        try (MockedStatic<KeyCache> mockedKeyCache = Mockito.mockStatic(KeyCache.class)) {
            PublicKey mockPublicKey = mock(PublicKey.class);
            mockedKeyCache.when(() -> KeyCache.getKeyForService("client")).thenReturn(mockPublicKey);
    
            mockMvc.perform(MockMvcRequestBuilders.get("/api/data/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("The data for ID: 1 with the key: " + mockPublicKey.toString()));
        }
    }
}