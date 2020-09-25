package xyz.lizhaorong.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public SimpleUser analysisToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
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
}
