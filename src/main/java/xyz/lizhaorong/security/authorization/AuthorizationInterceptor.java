package xyz.lizhaorong.security.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.lizhaorong.security.authorization.token.TokenManager;
import xyz.lizhaorong.web.util.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenManager manager;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //如果方法或类注明了@authorization
        if (method.getDeclaringClass().getAnnotation(Authorization.class)!=null|| method.getAnnotation(Authorization.class) != null) {
            //从header中得到token
            String authorization = request.getHeader("Authorization");
            System.out.println("\n\n进行身份验证");
            System.out.println("token: "+authorization+"\n\n");
            //验证token
            boolean flag = manager.checkToken(authorization);
            if(flag) return true;

            response.getWriter().write(mapper.writeValueAsString(Response.failure("登录状态失效")));
            return false;

        }
        return true;
    }
}
