package com.lis.listest.dao;

import com.lis.listest.entities.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackDao extends JpaRepository<FeedbackEntity,Integer> {
}
