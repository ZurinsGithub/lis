package com.lis.listest.services;

import com.lis.listest.dao.DaoTest;
import com.lis.listest.entities.EntityTest;
import com.lis.listest.tools.Log;
import com.lis.listest.tools.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTest {
    @Autowired
    DaoTest daoTest;

    public String AddItem(String name,String info){
        String res;
        EntityTest entityTest = new EntityTest(name,info,"");
        Log.l("name: "+name);
        //dao层不用new？
        try {
            daoTest.save(entityTest);
            res="successed";
        }catch (Exception e){
            Log.l(e.toString(), LogType.ERR);
            res="failed";
        }
        return res;
    }

    public List QueryAll(){
        List all = daoTest.findAll();
        return all;
    }
}
