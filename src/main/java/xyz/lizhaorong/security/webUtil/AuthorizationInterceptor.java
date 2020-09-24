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

    /**
     * 获取源地址，如果源地址和记录的地址不匹配，则要求重新登录
     */
    private static String getAddress(HttpServletRequest request){
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

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
            if(authorization!=null){
                //验证token
                SimpleUser user = tokenManager.checkToken(authorization);
                System.out.println(user);
                //验证身份等级是否拥有权限
                if(user!=null) {
                    if (user.getRole()>ano.value()){
                        String addr = getAddress(request);
                        if(addr.equals(user.getAddr())){
                            return true;
                        }else{
                            message = "c04和上次登录地址不同";
                        }
                    }else{
                        message = "c03您的账号权限不足";
                    }
                }else{
                    message = "c02登录信息无效";
                }
            } else {
                message = "c01没有检测到登录信息";
            }

            response.setStatus(401);
            response.getWriter().write(mapper.writeValueAsString(Response.failure(message)));
            return false;
        }
        return true;
    }
}
