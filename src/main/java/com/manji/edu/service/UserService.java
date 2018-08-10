package com.manji.edu.service;

import com.manji.edu.model.User;

public interface UserService {

    User login(String username, String password);
}
