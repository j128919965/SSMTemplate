package xyz.lizhaorong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.lizhaorong.dao.UserDao;
import xyz.lizhaorong.entity.User;

@Service
public class SbService {

    @Autowired
    UserDao userDao;

    public User geUserById(int id){
        return userDao.getUserById(id);
    }


}
