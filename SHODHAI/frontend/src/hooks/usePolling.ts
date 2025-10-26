import { useState, useEffect, useCallback } from 'react';
import { SubmissionResponse, LeaderboardEntry } from '@/types';
import { apiService } from '@/lib/api';

export function useSubmissionPolling(submissionId: number | null, interval: number = 2000) {
  const [submission, setSubmission] = useState<SubmissionResponse | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const pollSubmission = useCallback(async () => {
    if (!submissionId) return;

    try {
      setLoading(true);
      const response = await apiService.getSubmission(submissionId);
      setSubmission(response);
      setError(null);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to fetch submission');
    } finally {
      setLoading(false);
    }
  }, [submissionId]);

  useEffect(() => {
    if (!submissionId) return;

    // Initial fetch
    pollSubmission();

    // Set up polling interval
    const intervalId = setInterval(pollSubmission, interval);

    // Clean up interval when submission is completed or component unmounts
    return () => clearInterval(intervalId);
  }, [submissionId, pollSubmission, interval]);

  // Stop polling when submission is completed
  useEffect(() => {
    if (submission && submission.status !== 'PENDING' && submission.status !== 'RUNNING') {
      // Submission is completed, we can stop polling
    }
  }, [submission]);

  return { submission, loading, error, refetch: pollSubmission };
}

export function useLeaderboardPolling(contestId: number, interval: number = 15000) {
  const [leaderboard, setLeaderboard] = useState<LeaderboardEntry[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const pollLeaderboard = useCallback(async () => {
    try {
      setLoading(true);
      const response = await apiService.getLeaderboard(contestId);
      setLeaderboard(response);
      setError(null);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to fetch leaderboard');
    } finally {
      setLoading(false);
    }
  }, [contestId]);

  useEffect(() => {
    if (!contestId) return;

    // Initial fetch
    pollLeaderboard();

    // Set up polling interval
    const intervalId = setInterval(pollLeaderboard, interval);

    return () => clearInterval(intervalId);
  }, [contestId, pollLeaderboard, interval]);

  return { leaderboard, loading, error, refetch: pollLeaderboard };
}
