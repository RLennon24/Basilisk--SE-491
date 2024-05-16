package basilisk;
import basilisk.user.servlet.service.KeyExchangeService;


import junit.framework.TestCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;



@WebMvcTest(KeyExchangeService.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = KeyExchangeService.class)
public class KeyExchangeServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestKeyExchangeService() throws Exception {
        String url = "/key-exchange/public-key";

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/key-exchange/public-key")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("public-key"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("public-key"));
    }
    
}
