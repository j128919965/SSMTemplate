package xyz.lizhaorong.security.token;


public interface TokenManager {

    String generate(String uid,int role,String addr);

    SimpleUser checkToken(String token);


}
