package com.lis.listest.entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stationorders", schema = "dbo", catalog = "lisdb")
public class StationordersEntity {
    private Long id;
    private String ordernumber;
    private Integer weight;
    private String path;
    private String mean;
    private String remark;
    private int stationid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = (long)id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ordernumber")
    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    @Basic
    @Column(name = "weight")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        StationordersEntity that = (StationordersEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(ordernumber, that.ordernumber) && Objects.equals(weight, that.weight) && Objects.equals(path, that.path) && Objects.equals(mean, that.mean) && Objects.equals(remark, that.remark);
    }

    public int getStationid() {
        return stationid;
    }

    public void setStationid(int stationid) {
        this.stationid = stationid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ordernumber, weight, path, mean, remark);
    }

    public static StationordersEntity FromOrder(OrderEntity orderEntity,JSONObject productinfoobj,int stationid){
        StationordersEntity stationordersEntity = new StationordersEntity();
        stationordersEntity.setPath(orderEntity.getPath());
        stationordersEntity.setMean(productinfoobj.getString("mean"));
        stationordersEntity.setWeight(productinfoobj.getInteger("weight"));
        stationordersEntity.setOrdernumber(orderEntity.getNumber());
        stationordersEntity.setStationid(stationid);

        JSONObject obj = new JSONObject();
        obj.put("state","wait");
        obj.put("phone",JSON.parseObject(orderEntity.getProductor()).getString("phone"));
        obj.put("productor",JSON.parseObject(orderEntity.getProductor()).getString("name"));
        stationordersEntity.setRemark(obj.toString());
        return stationordersEntity;
    }
}
