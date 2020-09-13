package lzr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import xyz.lizhaorong.security.authorization.token.DefaultTokenManager;
import xyz.lizhaorong.web.util.Response;

public class TokenTest {

    @Test
    public void t1() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(Response.failure("没有登录"));
        System.out.println(string);
    }

}
