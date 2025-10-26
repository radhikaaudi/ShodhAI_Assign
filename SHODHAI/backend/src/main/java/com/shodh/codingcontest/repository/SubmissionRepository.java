package com.shodh.codingcontest.repository;

import com.shodh.codingcontest.model.Submission;
import com.shodh.codingcontest.model.SubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    
    List<Submission> findByContestIdOrderBySubmissionTimeDesc(Long contestId);
    
    List<Submission> findByContestIdAndUsernameOrderBySubmissionTimeDesc(Long contestId, String username);
    
    List<Submission> findByContestIdAndProblemIdOrderBySubmissionTimeDesc(Long contestId, Long problemId);
    
    @Query("SELECT s FROM Submission s WHERE s.contest.id = :contestId AND s.username = :username ORDER BY s.submissionTime DESC")
    List<Submission> findUserSubmissionsInContest(@Param("contestId") Long contestId, @Param("username") String username);
    
    @Query("SELECT s FROM Submission s WHERE s.contest.id = :contestId AND s.status = :status ORDER BY s.submissionTime DESC")
    List<Submission> findSubmissionsByContestAndStatus(@Param("contestId") Long contestId, @Param("status") SubmissionStatus status);
    
    @Query("SELECT DISTINCT s.username FROM Submission s WHERE s.contest.id = :contestId")
    List<String> findDistinctUsernamesByContestId(@Param("contestId") Long contestId);
    
    @Query("SELECT s FROM Submission s WHERE s.contest.id = :contestId AND s.status = 'ACCEPTED' ORDER BY s.submissionTime ASC")
    List<Submission> findAcceptedSubmissionsByContestOrderByTime(@Param("contestId") Long contestId);
    
    @Query("SELECT s FROM Submission s JOIN FETCH s.problem p JOIN FETCH p.testCases WHERE s.id = :submissionId")
    Optional<Submission> findByIdWithProblemAndTestCases(@Param("submissionId") Long submissionId);
}

