package com.lis.listest.controllers;

import com.alibaba.fastjson.JSONObject;
import com.lis.listest.services.ProductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Productor {
    @Autowired
    ProductorService productorService;
    @PostMapping("/testproject/modifyproductor")
    public String modifyinformation(@RequestBody JSONObject jsonObject){
        return productorService.ModifyInformation(jsonObject);
    }
}
