package com.lis.listest.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.services.FeedbackService;
import com.lis.listest.services.ManageService;
import com.lis.listest.services.MsgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Feedback {
    @Autowired
    FeedbackService service;

    @PostMapping("/testproject/freeback/sendfreeback")
    public String SendFreeback(@RequestBody JSONObject jsonObject){
        return service.SendFreeback(jsonObject);
    }
}
