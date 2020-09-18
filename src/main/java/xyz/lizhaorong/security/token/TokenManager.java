package xyz.lizhaorong.security.token;


public interface TokenManager {

    String generate(String uid,int role);

    SimpleUser checkToken(String token);


}
