package lzr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import xyz.lizhaorong.security.webUtil.Authorization;
import xyz.lizhaorong.security.token.DefaultTokenManager;
import xyz.lizhaorong.security.token.TokenManager;
import xyz.lizhaorong.web.controller.HomeController;
import xyz.lizhaorong.web.util.Response;

import java.lang.annotation.Annotation;

public class TokenTest {

    @Test
    public void t1() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(Response.failure("没有登录"));
        System.out.println(string);
    }

    @Test
    public void t2(){
        TokenManager manager = new DefaultTokenManager();
        System.out.println(manager.generate("201721221294",2));
    }

    @Test
    public void t3() throws NoSuchMethodException {
        //Authorization annotation = HomeController.class.getMethod("getsb").getClass().getAnnotation(Authorization.class);
        Annotation[] annotations = HomeController.class.getMethod("getsb").getDeclaringClass().getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }

    @Test
    public void t5() throws NoSuchMethodException {
        //Authorization annotation = HomeController.class.getMethod("getsb").getClass().getAnnotation(Authorization.class);
        Class<?> getsb = HomeController.class.getMethod("getsb").getDeclaringClass();
        System.out.println(getsb);

    }

    @Test
    public void t4(){
        Authorization annotation = HomeController.class.getAnnotation(Authorization.class);
        System.out.println(annotation);
    }

}
