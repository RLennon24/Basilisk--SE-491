package basilisk.user.servlet.service.viewdata;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.keyexchange.packaging.KeyPackager;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.keygen.KeyCache;
import basilisk.user.servlet.message.BaseMessage;
import basilisk.user.servlet.message.BaseMessageBuilder;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewDataService.class)
@ActiveProfiles(profiles = "localtest")
@PropertySource("classpath:/application.properties")
public class ViewDataServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    public void testGetDataForId() throws Exception {
        BaseMessage message = BaseMessageBuilder.encodeMessage("testOne");
        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/id")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDataForIdAttack() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("dyP4cmmc0ZH3ADdjnhHlcA==");
        message.setSignature("dyP4cmmc0ZH3ADdjnhHlcA==\n" +
                "ACU6JKsGfZ0Yn7oHVYKIW3WPMrd2VTpF+FzV1xxJD6qK8b/cOhrAXbXCMtnqarG8T4XOhQXKRXzf6dFvjOMxqFfx838f14LyT+FWASKUEhCERAMFy7ZZLPV2Xg4BJtK3/Et373tKO/6O1U51UAuDMHwTX0Hnnei3gNmheIvi37i36pAmEUaa9iT85QWWESvICJ1E0btF2eV4Bcsv/0WV6QjL+m8P/qQwj8gPAYj9QQi7rnABYRrYAIaxO0C0T4P4+bUn5JsZJXP5RoRQm39dCUxTqMhjAiwD7Ga976gJGeC8m4gyfg4O3IXUfQTei7psjNvXGlDRuSHUR7ZcYio9kw==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));
        message.setMac("�u���1�?��\u007F��w�L��\u001A\u001Ed�\"�W�\\�wet�");

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/id")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testGetDataByRole() throws Exception {
        BaseMessage message = BaseMessageBuilder.encodeMessage("government");
        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/role")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDataByRoleAttack() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("dyP4cmmc0ZH3ADdjnhHlcA==");
        message.setSignature("dyP4cmmc0ZH3ADdjnhHlcA==\n" +
                "ACU6JKsGfZ0Yn7oHVYKIW3WPMrd2VTpF+FzV1xxJD6qK8b/cOhrAXbXCMtnqarG8T4XOhQXKRXzf6dFvjOMxqFfx838f14LyT+FWASKUEhCERAMFy7ZZLPV2Xg4BJtK3/Et373tKO/6O1U51UAuDMHwTX0Hnnei3gNmheIvi37i36pAmEUaa9iT85QWWESvICJ1E0btF2eV4Bcsv/0WV6QjL+m8P/qQwj8gPAYj9QQi7rnABYRrYAIaxO0C0T4P4+bUn5JsZJXP5RoRQm39dCUxTqMhjAiwD7Ga976gJGeC8m4gyfg4O3IXUfQTei7psjNvXGlDRuSHUR7ZcYio9kw==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));
        message.setMac("�u���1�?��\u007F��w�L��\u001A\u001Ed�\"�W�\\�wet�");

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/role")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testGetDataByTag() throws Exception {
        BaseMessage message = BaseMessageBuilder.encodeMessage("personal");
        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/tag")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDataByTagAttack() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("Jqm74VsKn2byj9EYo+ywxg==");
        message.setSignature("Jqm74VsKn2byj9EYo+ywxg==\n" +
                "hLC3bJ7hbb6i6LZ1NpTwfA31yj1qoLUeJpWwsmbSMLDek/ee0LE4RSPeG6nHpVuAg1fAjuy/uH2npeJ96eW8DnAWKVNN6HV+tUEPWZmum4kKkMKKmqiusappaYCtGT3jaIGLQpeTH8vzKfBmMyILcosZJ/qAV+oFXV9pygM10hYTi/OOmZQJ+ChuSKBUike7gIdb27/lW+9gFrxPLcyUkGsEmMjb+jSNYb+LsjnNkFu32PME2ppScqN/cSJ1uOjtcZJWf3p7F2AEBzCp/1itTm6QoYKmqfIL9BWNAhRItciJME21GOwfwV59bZ6i9UsUE3jy/jFuw7M5REPEtssd6w==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));
        message.setMac("J\u0016�͙l~���:x=���q\n" +
                "�*}[D[m���M\n" +
                "\u0000�");

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/tag")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().is5xxServerError());
    }
}