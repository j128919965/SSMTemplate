package xyz.lizhaorong.web.util.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.lizhaorong.web.util.authorization.token.TokenManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenManager manager;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //如果注明了@authorization，需要进行验证,进行验证返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            //从header中得到token
            String authorization = request.getHeader("Authorization");
            System.out.println("\n\n进行身份验证");
            System.out.println("token: "+authorization+"\n\n");
            //验证token
            return manager.checkToken(authorization);
        }
        return true;
    }
}
