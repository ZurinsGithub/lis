package com.lis.listest.controllers;

import com.alibaba.fastjson.JSONObject;
import com.lis.listest.services.LoginService;
import com.lis.listest.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Login {
    @Autowired
    LoginService loginService;
    @PostMapping("/testproject/login")
    public String Login(@RequestBody JSONObject jsonObject)
    {
        String key = jsonObject.getString("key");
        Log.l("获取的参数: "+key);
        return loginService.Login(key);
    }
}
