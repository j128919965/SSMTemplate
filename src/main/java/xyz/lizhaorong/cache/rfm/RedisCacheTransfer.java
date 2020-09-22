package xyz.lizhaorong.cache.rfm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheTransfer {
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisCache4Mybatis.setRedisTemplate(redisTemplate);
    }



}
