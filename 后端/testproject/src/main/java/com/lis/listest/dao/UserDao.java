package com.lis.listest.dao;

import com.lis.listest.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao  extends JpaRepository<UserEntity,Integer> {
    List<UserEntity> findByuserkey(String userkey);
}
