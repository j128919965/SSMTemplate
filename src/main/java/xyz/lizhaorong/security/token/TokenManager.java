package xyz.lizhaorong.security.token;


import xyz.lizhaorong.web.util.Response;

import java.util.List;

public interface TokenManager {

    /**
     * 生成一个token
     * @return accessToken字符串
     */
    String generateAccessToken(SimpleUser user);


    /**
     * 生成一个用于刷新的token
     * @return refreshToken字符串
     */
    String generateRefreshToken(SimpleUser user);

    /**
     * 刷新token
     * @return 刷新后的token组
     */
    List<String> refreshToken(String tk);

    /**
     * 检查token是否符合要求
     * @return 结果
     */
    Response checkAuthorization(String authorization, int val, String addr);

}
