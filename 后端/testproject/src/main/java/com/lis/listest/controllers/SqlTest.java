package com.lis.listest.controllers;

import com.lis.listest.services.ServiceTest;
import com.lis.listest.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SqlTest {
    @Autowired
    ServiceTest serviceTest;

    @RequestMapping("/testproject/sqltest")
    public String sqltest()
    {
        return "test sucess";
    }

    @PostMapping("/testproject/handletest")
    public String handletest(@RequestParam("name") String name,@RequestParam("info") String info){

        //发送给Service层处理
        return serviceTest.AddItem(name,info);
    }


    @PostMapping("/testproject/querytest")
    public List querytest(){
        return serviceTest.QueryAll();
    }
}
