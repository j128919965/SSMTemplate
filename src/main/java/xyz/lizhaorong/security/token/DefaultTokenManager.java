package xyz.lizhaorong.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import xyz.lizhaorong.security.webUtil.TokenErrorCode;
import xyz.lizhaorong.web.util.Response;

import java.util.*;

@Component("tokenManager")
public class DefaultTokenManager implements TokenManager {

    public static final int MAX_COUNT = 5;

    /**
     * accessToken 过期时间 60min
     */
    private static final long EXPIRE_TIME=60 * 60 *1000;

    /**
     * refreshToken 过期时间 90min
     */
    private static final long REFRESH_EXPIRE_TIME=90 * 60 *1000;



    /**
     * 私钥，使用它生成token，最好进行下加密
     */
    private static final String TOKEN_SECRET="4kd2js1kl4mmc5kd4sa4x54e8w/d18as-+asc2DDX2D8gf";

    private static final String REFRESH_TOKEN_SECRET="SOFG,ZX9-S8Q2+X5R4+672FS9MPV;Z.PQ*/91`SA56CZ5@$^%&(@sgjxasfw";

    /**
     * 对token进行检查
     * @return 响应
     */
    public Response checkAuthorization(String authorization, int val, String addr){
        return checkAuthorizationImpl(authorization,val,addr,true);
    }

    private Response checkRefreshAuthorization(String authorization){
        return checkAuthorizationImpl(authorization,0,null,false);
    }

    private Response checkAuthorizationImpl(String authorization, int val, String addr,boolean flag){

        //token是否为空
        if(authorization==null) return Response.failure(TokenErrorCode.DID_NOT_LOGIN);

        //获取token解析结果
        SimpleUser user;
        user = analysisToken(authorization,flag);

        if(user==null) return Response.failure(TokenErrorCode.WRONG_TOKEN);

        //令牌需要刷新
        if(user.getCount()==-1) return Response.failure(TokenErrorCode.NEED_REFRESH);

        //需要重新登录
        if(user.getCount()== DefaultTokenManager.MAX_COUNT) return Response.failure(TokenErrorCode.NEED_LOGIN);

        if(flag){
            //检查地址是否一致
            if(!addr.equals(user.getAddr())) return Response.failure(TokenErrorCode.WRONG_ADDR);

            //检查接口权限
            if (user.getRole()<val)return Response.failure(TokenErrorCode.INSUFFICIENT_AUTHORITY);
        }
        if(flag){
            return Response.success();
        }else {
            return Response.success(user);
        }

    }

    @Override
    public String generateAccessToken(SimpleUser user) {

        try{
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim("uid",user.getUserId())
                    .withClaim("role",user.getRole())
                    .withClaim("addr",user.getAddr())
                    .withClaim("count",user.getCount())
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String generateRefreshToken(SimpleUser user) {
        try{
            Date date = new Date(System.currentTimeMillis()+REFRESH_EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(REFRESH_TOKEN_SECRET);
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim("uid",user.getUserId())
                    .withClaim("role",user.getRole())
                    .withClaim("addr",user.getAddr())
                    .withClaim("count",user.getCount())
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private SimpleUser analysisToken(String token,boolean flag){
        try{

            Algorithm algorithm ;
            if(flag){
                algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            }else{
                algorithm = Algorithm.HMAC256(REFRESH_TOKEN_SECRET);
            }
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return new SimpleUser(
                    decodedJWT.getClaim("uid").asString()
                    ,decodedJWT.getClaim("role").asInt()
                    , decodedJWT.getClaim("addr").asString()
                    ,decodedJWT.getClaim("count").asInt()
            );
        }catch (TokenExpiredException e){
            return new SimpleUser(null,0,null,-1);
        }catch (JWTDecodeException e){
            System.out.println("token解析失败");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<String> refreshToken(String tk) {
        //TODO
        Response response = checkRefreshAuthorization(tk);
        //如果解析失败，则返回null
        if(!response.isSuccess())return null;
        SimpleUser user = (SimpleUser) response.getData();
        user.setCount(user.getCount()+1);
        if(user.getCount()>5)return null;

        List<String> list = new ArrayList<>();
        list.add(generateAccessToken(user));
        list.add(generateRefreshToken(user));

        return list;
    }
}
