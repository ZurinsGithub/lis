package com.lis.listest.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.dao.MsgsDao;
import com.lis.listest.dao.UserDao;
import com.lis.listest.entities.MsgsEntity;
import com.lis.listest.entities.UserEntity;
import com.lis.listest.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MsgsService {
    @Autowired
    MsgsDao msgsDao;
    @Autowired
    UserDao userDao;

    public String AddMsg(JSONObject msgjson){
        MsgsEntity msgsEntity = new MsgsEntity();
        msgsEntity.setRemark(msgjson.getJSONObject("remark").toString());
        msgsEntity.setReceiverid(msgjson.getInteger("receiverid"));
        msgsEntity.setSendername(msgjson.getString("sendername"));
        msgsEntity.setState(msgjson.getString("state"));
        msgsEntity.setText(msgjson.getJSONObject("text").toString());
        msgsDao.save(msgsEntity);
        Log.l("成功添加消息：" + msgjson.toString());
        return "1";
    }

    public void AddMsg(MsgsEntity msg){
        msgsDao.save(msg);
    }

    public String GetMsgs(String receiverkey){
        // 获取接收者id
        List<UserEntity> l = userDao.findByuserkey(receiverkey);
        int receiverid = l.get(0).getId();
        return GetMsgs(receiverid);
    }

    public String GetMsgs(int receiverid){
        // 获取消息
        List<MsgsEntity> list = msgsDao.findByreceiverid(receiverid);
        JSONArray jsonArray = new JSONArray();
        for(int i = 0;i < list.size();i++){
            JSONObject jsonObject = new JSONObject();
            MsgsEntity u = list.get(i);
            jsonObject.put("sendername",u.getSendername());
            jsonObject.put("receiverid",u.getReceiverid());
            jsonObject.put("remark",u.getRemark());
            jsonObject.put("id",u.getId());
            jsonObject.put("state",u.getState());
            jsonObject.put("text",JSONObject.parse(u.getText()));
            jsonArray.add(jsonObject);
        }
        Log.l(jsonArray.toString());
        return jsonArray.toString();
//        String str = JSON.toJSON(list).toString();
//        Log.l(str);
//        JSONArray array = JSONObject.parseArray(str);
//        Log.l(array.toString());
//        return str;
    }

    public String modifyMsgs(JSONArray arr){
        for(int i = 0;i<arr.size();i++){
            JSONObject obj = arr.getJSONObject(i);
            // https://blog.csdn.net/qq_35620501/article/details/93627016?utm_term=findAllById%20jpa%20%E4%BD%BF%E7%94%A8&utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~all~sobaiduweb~default-1-93627016&spm=3001.4430
            Optional<MsgsEntity> optional = msgsDao.findById(obj.getInteger("id"));
            MsgsEntity msgsEntity = optional.get();
            // 只能修改这三个
            msgsEntity.setText(obj.getJSONObject("text").toString());
            msgsEntity.setState(obj.getString("state"));
            msgsEntity.setRemark(obj.getString("remark"));
            msgsDao.save(msgsEntity);
        }
        return GetMsgs(arr.getJSONObject(0).getInteger("receiverid"));
    }
}
