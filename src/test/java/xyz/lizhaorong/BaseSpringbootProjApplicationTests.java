package xyz.lizhaorong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.lizhaorong.security.token.TokenManager;
import xyz.lizhaorong.util.http.AsyncHttpClient;
import xyz.lizhaorong.util.http.BaseHttpClient;
import xyz.lizhaorong.util.http.ThreadAsyncHttpClient;
import xyz.lizhaorong.util.http.lzrapp.TokenBasedHttpClient;
import xyz.lizhaorong.util.support.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BaseSpringbootProjApplicationTests {

    @Test
    void testHttp() throws IOException {
        String url = "https://ssacgn.online/api/honor";
        Map<String,String> params = new HashMap<>();
        params.put("uid","1");
        BaseHttpClient httpClient = new BaseHttpClient();
        Map map = httpClient.get(url, params, Map.class);
        map.forEach((k,v)->{
            System.out.println(k+" : "+v);
        });
    }

    @Test
    void s() throws IOException, InterruptedException {
        String url = "https://ssacgn.online/api/notice";
        Map<String,String> params = new HashMap<>();
        params.put("uid","1");
        AsyncHttpClient client = new ThreadAsyncHttpClient(new BaseHttpClient());
        client.success(t->{
            List data = (List)((Response) t).getData();
            for (Object datum : data) {
                System.out.println(datum);
            }
        })
        .failed(System.out::println)
        .setHeader("name","lzr")
        .getAsync(url,null, Response.class);
        Thread.sleep(3000);
    }

}
