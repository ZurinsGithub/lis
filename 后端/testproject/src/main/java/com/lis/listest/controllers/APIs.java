package com.lis.listest.controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.entities.OrderEntity;
import com.lis.listest.services.APIService;
import com.lis.listest.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class APIs {
    @Autowired
    private APIService service;

    @PostMapping("/testproject/manage/addstation")
    public String addstation(@RequestBody JSONArray arr){
        return service.AddStation(arr);
    }

    @GetMapping("/testproject/manage/testcal")
    public String testcal(@RequestBody JSONObject obj){
        return service.CalculateProvince(obj.getJSONObject("startp"),obj.getJSONObject("endp"),obj.getString("mean")).toString();
    }

    // 提供给管理端
    @PostMapping("/testproject/manage/testcalprice")
    public String testcalprice(@RequestBody JSONObject obj){
        return service.CalPrice(obj.getJSONObject("start"),obj.getJSONObject("end"),obj.getString("mean"),obj.getInteger("weight")).toString();
    }

    // 提供给客户端
    @PostMapping("/testproject/apis/calprice")
    public String calprice(@RequestBody JSONObject obj){
        return service.CalPrice(obj.getInteger("start"),obj.getInteger("end"),obj.getString("mean"),obj.getInteger("weight")).toString();
    }

    @PostMapping("/testproject/apis/createorder")
    public String CreateOrder(@RequestBody JSONObject obj){
        return service.CreateOrder(obj);
    }

    @PostMapping("/testproject/apis/querystations")
    public String querystations(@RequestBody JSONObject obj){
        return service.getStationsByConditions(obj).toString();
    }

    @PostMapping("/testproject/apis/usergetorders")
    public String usergetorders(@RequestParam String userKey){
        Log.l(userKey);
        return service.getOrdersByuserKsy(userKey);
    }

    @PostMapping("/testproject/apis/clearorders")
    public String clearorders(@RequestBody String userKey){
        return service.ClearAllOrdersByuserKey(userKey);
    }

    @PostMapping("/testproject/apis/deleteorders")
    public String deleteorders(@RequestBody JSONObject obj)
    {
        String userKey = obj.getString("key");
        JSONArray arr = obj.getJSONArray("ordernums");
        return service.deleteOrdersByUserKeyAndOrderNums(arr,userKey);
    }

    @PostMapping("/testproject/apis/getorderbynum")
    public String getorderbynum(@RequestBody JSONObject obj){
        return service.getOrderByNumber(obj.getString("number"));
    }
    @PostMapping("/testproject/apis/getordersbynum")
    public String getordersbynum(@RequestBody JSONArray obj){
        return service.getOrdersByNumber(obj);
    }

    @PostMapping("/testproject/apis/modifystate")
    public String modifystate(@RequestBody JSONObject obj){
        return service.ModifyState(obj);
    }

    @PostMapping("/testproject/apis/modifystateinbuntch")
    public String modifystateinbuntch(@RequestBody JSONObject jsonObject){
        return service.ModifyStateInBuntch(jsonObject);
    }

    @PostMapping("/testproject/apis/gettransinfo")
    public String gettransinfo(@RequestBody JSONObject jsonObject){
        return service.getTransinfo(jsonObject.getInteger("id"));
    }

    @PostMapping("/testproject/apis/queryorder")
    public String queryorder(@RequestBody JSONObject jsonObject){
        return service.QueryOrder(jsonObject.getString("number"));
    }
}
