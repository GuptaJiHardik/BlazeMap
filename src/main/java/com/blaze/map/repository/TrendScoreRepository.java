package com.blaze.map.repository;

import com.blaze.map.entity.TrendScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendScoreRepository extends JpaRepository<TrendScore, Long> {
}
