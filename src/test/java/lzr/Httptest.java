package lzr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import xyz.lizhaorong.entity.User;
import xyz.lizhaorong.web.util.Response;
import xyz.lizhaorong.web.util.http.HttpUtil;

import java.io.IOException;
import java.util.Map;

public class Httptest {

    @Test
    public void t1() throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        String backData;
        backData = "{\"id\":123,\"name\":\"lzr\",\"gender\":true}";

        System.out.println(jsonMapper.readValue(backData, User.class));

    }

    @Test
    public void t2() throws Exception {
        HttpUtil<User, Response> httpUtil = new HttpUtil<>();
        User u = new User();
        u.setName("lzr");
        u.setGender(false);
        u.setId(9);
        httpUtil.setHeader("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIyMDE3MjEyMjEyOTQiLCJyb2xlIjo0LCJleHAiOjE2MDA3NDE0MjR9.Kt5nWrfrCHhygeRsuYbE9Gc9VF5jGYkK8YNl4KRocoY");
        Response response = httpUtil.post("http://localhost:8080/home/psb2", u, Response.class);
        System.out.println(response);

    }

}
