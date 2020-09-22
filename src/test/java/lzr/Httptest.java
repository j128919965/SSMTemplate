package lzr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import xyz.lizhaorong.entity.User;
import xyz.lizhaorong.web.util.Response;
import xyz.lizhaorong.web.util.http.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Httptest {

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIyMDE3MjEyMjEyOTQiLCJyb2xlIjo0LCJleHAiOjE2MDA3NDczNjZ9.pYBvV1_tMrjvGUvrNeRnQeLB0b1iEubKZDJz_HyDwUE";

    @Test
    public void t1() throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        String backData;
        backData = "{\"id\":123,\"name\":\"lzr\",\"gender\":true}";

        System.out.println(jsonMapper.readValue(backData, User.class));

    }

    @Test
    public void t2() throws Exception {
        HttpUtil httpUtil = new HttpUtil();
        User u = new User();
        u.setName("lzr");
        u.setGender(false);
        u.setId(9);
        Response response = httpUtil
                .setHeader("Authorization",token)
                .post("http://localhost:8080/home/psb2", u, Response.class);

        System.out.println(response);

    }

    @Test
    public void t3() throws IOException {
        String url = "http://localhost:8080/home/sb";
        HttpUtil httpUtil = new HttpUtil();
        HashMap<String, String> map = new HashMap<>();
        map.put("id","1");
        Response user = httpUtil.setHeader("Authorization",token)
                .get(url, map, Response.class);
        System.out.println(user);
    }

}
