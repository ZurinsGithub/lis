package com.lis.listest.services;

import com.alibaba.fastjson.JSONObject;
import com.lis.listest.dao.UserDao;
import com.lis.listest.entities.UserEntity;
import com.lis.listest.tools.Log;
import com.lis.listest.tools.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductorService {
    @Autowired
    UserDao userDao;

    public String ModifyInformation(JSONObject jsonObject){
        String res = "";
        List<UserEntity> list = userDao.findByuserkey(jsonObject.getString("userKey"));
        UserEntity userEntity = list.get(0);
        userEntity.setUsername(jsonObject.getString("userName"));
        userEntity.setInfo(jsonObject.getJSONObject("info").toString());
        Log.l(jsonObject.getJSONObject("info").toString());
        try {
            userDao.save(userEntity);
            Log.l("用户信息修改：" + jsonObject.toString());
            res = "1";
        }catch (Exception e){
            res = "0";
            Log.l(e.getMessage(), LogType.ERR);
        }
        return res;
    }
}
