package xyz.lizhaorong.security.authorization.token;

import org.springframework.stereotype.Component;


public interface TokenManager {

    String addToken(String uid);

    boolean checkToken(String token);

    String getUserID(String token);

}
