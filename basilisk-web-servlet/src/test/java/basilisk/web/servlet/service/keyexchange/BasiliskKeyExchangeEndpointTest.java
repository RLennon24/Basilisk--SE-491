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
public class BasiliskKeyExchangeEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testExchangeKey() throws Exception {
        try (MockedStatic<KeyGenerator> mockedKeyGenerator = Mockito.mockStatic(KeyGenerator.class)) {
            PublicKey mockPublicKey = mock(PublicKey.class);
            mockedKeyGenerator.when(KeyGenerator::getPublicKey).thenReturn(mockPublicKey);

            mockMvc.perform(MockMvcRequestBuilders.post("/exchange-keys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"publicKey\":\"examplePublicKey\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serverPublicKey").exists());
        }
    }
}

