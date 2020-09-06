package xyz.lizhaorong.web.util.authorization.token;

import org.springframework.stereotype.Component;

@Component
public class DefaultTokenManager implements TokenManager {
    @Override
    public String addToken(String uid) {
        return null;
    }

    @Override
    public boolean checkToken(String token) {
        return true;
    }
}
