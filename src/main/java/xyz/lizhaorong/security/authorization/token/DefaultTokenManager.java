package xyz.lizhaorong.security.authorization.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("manager")
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
    public String addToken(String uid) {

        try{
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim("uid",uid)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean checkToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getUserID(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("uid").asString();
        } catch (JWTDecodeException e) {
            e.getStackTrace();
        }
        return null;
    }
}
