package com.lis.listest.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.dao.OrderDao;
import com.lis.listest.dao.UserDao;
import com.lis.listest.entities.OrderEntity;
import com.lis.listest.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ManageService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    UserDao userDao;

    public String AddRecord(JSONObject jsonObject) {
        OrderEntity orderEntity = orderDao.findAllBynumber(jsonObject.getString("number")).get(0);
        JSONObject recobj = new JSONObject();
        recobj.put("content", JSON.parseObject(jsonObject.getString("json")));
        recobj.put("text",jsonObject.getString("text"));
        recobj.put("time",new SimpleDateFormat("yyMMdd hh:mm:ss").format(new Date()));
        JSONObject souobj = JSON.parseObject(orderEntity.getSourceinfo());
        JSONArray recarr = souobj.getJSONArray("records");
        recarr.add(recobj);
        souobj.put("records",recarr);
        orderEntity.setSourceinfo(souobj.toJSONString());
        orderDao.save(orderEntity);
        return "1";
    }

    public String GetUserKey(JSONObject jsonObject) {
        UserEntity userEntity = userDao.findByuserkey(jsonObject.getString("userKey")).get(0);
        UserEntity res = new UserEntity();
        res.setUsername(userEntity.getUsername());
        res.setRole(userEntity.getRole());
        res.setUserkey(userEntity.getUserkey());
        return userEntity.toJSONObj().toString();
    }

    public String ModifyUserkey(JSONObject jsonObject) {
        UserEntity userEntity = userDao.findByuserkey(jsonObject.getString("tarKey")).get(0);
        userEntity.setUserkey(jsonObject.getString("userKey"));
        userEntity.setRole(jsonObject.getInteger("role"));
        userDao.save(userEntity);
        return "1";
    }
}
