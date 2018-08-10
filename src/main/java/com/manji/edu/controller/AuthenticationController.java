package com.manji.edu.controller;

import com.alibaba.fastjson.JSONObject;
import com.manji.edu.model.User;
import com.manji.edu.service.UserService;
import com.manji.edu.utils.JwtUtils;
import com.manji.edu.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        if (StringUtils.isBlank(username)) return ResponseUtil.error("请输入用户名！");
        String password = jsonObject.getString("password");
        if (StringUtils.isBlank(password)) return ResponseUtil.error("请输入密码！");
        User login = userService.login(username, password);
        if (null ==login)  return ResponseUtil.error("用户名或密码错误！");
        Map<String, String> map = new HashMap<>();
        map.put("token", JwtUtils.createToken(login.getId()));
        map.put("nickname", JwtUtils.createToken(login.getNickname()));
        return ResponseUtil.success("登陆成功！", map);
    }
}
