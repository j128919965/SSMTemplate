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

    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME=60 * 60 *1000;

    /**
     * 私钥，使用它生成token，最好进行下加密
     */
    private static final String TOKEN_SECRET="4kd2js1kl4mmc5kd4sa4x54e8w/d18as-+asc2DDX2D8gf";


    @Override
    public String generate(String uid,int role,String addr) {

        try{
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim("uid",uid)
                    .withClaim("role",role)
                    .withClaim("addr",addr)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SimpleUser checkToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return new SimpleUser(decodedJWT.getClaim("uid").asString(),decodedJWT.getClaim("role").asInt(),decodedJWT.getClaim("addr").asString());
        }catch (TokenExpiredException e){
            System.out.println("token过期");
        }catch (JWTDecodeException e){
            System.out.println("token解析失败");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
