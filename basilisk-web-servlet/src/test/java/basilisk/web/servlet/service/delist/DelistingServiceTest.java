package basilisk.web.servlet.service.delist;

import basilisk.web.servlet.message.BaseMessage;
import com.google.gson.Gson;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static basilisk.web.servlet.service.keyexchange.KeyExchangeService.DATA_OWNER_HEADER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DelistingService.class)
public class DelistingServiceTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDelistKeys() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("YqiFFzxBhPvq2MULTJ8bBA==");
        message.setSignature("JoskehXpH4diE+2361hbA7jsP0VwETtcD8zbgoJqhZf6kOtPhA0FgSdIzrBad/xXpAY6j21P7Mgfdt+B0O2riytli2fT+ffBEAF9pvjRJmh/Tsm0qcQtRYmrnWA1zrmpkQNRCs4qy27qTKbALQFqF39lbv9HdsoaIJJbGWsbUoHcTE9SsODCRnG7uONStdrSyLEsFU3eVO5Cejh3D+46vL+iWFCmR5Keh5zOR/lCuVAGZXVGlmJOlG30DpDP3C6PKj1Ep1xMzMqSaXpcB8BNEtB6cI9fOBag5soXXoq92+w/IN1V42hADDePELg/M1YbNO8ppOufczTUpL/BULnJ5w==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));
        message.setMac("̀�\u000F\u0019��\u000E�s:��̏\u001C�/��\u001C�\\�\u07FE����W\u000E");

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/delist")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(DATA_OWNER_HEADER, "TEST")
                        .content(gson.toJson(message)))
                .andExpect(status().is5xxServerError());
    }
}