package com.fet.repository;

import com.fet.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {

    List<Metric> findByStudentId(Long studentId);
}
