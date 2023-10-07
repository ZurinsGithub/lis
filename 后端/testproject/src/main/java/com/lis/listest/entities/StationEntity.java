package com.lis.listest.entities;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "stations")
public class StationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;


    @Column(name = "type")
    private int type;


    @Column(name = "transittimes")
    private String transittimes;


    @Column(name = "info")
    private String info;


    @Column(name = "remark")
    private String remark;

    @Column(name = "belong")
    private String belong;

    @Column(name = "margin")
    private float margin;

    public StationEntity(int id, String name, int type, String transittimes, String info, String remark, String belong, float margin) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.transittimes = transittimes;
        this.info = info;
        this.remark = remark;
        this.belong = belong;
        this.margin = margin;
    }

    public StationEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTransittimes() {
        return transittimes;
    }

    public void setTransittimes(String transittimes) {
        this.transittimes = transittimes;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }



    public JSONObject toJSONObj(){
        JSONObject obj = new JSONObject();
        obj.put("id",this.getId());
        obj.put("name",this.getName());
        obj.put("type",this.getType());
        obj.put("belong",this.getBelong());
        obj.put("margin",this.getMargin());
        obj.put("transittimes", JSON.parseArray(this.getTransittimes()));
        obj.put("info", JSON.parseObject(this.getInfo()));
        obj.put("remark", JSON.parseObject(this.getRemark()));
        return obj;
    }
}
