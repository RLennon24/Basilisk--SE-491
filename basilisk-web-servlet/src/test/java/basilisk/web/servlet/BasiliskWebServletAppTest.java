package basilisk.web.servlet;
//Below is my web servlet testing code. -Zachary Wile

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
//These are all the essential Spring and JUnit libraries needed for testing. 

public class BasiliskWebServletAppTest {

    @Autowired
    private MockMvc mockMvc;    //The MockMvc gives methods that can be used to test endpoints without a complete server startup

}
