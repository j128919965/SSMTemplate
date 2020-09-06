package xyz.lizhaorong.web.util.authorization.token;

public interface TokenManager {

    String addToken(String uid);


    boolean checkToken(String token);


}
