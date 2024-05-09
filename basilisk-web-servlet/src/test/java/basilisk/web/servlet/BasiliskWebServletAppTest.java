package basilisk.web.servlet;
//Below is my web servlet testing code. -Zachary Wile
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//These are all the essential Spring and JUnit libraries needed for testing. 

@WebMvcTest(BasiliskKeyExchangeEndpoint.class) //This is to indicate that the test class is specifically for testing the BasiliskWebServletApp class
public class BasiliskWebServletAppTest {
	
	@Autowired
	private MockMvc mockMvc;	//The MockMvc gives methods that can be used to test endpoints without a complete server startup

	@Test //For the POST endpoint handling key Exchange
	public void testExchangeKey() throws Exception{
		String askForBody = "{\"publicKey\":\"ClientPublicKey\"}";
	
	//Simulates a POST request to the api/exchange-keys
	mockMvc.perform(MockMvcRequestBuilders
		.post("/api/exchange-keys")
		.content(askForBody)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.serverPublicKey").exists());
	}

		//Test method for the GET endpoint, then it expects a succesful response status. Then it verifies that the returned content matches "The data for ID: 1"
		@Test
	public void getTestData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
		.get("/api/data/1")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOK())
		.andExpect(content().string("The data for ID: 1"));

	}
	    
}
