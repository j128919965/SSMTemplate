package xyz.lizhaorong.security.webUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.lizhaorong.security.token.DefaultTokenManager;
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
        //检查是否需要身份验证
        int atVal = needAuthorization(handler);

        if (atVal >0) {
            Response res = checkAuthorization(request,atVal);
            if(!res.isSuccess()) {
                response.setStatus(401);
                response.getWriter().write(mapper.writeValueAsString(res));
                return false;
            }
        }
        return true;
    }

    /**
     * 检查是否目标方法需要身份验证
     * @param handler 目标方法
     * @return 身份验证等级，0代表不需要
     */
    private int needAuthorization(Object handler){
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //如果方法或类注明了@authorization
        Authorization ano = method.getAnnotation(Authorization.class);
        //value以方法上的注解为优先
        ano = ano != null ? ano : method.getDeclaringClass().getAnnotation(Authorization.class);
        return ano==null?0:ano.value();
    }

    /**
     * 对token进行检查
     * @return 响应
     */
    private Response checkAuthorization(HttpServletRequest request,int val){
        String message;
        //从header中得到token
        String authorization = request.getHeader("Authorization");

        //
        if(authorization==null) return Response.failure(TokenErrorCode.DID_NOT_LOGIN);

        //获取token解析结果
        SimpleUser user = tokenManager.analysisToken(authorization);
        if(user==null) return Response.failure(TokenErrorCode.WRONG_TOKEN);

        //令牌需要刷新
        if(user.getCount()==-1) return Response.failure(TokenErrorCode.NEED_REFRESH);

        //需要重新登录
        if(user.getCount()== DefaultTokenManager.MAX_COUNT) return Response.failure(TokenErrorCode.NEED_LOGIN);

        //检查地址是否一致
        if(!getAddress(request).equals(user.getAddr())) return Response.failure(TokenErrorCode.WRONG_ADDR);

        //检查接口权限
        if (user.getRole()<val)return Response.failure(TokenErrorCode.INSUFFICIENT_AUTHORITY);

        return Response.success();
    }

}
