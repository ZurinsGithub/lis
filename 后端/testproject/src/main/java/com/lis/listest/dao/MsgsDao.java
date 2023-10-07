package com.lis.listest.dao;

import com.lis.listest.entities.MsgsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsgsDao extends JpaRepository<MsgsEntity,Integer> {
    List<MsgsEntity> findByreceiverid(int receiverid);
    // ...
}
