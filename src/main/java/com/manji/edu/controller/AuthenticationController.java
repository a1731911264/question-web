package com.manji.edu.controller;

import com.alibaba.fastjson.JSONObject;
import com.manji.edu.utils.OKHttpClientUtil;
import com.manji.edu.utils.ResponseUtil;
import com.manji.edu.service.UserService;
import com.manji.edu.utils.JwtUtils;
import com.manji.edu.utils.ThreadLocalUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Map<String, String> map = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private static final String SSO_URL = "https://sso.cnsuning.com/ids/login?jsonViewType=true";

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        if (StringUtils.isBlank(username)) return ResponseUtil.error("请输入用户名！");
        String password = jsonObject.getString("password");
        if (StringUtils.isBlank(password)) return ResponseUtil.error("请输入密码！");

     /*   User login = userService.login(username, password);
        if (null ==login)  return ResponseUtil.error("工号或密码错误！");*/
        try {
            String json = OKHttpClientUtil.httpPostOfForm(SSO_URL, jsonObject);
            JSONObject auth = JSONObject.parseObject(json);
            if (auth.containsKey("success")) {
                Boolean success = auth.getBoolean("success");
                if (success) {
                    String userNumber = map.get(username);
                    Map<String, Object> result = new HashMap<String, Object>();
                    if (StringUtils.isBlank(userNumber)) {
                        // 第一次登录
                        result.put("flag", true);
                    } else {
                        result.put("flag", false);
                        result.put("nickname", userNumber);
                    }
                    String token = JwtUtils.createToken(username);
                    result.put("token", token);
                    return ResponseUtil.success("登录成功！", result);
                }
            }
        } catch (Exception e) {
            logger.info("登录鉴权=>向SSO鉴权异常，异常原因exception={}", e);
            return ResponseUtil.error("SSO登录鉴权服务异常，请稍候再试！");
        }

        return ResponseUtil.error("工号或密码错误");
        /*Map<String, String> map = new HashMap<>();
        map.put("token", JwtUtils.createToken(login.getId()));
        map.put("nickname", login.getNickname());
        return ResponseUtil.success("登陆成功！", map);*/
    }

    @PostMapping("/username")
    public Map<String, Object> addUsername(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        if (StringUtils.isBlank(username)) return ResponseUtil.error("请输入姓名！");
        String userId = ThreadLocalUtil.getUserId();
        map.put(userId, username);
        return ResponseUtil.success("保存成功", "{}");
    }
}
