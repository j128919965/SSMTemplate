package xyz.lizhaorong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.lizhaorong.security.token.TokenManager;

@SpringBootTest
class BaseSpringbootProjApplicationTests {

    @Autowired
    TokenManager tokenManager;

    @Test
    void contextLoads() {
        System.out.println("------------------------");
        System.out.println(tokenManager);
        System.out.println("------------------------");
    }

}
