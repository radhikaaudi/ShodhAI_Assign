package com.shodh.codingcontest.repository;

import com.shodh.codingcontest.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    
    Optional<Contest> findByIdAndIsActiveTrue(Long id);
    
    List<Contest> findByIsActiveTrue();
    
    @Query("SELECT c FROM Contest c WHERE c.isActive = true AND c.startTime <= CURRENT_TIMESTAMP AND c.endTime >= CURRENT_TIMESTAMP")
    List<Contest> findActiveContests();
    
    @Query("SELECT c FROM Contest c WHERE c.id = :contestId AND c.isActive = true")
    Optional<Contest> findActiveContestById(@Param("contestId") Long contestId);
}

