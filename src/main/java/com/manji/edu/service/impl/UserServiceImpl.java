package com.manji.edu.service.impl;

import com.manji.edu.dao.UserDao;
import com.manji.edu.model.User;
import com.manji.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public User login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }
}
