package com.lis.listest.entities;

import javax.persistence.*;

@Entity
@Table(name = "msgs")
public class MsgsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "sendername")
    private String sendername;

    @Column(name = "receiverid")
    private int receiverid;

    @Column(name = "text")
    private String text;

    @Column(name = "state")
    private String state;

    @Column(name = "remark")
    private String remark;

    public MsgsEntity(){}

    public MsgsEntity(int id, String sendername, int receiverid, String text, String state, String remark) {
        this.id = id;
        this.sendername = sendername;
        this.receiverid = receiverid;
        this.text = text;
        this.state = state;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public int getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(int receiverid) {
        this.receiverid = receiverid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
