package xyz.lizhaorong.security.webUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.lizhaorong.security.token.SimpleUser;
import xyz.lizhaorong.security.token.TokenManager;
import xyz.lizhaorong.web.util.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenManager tokenManager;

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
        Authorization ano = method.getDeclaringClass().getAnnotation(Authorization.class);
        ano = ano != null?ano:method.getAnnotation(Authorization.class);
        if (ano !=null) {
            String message;
            //从header中得到token
            String authorization = request.getHeader("Authorization");
            System.out.println("\n\n进行身份验证");
            System.out.println("token: "+authorization+"\n\n");
            //验证token
            SimpleUser user = tokenManager.checkToken(authorization);
            //验证身份等级是否拥有权限
            if(user!=null) {
                if (user.getRole()>ano.value())
                    return true;
                message = "您的账号权限不足";
            }else{
                message = "登录信息无效";
            }
            response.getWriter().write(mapper.writeValueAsString(Response.failure(message)));
            return false;
        }
        return true;
    }
}
