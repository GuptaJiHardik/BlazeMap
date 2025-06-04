package com.blaze.map.repository;

import com.blaze.map.entity.TrendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendEntityRepository extends JpaRepository<TrendEntity, Long> {
}
