package xyz.lizhaorong.security.token;

import xyz.lizhaorong.security.token.entity.SimpleUser;
import xyz.lizhaorong.security.token.entity.TokenObject;
import xyz.lizhaorong.util.support.ErrorCode;

public interface TokenManager {

    TokenObject generate(SimpleUser user);

    TokenObject refresh(String refreshToken);

    ErrorCode checkAuthorization(String accessToken,int role,String addr);

}
