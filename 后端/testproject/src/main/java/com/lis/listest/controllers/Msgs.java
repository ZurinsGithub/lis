package com.lis.listest.controllers;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.services.MsgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Msgs {

    @Autowired
    MsgsService msgsService;

    @PostMapping("/testproject/msgs/send")
    public String send(@RequestBody JSONObject msg){
        return msgsService.AddMsg(msg);
    }

    @PostMapping("/testproject/msgs/get")
    public String get(@RequestBody JSONObject obj){
        String key = obj.getString("key");
        return msgsService.GetMsgs(key);
    }

    @PostMapping("/testproject/msgs/modify")
    public String modify(@RequestBody JSONArray arr){
        return msgsService.modifyMsgs(arr);
    }
}
