package com.lis.listest.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.dao.*;
import com.lis.listest.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StationService {
    @Autowired
    StationOrderDao stationOrderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TransportDao transportDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    StationDao stationDao;

    public JSONArray getOrders(JSONObject jsonObject){
        String key = jsonObject.getString("key");
        UserEntity userEntity = userDao.findByuserkey(key).get(0);
        if (userEntity.getRole() == 2) {
            int id = JSON.parseObject(userEntity.getInfo()).getInteger("stationid");
            List<StationordersEntity> list = stationOrderDao.findAllBystationid(id);
            JSONArray arr = new JSONArray();
            for(int i = 0;i<list.size();i++){
                StationordersEntity stationordersEntity = list.get(i);
                JSONObject object = JSON.parseObject(JSON.toJSONString(stationordersEntity));
                object.put("remark",JSON.parseObject(stationordersEntity.getRemark()));
                arr.add(object);
            }
            return arr;
        } else {
            return null;
        }
    }

    public String CreateTransport(JSONObject obj) {
        TransportEntity transportEntity=TransportEntity.fromJSONobj(obj);
        transportDao.save(transportEntity);
        return "1";
    }

    public JSONArray getTransport(int stationid){
        JSONArray arr = new JSONArray();
        List<TransportEntity> list = transportDao.findAllBystationid(stationid);
        for(int i = 0;i < list.size();i++){
            arr.add(list.get(i).toJSONobj());
        }
        return arr;
    }

    public String DeleteTransportById(Integer id) {
        transportDao.deleteById(id);
        return "1";
    }

    public String finishorder(String number) {
        stationOrderDao.deleteByordernumber(number);

        JSONObject obj = new JSONObject();
        obj.put("text","订单已完结");
        obj.put("sign","0-finish");
        obj.put("time",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        OrderEntity orderEntity = orderDao.findAllBynumber(number).get(0);
        orderEntity.setState(obj.toString());
        orderDao.save(orderEntity);
        return "1";
    }

    public String GetStation(Integer id) {
        StationEntity stationEntity = stationDao.getOne(id);
        return stationEntity.toJSONObj().toString();
    }

    public String ModifyStation(JSONObject obj) {
        // 需要同时修改两个表
        // 暂支持修改：名字、余量
        StationEntity stationEntity = stationDao.getOne(obj.getInteger("id"));
        stationEntity.setMargin(obj.getFloat("margin"));
        stationEntity.setName(obj.getString("name"));
        stationEntity.setTransittimes(obj.getJSONArray("transittimes").toString());
        stationDao.save(stationEntity);

        UserEntity userEntity = userDao.findByuserkey(obj.getString("key")).get(0);
        userEntity.setUsername(obj.getString("name"));
        return "1";
    }
}
