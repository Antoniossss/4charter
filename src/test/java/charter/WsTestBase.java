package charter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public abstract class WsTestBase {

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper=new ObjectMapper();

}
