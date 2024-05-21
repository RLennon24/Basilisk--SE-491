package basilisk.web.servlet.service.viewdata;

import basilisk.web.servlet.keygen.KeyCache;
import basilisk.web.servlet.keygen.ServerKeyGenerator;
import basilisk.web.servlet.message.BaseMessage;
import basilisk.web.servlet.message.BaseMessageBuilder;
import basilisk.web.servlet.util.GenKeysForTestUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewDataService.class)
public class ViewDataServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setUp() {
        ServerKeyGenerator.getPublicKey();
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048, SecureRandom.getInstanceStrong());

            //generate keys
            KeyCache.addServicePublicKey("127.0.0.1:80", keyGen.generateKeyPair().getPublic());
        } catch (Exception e) {
            System.out.println("Could not generate User Keys");
        }

        GenKeysForTestUtil.generateSymmetricKeys("127.0.0.1:80");
    }

    //@Test
    public void testGetDataForId() throws Exception {
        BaseMessage message = BaseMessageBuilder.packMessage("test", "127.0.0.1:80");
        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/id")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    //@Test
    public void testGetDataByRole() throws Exception {
        BaseMessage message = BaseMessageBuilder.packMessage("test", "127.0.0.1:80");
        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/role")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    //@Test
    public void testGetDataByTag() throws Exception {
        BaseMessage message = BaseMessageBuilder.packMessage("test", "127.0.0.1:80");
        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/tag")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }
}