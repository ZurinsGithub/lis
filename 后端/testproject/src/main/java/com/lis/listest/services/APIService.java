package com.lis.listest.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.dao.OrderDao;
import com.lis.listest.dao.StationDao;
import com.lis.listest.dao.StationOrderDao;
import com.lis.listest.dao.TransportDao;
import com.lis.listest.entities.OrderEntity;
import com.lis.listest.entities.StationEntity;
import com.lis.listest.entities.StationordersEntity;
import com.lis.listest.entities.TransportEntity;
import com.lis.listest.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class APIService {

    @Autowired
    StationDao stationDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    StationOrderDao stationOrderDao;
    @Autowired
    TransportDao transportDao;
    @Autowired
    ManageService manageService;

    public String AddStation(JSONArray arr){
        for (int i = 0;i < arr.size();i++){
            JSONObject jsonObject = arr.getJSONObject(i);
            stationDao.save(getStationEntityByJSONObject(jsonObject));
        }
        return "1";
    }

    public JSONObject CalculateProvince(JSONObject start,JSONObject end,String mean){
        int startArea = start.getJSONObject("remark").getInteger("bigarea");
        int endArea = end.getJSONObject("remark").getInteger("bigarea");
        return CalculateProvince(startArea,endArea,mean);
    }

    public JSONObject CalculateProvince(int startArea ,int endArea,String mean){
//        计算省中转站之间的时间（两个分拣 + 运输），
//        输入：省级中转站JSONObject、运输方式，
//        输出：所有运输方式 * [时间（距离 * 单位时间） + 价格（距离 * 单位价格）]
//        该方法测试用，系统正式使用有待改进
        // 单位时间、价格/km
        double etime = 0 ;
        double eprice = 0 ;
        // 总距离、时间、价格
//        int time = 0;
        int distance = 0;
//        double price = 0;

        switch (mean) {
            case "air":
                eprice = 0.0156;
                etime = 0.0056;
                break;
            case "cold":
                eprice = 0.0143;
                etime = 0.016;
                break;
            case "road":
                eprice = 0.005;
                etime = 0.0133;
                break;
        }


        if(startArea > endArea){
            int t = startArea;
            startArea = endArea;
            endArea = t;

        }else if(startArea == endArea){
            // 同大区域，不同省，是较短距离
            distance = 1000;
        }


        // 较长距离：13、14、35
        if(startArea == 1 &&(endArea == 3||endArea==4)){
            // 是较长距离
            distance = 3000;
        }else if(startArea == 3 && endArea == 5){
            // 是较长距离
            distance = 3000;
        }else {
            // 是中等距离
            distance = 2000;
        }

        JSONObject res = new JSONObject();
        res.put("time", (int)((double)distance*etime));
        res.put("price",(int)((double)distance*eprice));
        Log.l("start: " + startArea + "   end: " + endArea + "  ep: " + eprice + "   et: " + etime + "   mean: " + mean + "   distance: " + distance);
        return res;
    }

    public JSONObject CalPrice(int startid,int endid,String mean,int weight){
        StationEntity startE=stationDao.findById(startid).get();
        StationEntity endE=stationDao.findById(endid).get();
        return CalPrice(startE.toJSONObj(),endE.toJSONObj(),mean,weight);
    }

    public JSONObject CalPrice(JSONObject startObj,JSONObject endObj,String mean,int weight){
        JSONObject res = new JSONObject();
        JSONArray parr = new JSONArray();
        // 获取完整路线
        // 市级
        StationEntity stationEntitys2 = stationDao.findById(startObj.getJSONObject("info").getInteger("tsid")).get();
        StationEntity stationEntitye2 = stationDao.findById(endObj.getJSONObject("info").getInteger("tsid")).get();

        // 省级
        StationEntity stationEntitys1 = stationDao.findById(JSON.parseObject(stationEntitys2.getInfo()).getInteger("tsid")).get();
        StationEntity stationEntitye1 = stationDao.findById(JSON.parseObject(stationEntitye2.getInfo()).getInteger("tsid")).get();

        // 测试输出部分，可删除
        JSONObject obj1 = new JSONObject();
        obj1.put("s1",stationEntitys1.toJSONObj());
        obj1.put("e1",stationEntitye1.toJSONObj());
        obj1.put("s2",stationEntitys2.toJSONObj());
        obj1.put("e2",stationEntitye2.toJSONObj());
        obj1.put("s3",startObj);
        obj1.put("e3",endObj);
        Log.l("完整路线如下：\n" + obj1.toString());

        //输出路线
        parr.add(startObj.getInteger("id"));
        parr.add(stationEntitys2.getId());
        if(stationEntitys2.getId()!=stationEntitye2.getId()){
            parr.add(stationEntitys1.getId());
            if(stationEntitys1.getId()!= stationEntitys2.getId()) {
                parr.add(stationEntitye1.getId());
//                parr.add(stationEntitys2.getId());
            }
            parr.add(stationEntitye2.getId());
        }
        parr.add(endObj.getInteger("id"));
        res.put("patharr",parr);

        // 计算价格、时间
        float price = 0;
        float time = 0;
        // 出方向驿站、市级中转站
        JSONObject s3 = getTransit(startObj.getJSONArray("transittimes"),mean,1);
        JSONObject s2 = getTransit(JSON.parseArray(stationEntitys2.getTransittimes()),mean,1);

        // 入方向驿站、市级中转站
        JSONObject e3 = getTransit(endObj.getJSONArray("transittimes"),mean,0);
        JSONObject e2 = getTransit(JSON.parseArray(stationEntitye2.getTransittimes()),mean,0);

        if(stationEntitys2.getId()!=stationEntitye2.getId()){
            time += s3.getFloat("time") + s2.getFloat("time") + e3.getFloat("time") + e2.getFloat("time");
            price += s3.getFloat("price") + s2.getFloat("price") + e3.getFloat("price") + e2.getFloat("price");
        }else {
            time +=s3.getFloat("time")+e3.getFloat("time");
            price += s3.getFloat("price") + e3.getFloat("price");

            res.put("time",time);
            res.put("price",price * weight);
//            res.put("path",parr);
            Log.l("是同市驿站中转");
            return res;
        }

        // 省级中转站判断
        if(stationEntitys1.getId()!=stationEntitye1.getId()){
            int startArea = JSON.parseObject(stationEntitys1.getRemark()).getInteger("bigarea");
            int endArea = JSON.parseObject(stationEntitye1.getRemark()).getInteger("bigarea");
            JSONObject jsonObject = CalculateProvince(startArea,endArea,mean);
            time += jsonObject.getFloat("time");
            price += jsonObject.getFloat("price");
            Log.l("不同省中转");
        }else {
            Log.l("是同一个省级中转站");
        }

        // 返回
        res.put("time",time);
        res.put("price",price * weight);
        return res;
    }

    public JSONObject getTransit(JSONArray arr,String mean,int out){
        // 计算市级中转站、驿站的时间、价格
        JSONObject res = new JSONObject();
        String str = mean;
        if(mean.equals("air")){
            str = "road";
        }
        for(int i = 0;i<arr.size();i++){
            JSONObject object = arr.getJSONObject(i);
            if(object.getInteger("out")==out&&object.getString("mean").equals(str)){
                res.put("time",object.getFloat("time"));
                res.put("price",object.getFloat("price"));
                break;
            }
        }
        Log.l("getTransit: " + res.toString());
        return res;
    }

    public StationEntity getStationEntityByJSONObject(JSONObject jsonObject){
        StationEntity stationEntity = new StationEntity();
        stationEntity.setType(jsonObject.getInteger("type"));
        stationEntity.setInfo(jsonObject.getJSONObject("info").toString());
        stationEntity.setName(jsonObject.getString("name"));
        stationEntity.setRemark(jsonObject.getJSONObject("remark").toString());
        stationEntity.setTransittimes(jsonObject.getJSONArray("transittimes").toString());
        stationEntity.setBelong(jsonObject.getString("belong"));
        stationEntity.setMargin(jsonObject.getFloat("margin"));
        return stationEntity;
    }

    public String CreateOrder(JSONObject object){
        // 获取路线、价格、时间
        int startid = object.getJSONObject("startaddress").getInteger("stationid");
        int endid = object.getJSONObject("receiveraddress").getInteger("stationid");
        StationEntity start = stationDao.findById(startid).get();
        StationEntity end = stationDao.findById(endid).get();
        JSONObject pathres = CalPrice(start.toJSONObj(),end.toJSONObj(),object.getJSONObject("productinfo").getString("mean"),(int)(object.getJSONObject("productinfo").getFloat("weight")+0.99));
        JSONObject productinfoobj = object.getJSONObject("productinfo");
        productinfoobj.put("price",pathres.getFloat("price"));
        productinfoobj.put("time",pathres.getInteger("time"));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProductinfo(productinfoobj.toString());

        orderEntity.setProductorkey(object.getString("productorkey"));
        orderEntity.setProductor(object.getJSONObject("productor").toString());
        orderEntity.setStartaddress(object.getJSONObject("startaddress").toString());
        orderEntity.setReceiver(object.getJSONObject("receiver").toString());
        orderEntity.setReceiveraddress(object.getJSONObject("receiveraddress").toString());
        orderEntity.setSourceinfo(object.getJSONObject("sourceinfo").toString());
        orderEntity.setRemark(object.getJSONObject("remark").toString());

        orderEntity.setStarttime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        orderEntity.setNumber(new SimpleDateFormat("yyMMddhhmmss").format(new Date()) + (int)(Math.random() * 1000));
        orderEntity.setPath(pathres.getJSONArray("patharr").toString());
        orderEntity.setTransportinfo("{}");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "等待驿站揽件");
        jsonObject.put("time", orderEntity.getStarttime());
        jsonObject.put("sign",pathres.getJSONArray("patharr").get(0).toString() + "-wait");
        orderEntity.setState(jsonObject.toString());
        Log.l("state:" + orderEntity.getState());

        orderDao.save(orderEntity);
        // 耦合性：交给下一个站点处理？
        // 减少所有站点余量
        NextHandle(orderEntity,productinfoobj,pathres.getJSONArray("patharr"));


        Log.l(orderEntity.toJSONObj().toString());
        return orderEntity.toJSONObj().toString();
    }

    private void NextHandle(OrderEntity orderEntity,JSONObject productinfoobj,JSONArray patharr) {
        // 下单时提交到驿站表，生成一个待处理记录，并减少余量
//        提交
        StationordersEntity stationordersEntity = StationordersEntity.FromOrder(orderEntity,productinfoobj,patharr.getInteger(0));
        stationOrderDao.save(stationordersEntity);
        // 减少余量
        StationEntity stationEntity = stationDao.getOne(patharr.getInteger(0));
        stationEntity.setMargin(stationEntity.getMargin() - productinfoobj.getInteger("weight"));
        stationDao.save(stationEntity);
    }

    public JSONArray getStationsByConditions(JSONObject cond){
        JSONArray jsonArray = new JSONArray();

        int weight = cond.getInteger("weight");
        int out = cond.getInteger("out");
        String mean = cond.getString("mean");
        String station = cond.getString("station");

        List<StationEntity> list = stationDao.findAllBybelong(station);

        for(int i = 0;i<list.size();i++){
            StationEntity stationEntity = list.get(i);

            if(stationEntity.getMargin()>=weight)
            {
                JSONArray j = JSON.parseArray(stationEntity.getTransittimes());
                for (int o = 0; o < j.size(); o++) {
                    JSONObject obj = j.getJSONObject(o);
                    if(mean.equals("air")){
                        // 航运在驿站、市级中转站采用公路运输
                        mean = "road";
                    }
                    if (obj.getString("mean").equals(mean) && obj.getInteger("out") == out) {
                        jsonArray.add(stationEntity.toJSONObj());
                        break;
                    }
                }
            }

        }

        return jsonArray;
    }

    public String getOrdersByuserKsy(String userKey){
        JSONArray arr = new JSONArray();
        List<OrderEntity> res = orderDao.findAllByproductorkey(userKey);
        for (int i = 0 ; i < res.size() ; i++){
            arr.add(res.get(i).toJSONObj());
        }
        return arr.toString();
    }

    public String ClearAllOrdersByuserKey(String userKey){
        orderDao.deleteByproductorkey(userKey);
        return "1";
    }

    public String deleteOrdersByUserKeyAndOrderNums(JSONArray orderNums,String userKey){
        for (int i = 0;i<orderNums.size();i++){
            OrderEntity orderEntity = orderDao.findAllBynumber(orderNums.getString(i)).get(0);
            if(orderEntity.getProductorkey().equals( userKey)){
                orderDao.delete(orderEntity);
            }
        }
        return "1";
    }

    public String getOrderByNumber(String number){
        OrderEntity orderEntity = orderDao.findAllBynumber(number).get(0);
        orderEntity.setProductorkey("");
        return orderEntity.toJSONObj().toString();
    }

    public String getOrdersByNumber(JSONArray arr){
        JSONArray res = new JSONArray();
        for(int i=0;i<arr.size();i++){
            OrderEntity orderEntity = orderDao.findAllBynumber(arr.getString(i)).get(0);
            orderEntity.setProductorkey("");
            res.add(orderEntity.toJSONObj());
        }
        return res.toString();
    }

    public String ModifyStateInBuntch(JSONObject obj){
        JSONArray arr = obj.getJSONArray("ordernumbers");
        for(int i =0;i<arr.size();i++){
            obj.put("number",arr.getString(i));
            ModifyState(obj);
        }
        return "1";
    }

    public String ModifyState(JSONObject obj){
        // 修改订单状态
        String number = obj.getString("number");
        int stationid = obj.getInteger("id");
        String text = obj.getString("text");
        String state = obj.getString("state");

        StationordersEntity stationordersEntity = stationOrderDao.findAllByordernumber(number).get(0);
        JSONObject stateobj = JSON.parseObject(stationordersEntity.getRemark());
        stateobj.put("state",state);
        stationordersEntity.setRemark(stateobj.toString());
        stationordersEntity.setStationid(stationid);
        stationOrderDao.save(stationordersEntity);

        OrderEntity orderEntity = orderDao.findAllBynumber(number).get(0);
        JSONObject stateobj1 = JSON.parseObject(orderEntity.getState());
        stateobj1.put("sign",stationid + "-" + state);
        stateobj1.put("text",text);
        stateobj1.put("time",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        orderEntity.setState(stateobj1.toString());

        String transportinfo = obj.getString("transportinfo");
        orderEntity.setTransportinfo(transportinfo);
        orderDao.save(orderEntity);

        return "1";
    }

    public String getTransinfo(Integer id) {
        TransportEntity transportEntity = transportDao.getOne(id);
//        Log.l(transportEntity.getInfo());
        return JSON.parseObject(transportEntity.getInfo()).toString();
    }

    public String QueryOrder(String number) {
//        Log.l("query: "+number);
        OrderEntity orderEntity = orderDao.findAllBynumber(number).get(0);
        return orderEntity.toJSONObj().toString();
    }

    // 默认是normal类型的溯源信息
    public void addSourceRecord(String number,String text){
        JSONObject obj = JSON.parseObject("{\n" +
                "            \"type\":\"normal\",\n" +
                "            \"mark\":\"\"\n" +
                "          }");
        addSourceRecord(number,text,obj);
    }

    // 可以是其他类型的溯源信息
    private void addSourceRecord(String number, String text, JSONObject obj) {
        JSONObject recobj = new JSONObject();
        recobj.put("number",number);
        recobj.put("text",text);
        recobj.put("json", obj);
        manageService.AddRecord(recobj);
    }

    public void addSourceRecordInBuntch(JSONArray ordernumbers, String sourcetext, JSONObject json) {
        for(int i = 0;i<ordernumbers.size();i++){
            addSourceRecord(ordernumbers.getString(i),sourcetext,json);
        }
    }
}
