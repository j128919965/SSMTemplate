package xyz.lizhaorong.security.token;


public interface TokenManager {

    /**
     * 生成一个token
     * @return token字符串
     */
    String generateAccessToken(SimpleUser user);



    /**
     * 解析token
     * @param token 客户端发来的token字符串
     * @return 解析结果
     */
    SimpleUser analysisToken(String token);

}
