package com.lis.listest.dao;

import com.lis.listest.entities.StationordersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StationOrderDao extends JpaRepository<StationordersEntity,Integer> {
    List<StationordersEntity> findAllBystationid(int key);

    List<StationordersEntity> findAllByordernumber(String number);

    @Transactional
    void deleteByordernumber(String number);
}
