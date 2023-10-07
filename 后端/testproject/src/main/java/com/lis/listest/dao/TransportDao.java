package com.lis.listest.dao;

import com.lis.listest.entities.TransportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public   interface TransportDao extends JpaRepository<TransportEntity,Integer> {
    List<TransportEntity> findAllBystationid(int stationid);
}
