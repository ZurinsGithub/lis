package com.lis.listest.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.dao.UserDao;
import com.lis.listest.entities.UserEntity;
import com.lis.listest.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    UserDao userDao;

    public String Login(String key){
        List<UserEntity> list = userDao.findByuserkey(key);
        Log.l("login size: "+list.size());
        if(list.size()==1){
            //判断角色
            int i = list.get(0).getRole();
            if(i==1){
                //产商端
                return Login1(list);
            }else if(i==2) {
                //驿站端
                return Login2(list);
            }else if(i==3){
                //管理端
                return Login3(list);
            }
            else {
                return "{id:0,msg:\'未知的用户\'}";
            }
        }
        else {
            return "{id:0,msg:\'登陆秘钥输入错误\'}";
        }
        }

    private String Login3(List<UserEntity> list) {
        //管理端
        return Login1(list);
    }

    private String Login2(List<UserEntity> list) {
        //驿站端
        return Login1(list);
    }

    private String Login1(List<UserEntity> list) {
        //产商端
            JSONObject object = new JSONObject();
            object.put("userKey",list.get(0).getUserkey());
            object.put("userName",list.get(0).getUsername());
            object.put("info",JSONObject.parse(list.get(0).getInfo()));   // 为空时不会写入
            object.put("remark", JSON.parseObject(list.get(0).getRemark()));
            object.put("role",list.get(0).getRole());
        object.put("id",list.get(0).getId());
            return object.toString();
    }
}

//    public String Login(String key){
        // findBy? ?:列名 （可以利用错误修改功能定义在接口Dao中）
//        List<UserEntity> list = userDao.findByuserkey(key);
//        JSONArray array = new JSONArray();
//        for(int i=0;i<list.size();i++){
//            JSONObject object = new JSONObject();
//            object.put("userKey",list.get(i).getUserkey());
//            object.put("userName",list.get(i).getUsername());
//            object.put("info",list.get(i).getInfo());   // 为空时不会写入
//            object.put("remark",list.get(i).getRemark());
//            array.add(object);
//        }
//        Log.l("用户登陆返回list数组size："+list.size());
//        Log.l("用户登陆返回array数组size："+array.size());
//        Log.l("返回的数据为：" + list.get(0).getUsername());
//        Log.l("返回的json数据为：" + array);
//        if(list.size()==1){
//            return array.toString();
//        }else{
//            return "";
//        }
//    }
//}
