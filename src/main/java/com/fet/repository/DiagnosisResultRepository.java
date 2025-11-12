package com.fet.repository;

import com.fet.model.DiagnosisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisResultRepository extends JpaRepository<DiagnosisResult, Long> {

    List<DiagnosisResult> findByStudentId(Long studentId);
}
