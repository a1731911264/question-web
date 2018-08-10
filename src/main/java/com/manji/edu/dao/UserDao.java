package com.manji.edu.dao;

import com.manji.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {

    User findByUsernameAndPassword(String username, String password);
}
