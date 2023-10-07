package com.lis.listest.controllers;

import com.alibaba.fastjson.JSONObject;
import com.lis.listest.services.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Manage {
    @Autowired
    ManageService service;

    @PostMapping("/testproject/addrecord")
    public String addrecord(@RequestBody JSONObject jsonObject){
        return service.AddRecord(jsonObject);
    }

    @PostMapping("/testproject/manage/getuserkey")
    public String getuserkey(@RequestBody JSONObject jsonObject) {
        return service.GetUserKey(jsonObject);
    }

    @PostMapping("/testproject/manage/modifyuserkey")
    public String modifyuserkey(@RequestBody JSONObject jsonObject) {
        return service.ModifyUserkey(jsonObject);
    }
}
