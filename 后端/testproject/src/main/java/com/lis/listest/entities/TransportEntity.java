package com.lis.listest.entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transport", schema = "dbo", catalog = "lisdb")
public class TransportEntity {
    private int id;
    private String name;
    private Integer limitedweight;
    private String info;
    private String remark;
    private String mean;
    private int stationid;

    public JSONObject toJSONobj(){
        JSONObject obj = JSON.parseObject(JSON.toJSONString(this));
        obj.put("info",JSON.parseObject(info));
        obj.put("remark",JSON.parseObject(remark));
        return obj;
    }

    public static TransportEntity fromJSONobj(JSONObject obj) {
        TransportEntity transportEntity=new TransportEntity();
        transportEntity.setLimitedweight(obj.getInteger("limitedweight"));
        transportEntity.setInfo(obj.getJSONObject("info").toString());
        transportEntity.setRemark(obj.getJSONObject("remark").toString());
        transportEntity.setName(obj.getString("name"));
        transportEntity.setMean(obj.getString("mean"));
        transportEntity.setStationid(obj.getInteger("stationid"));
        return transportEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Basic
    @Column(name = "mean")
    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    @Basic
    @Column(name = "stationid")
    public int getStationid() {
        return stationid;
    }

    public void setStationid(int stationid) {
        this.stationid = stationid;
    }

    @Basic
    @Column(name = "limitedweight")
    public Integer getLimitedweight() {
        return limitedweight;
    }

    public void setLimitedweight(Integer limitedweight) {
        this.limitedweight = limitedweight;
    }

    @Basic
    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportEntity that = (TransportEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(limitedweight, that.limitedweight) && Objects.equals(info, that.info) && Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, limitedweight, info, remark);
    }
}
