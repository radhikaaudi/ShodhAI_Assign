package com.shodh.codingcontest.repository;

import com.shodh.codingcontest.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    
    List<Problem> findByContestId(Long contestId);
    
    @Query("SELECT DISTINCT p FROM Problem p LEFT JOIN FETCH p.testCases WHERE p.contest.id = :contestId AND p.contest.isActive = true")
    List<Problem> findActiveProblemsByContestId(@Param("contestId") Long contestId);
    
    @Query("SELECT DISTINCT p FROM Problem p LEFT JOIN FETCH p.testCases WHERE p.id = :problemId AND p.contest.id = :contestId AND p.contest.isActive = true")
    Optional<Problem> findActiveProblemByIdAndContestId(@Param("problemId") Long problemId, @Param("contestId") Long contestId);
}

