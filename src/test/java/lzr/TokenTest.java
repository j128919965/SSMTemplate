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
    public void t2(){
        TokenManager manager = new DefaultTokenManager();
        System.out.println(manager.generate("201721221294",4));
    }

    @Test
    public void t3() throws NoSuchMethodException {
        //Authorization annotation = HomeController.class.getMethod("getsb").getClass().getAnnotation(Authorization.class);
        Annotation[] annotations = HomeController.class.getMethod("getsb").getDeclaringClass().getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }

}
