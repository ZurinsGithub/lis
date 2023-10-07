package com.lis.listest.entities;

import org.hibernate.annotations.GenericGenerator;

// import lombok.Data;

import javax.persistence.*;

//https://www.cnblogs.com/pascall/p/10280825.html https://www.cnblogs.com/caofanqi/p/11748569.html  jpa常用注解

@Entity
@Table(name = "test")
// @Data        //未知的注解
public class EntityTest {
    @Id
    // @GenericGenerator(name = "idGenerator", strategy = "uuid")
    // 更多主键生成策略；
    // 主键生成策略：UUID
    // 下面的@GeneratedValue（JPA注解）使用generator = "idGenerator"引用了上面的name = "idGenerator"主键生成策略
    //@GeneratedValue(generator = "idGenerator", strategy = GenerationType.IDENTITY)
    //https://www.jianshu.com/p/c23c82a8fcfc
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    TABLE： 使用一个特定的数据库表格来保存主键
    //    SEQUENCE： 根据底层数据库的序列来生成主键，条件是数据库支持序列。这个值要与generator一起使用，generator 指定生成主键使用的生成器（可能是orcale中自己编写的序列）
    //    IDENTITY： 主键由数据库自动生成（主要是支持自动增长的数据库，如mysql）
    //    AUTO： 主键由程序控制，也是GenerationType的默认值

    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = false, nullable = true, length = 64)
    private String testname;
    @Column(name = "info")
    private String testinfo;
    @Column(name = "userkey")
    private String testuserkey;
    @Column(name = "phone")
    private String phone;

    public EntityTest() {
        // 必须的构造函数
    }


    public EntityTest(String testname, String testinfo,String testuserkey) {
        this.testname = testname;
        this.testinfo = testinfo;
        this.testuserkey = testuserkey;
    }
    public EntityTest( String testname, String testinfo, String testuserkey, String phone) {
        this.testname = testname;
        this.testinfo = testinfo;
        this.testuserkey = testuserkey;
        this.phone = phone;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getTestuserkey() {
        return testuserkey;
    }

    public void setTestuserkey(String testuserkey) {
        this.testuserkey = testuserkey;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public String getTestinfo() {
        return testinfo;
    }

    public void setTestinfo(String testinfo) {
        this.testinfo = testinfo;
    }
}
