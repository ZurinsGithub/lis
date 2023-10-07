package com.lis.listest.entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.tools.Log;
import com.lis.listest.tools.LogType;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number")
    private String number;

    @Column(name = "productorkey")
    private String productorkey;

    @Column(name = "productor")
    private String productor;

    @Column(name = "startaddress")
    private String startaddress;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "receiveraddress")
    private String receiveraddress;

    @Column(name = "starttime")
    private String starttime;

    @Column(name = "path")
    private String path;

    @Column(name = "productinfo")
    private String productinfo;

    @Column(name = "state")
    private String state;

    @Column(name = "sourceinfo")
    private String sourceinfo;

    @Column(name = "transportinfo")
    private String transportinfo;

    @Column(name = "remark")
    private String remark;

    public OrderEntity(int id, String number, String productorkey, String productor, String startaddress, String receiver, String receiveraddress, String starttime, String path, String productinfo, String state, String sourceinfo, String transportinfo, String remark) {
        this.id = id;
        this.number = number;
        this.productorkey = productorkey;
        this.productor = productor;
        this.startaddress = startaddress;
        this.receiver = receiver;
        this.receiveraddress = receiveraddress;
        this.starttime = starttime;
        this.path = path;
        this.productinfo = productinfo;
        this.state = state;
        this.sourceinfo = sourceinfo;
        this.transportinfo = transportinfo;
        this.remark = remark;
    }

    public OrderEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProductorkey() {
        return productorkey;
    }

    public void setProductorkey(String productorkey) {
        this.productorkey = productorkey;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getStartaddress() {
        return startaddress;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiveraddress() {
        return receiveraddress;
    }

    public void setReceiveraddress(String receiveraddress) {
        this.receiveraddress = receiveraddress;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSourceinfo() {
        return sourceinfo;
    }

    public void setSourceinfo(String sourceinfo) {
        this.sourceinfo = sourceinfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransportinfo() {
        return transportinfo;
    }

    public void setTransportinfo(String transportinfo) {
        this.transportinfo = transportinfo;
    }

    public JSONObject toJSONObj(){
        JSONObject obj = new JSONObject();
        obj.put("productorkey",this.productorkey);
        obj.put("starttime",this.starttime);
        obj.put("number",this.number);
        obj.put("productor", JSON.parseObject(this.productor));
        obj.put("startaddress", JSON.parseObject(this.startaddress));
        obj.put("receiver", JSON.parseObject(this.receiver));
        obj.put("receiveraddress", JSON.parseObject(this.receiveraddress));
        obj.put("productinfo", JSON.parseObject(this.productinfo));
        obj.put("sourceinfo", JSON.parseObject(this.sourceinfo));
        obj.put("remark", JSON.parseObject(this.remark));
        try {
            obj.put("transportinfo", JSON.parseObject(this.transportinfo));
        }
        catch (Exception e){
            Log.l("警告：trans为空");
        }
        obj.put("state", JSON.parseObject(this.state));
        obj.put("path", JSON.parseArray(this.path));
        return obj;
    }
}
