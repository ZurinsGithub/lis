package com.lis.listest.dao;

import com.lis.listest.entities.EntityTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DaoTest extends JpaRepository<EntityTest,Integer> {
    //SpringBoot简化了Dao层，省略了连接sql的代码
}
