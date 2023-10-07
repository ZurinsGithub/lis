package com.lis.listest.dao;

import com.lis.listest.entities.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationDao extends JpaRepository<StationEntity,Integer> {
    List<StationEntity> findAllBybelong(String belong);
}
