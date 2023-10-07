package com.lis.listest.entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "userkey")
    private String userkey;

    @Column(name = "info")
    private String info;

    @Column(name = "username")
    private String username;

    @Column(name = "remark")
    private String remark;

    @Column(name="role")
    private int role;

    public UserEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public UserEntity(int id, String userkey, String info, String username, String remark) {
        this.id = id;
        this.userkey = userkey;
        this.info = info;
        this.username = username;
        this.remark = remark;
    }

    public UserEntity(int id, String userkey, String info, String username, String remark, int role) {
        this.id = id;
        this.userkey = userkey;
        this.info = info;
        this.username = username;
        this.remark = remark;
        this.role = role;
    }

    public JSONObject toJSONObj(){
        JSONObject object = new JSONObject();
        object.put("info", JSON.parseObject(info));
        object.put("remark", JSON.parseObject(remark));
        object.put("id",id);
        object.put("role",role);
        object.put("userKey",userkey);
        object.put("userName",username);
        return object;
    }
}
