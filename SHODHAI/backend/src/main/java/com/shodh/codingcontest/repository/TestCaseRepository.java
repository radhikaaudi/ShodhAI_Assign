package com.shodh.codingcontest.repository;

import com.shodh.codingcontest.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    
    List<TestCase> findByProblemId(Long problemId);
    
    List<TestCase> findByProblemIdAndIsHiddenFalse(Long problemId);
}

