package basilisk.user.servlet.service.viewdata;

import basilisk.user.servlet.message.BaseMessage;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewDataService.class)
public class ViewDataServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetDataForId() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxNS4nB5Ochyob2u2d02tQGe5tNaN/YpLuyb2aZwWppAl4tPz3i6iqlGlRbHrf241kRJYPLuf4Bi+DcY+f5zuQRyY7aK0yC2FywDpw98EP3OIFlbsVwe7SkPxAZuj3owhl/JjXvFwk/CRDbsVGJZACf2Ekq2aprc/K5WjQEYWJUlzhWGe8dIvJtbNoBR+ozDJ5euXtnMgVKddR0PM60tw9mDl/mPbjNF/gz8I/hX0rlK6+HXLHWXkdl+uJjWxJo5hApkcAAdaCEAO0u+jan+a2NgCPwNaUM02hDgKc+a5dXLGg80MpGMHgvGpdqODBv/856CPbuYUNjNBZx6MLnnp6QIDAQAB");
        message.setSignature("CVA5wVSFlK55P2dFbZ2h0kwtEDegDe7UgB5o0CeSV5rS2ggtqep6T8CaES7ymC8Zn8VcmeUYXR+w3EdD6LYNulGEFWy0gEVNMxFOmbvEkh/WzYAnv1Xn52B070RV2Saz4r4VKmGudzZmQ2tDVmqPMyoxGD5O7FPsHE/Wt+pe6w0Ugs6E7BL8R8YXzHP+6s5jg32n0rf8tny6Aojs9plDv0VeiunM94193dJW5UD4bTJrREYWf5AnTQja3Gi5OvTydQtz+1Wa5wT/twUftA46nFIQTl4MBsgoQvwDuTfgXHPk25+Unee9RatLOIKS2aGccbGJBkO0tlSSvEigSTF9kQ==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/id")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testGetDataByRole() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxNS4nB5Ochyob2u2d02tQGe5tNaN/YpLuyb2aZwWppAl4tPz3i6iqlGlRbHrf241kRJYPLuf4Bi+DcY+f5zuQRyY7aK0yC2FywDpw98EP3OIFlbsVwe7SkPxAZuj3owhl/JjXvFwk/CRDbsVGJZACf2Ekq2aprc/K5WjQEYWJUlzhWGe8dIvJtbNoBR+ozDJ5euXtnMgVKddR0PM60tw9mDl/mPbjNF/gz8I/hX0rlK6+HXLHWXkdl+uJjWxJo5hApkcAAdaCEAO0u+jan+a2NgCPwNaUM02hDgKc+a5dXLGg80MpGMHgvGpdqODBv/856CPbuYUNjNBZx6MLnnp6QIDAQAB");
        message.setSignature("CVA5wVSFlK55P2dFbZ2h0kwtEDegDe7UgB5o0CeSV5rS2ggtqep6T8CaES7ymC8Zn8VcmeUYXR+w3EdD6LYNulGEFWy0gEVNMxFOmbvEkh/WzYAnv1Xn52B070RV2Saz4r4VKmGudzZmQ2tDVmqPMyoxGD5O7FPsHE/Wt+pe6w0Ugs6E7BL8R8YXzHP+6s5jg32n0rf8tny6Aojs9plDv0VeiunM94193dJW5UD4bTJrREYWf5AnTQja3Gi5OvTydQtz+1Wa5wT/twUftA46nFIQTl4MBsgoQvwDuTfgXHPk25+Unee9RatLOIKS2aGccbGJBkO0tlSSvEigSTF9kQ==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/role")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testGetDataByTag() throws Exception {
        BaseMessage message = new BaseMessage();
        message.setMessage("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxNS4nB5Ochyob2u2d02tQGe5tNaN/YpLuyb2aZwWppAl4tPz3i6iqlGlRbHrf241kRJYPLuf4Bi+DcY+f5zuQRyY7aK0yC2FywDpw98EP3OIFlbsVwe7SkPxAZuj3owhl/JjXvFwk/CRDbsVGJZACf2Ekq2aprc/K5WjQEYWJUlzhWGe8dIvJtbNoBR+ozDJ5euXtnMgVKddR0PM60tw9mDl/mPbjNF/gz8I/hX0rlK6+HXLHWXkdl+uJjWxJo5hApkcAAdaCEAO0u+jan+a2NgCPwNaUM02hDgKc+a5dXLGg80MpGMHgvGpdqODBv/856CPbuYUNjNBZx6MLnnp6QIDAQAB");
        message.setSignature("CVA5wVSFlK55P2dFbZ2h0kwtEDegDe7UgB5o0CeSV5rS2ggtqep6T8CaES7ymC8Zn8VcmeUYXR+w3EdD6LYNulGEFWy0gEVNMxFOmbvEkh/WzYAnv1Xn52B070RV2Saz4r4VKmGudzZmQ2tDVmqPMyoxGD5O7FPsHE/Wt+pe6w0Ugs6E7BL8R8YXzHP+6s5jg32n0rf8tny6Aojs9plDv0VeiunM94193dJW5UD4bTJrREYWf5AnTQja3Gi5OvTydQtz+1Wa5wT/twUftA46nFIQTl4MBsgoQvwDuTfgXHPk25+Unee9RatLOIKS2aGccbGJBkO0tlSSvEigSTF9kQ==");
        message.setTimestamp(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis())));

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/viewData/tag")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(gson.toJson(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }
}