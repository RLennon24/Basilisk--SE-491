package basilisk.web.servlet.service.keyexchange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BasiliskKeyExchangeEndpoint.class)
public class BasiliskKeyExchangeEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeyGenerator keyGenerator;
    @MockBean
    private KeyCache keyCache;

    @Test
    public void testExchangeKey() throws Exception {
        PublicKey mockPublicKey = mock(PublicKey.class);
        when(keyGenerator.getPublicKey()).thenReturn(mockPublicKey);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/exchange-keys")
                .content("{\"publicKey\":\"ClientPublicKey\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serverPublicKey").exists());
    }

    @Test
    public void getTestData() throws Exception {
        when(keyCache.getData("1")).thenReturn("Data for ID: 1");
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/data/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Data for ID: 1"));
    }
}
