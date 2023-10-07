package com.lis.listest.entities;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "feedback", schema = "dbo", catalog = "lisdb")
public class FeedbackEntity {
    private int id;
    private String info;
    private String ordernumber;
    private String text;
    private String remark;

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
    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        FeedbackEntity that = (FeedbackEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(info, that.info) && Objects.equals(ordernumber, that.ordernumber) && Objects.equals(text, that.text) && Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, info, ordernumber, text, remark);
    }

    public static FeedbackEntity fromjsonobj(JSONObject object){
        FeedbackEntity feedbackEntity = new FeedbackEntity();
        feedbackEntity.setOrdernumber(object.getString("ordernumber"));
        feedbackEntity.setText(object.getString("text"));
        try{
        feedbackEntity.setInfo(object.getJSONObject("info").toString());
        }catch (Exception ignored){
            feedbackEntity.setInfo("{}");
        }
        try {
            feedbackEntity.setRemark(object.getJSONObject("remark").toString());
        }catch (Exception ignored){
            feedbackEntity.setRemark("{}");
        }
        return feedbackEntity;
    }
}
