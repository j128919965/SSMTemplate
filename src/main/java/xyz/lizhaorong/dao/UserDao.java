package xyz.lizhaorong.dao;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.lizhaorong.entity.User;

@CacheNamespace
public interface UserDao {

    @Select("select * from users where id=#{id}")
    User getUserById(@Param("id") int id);

}
