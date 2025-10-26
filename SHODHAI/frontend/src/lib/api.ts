import { Contest, SubmissionRequest, SubmissionResponse, LeaderboardEntry } from '@/types';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

class ApiService {
  private async request<T>(endpoint: string, options?: RequestInit): Promise<T> {
    const url = `${API_BASE_URL}${endpoint}`;
    const response = await fetch(url, {
      headers: {
        'Content-Type': 'application/json',
        ...options?.headers,
      },
      ...options,
    });

    if (!response.ok) {
      throw new Error(`API request failed: ${response.status} ${response.statusText}`);
    }

    return response.json();
  }

  // Contest endpoints
  async getContests(): Promise<Contest[]> {
    return this.request<Contest[]>('/contests');
  }

  async getContest(contestId: number): Promise<Contest> {
    return this.request<Contest>(`/contests/${contestId}`);
  }

  async getLeaderboard(contestId: number): Promise<LeaderboardEntry[]> {
    return this.request<LeaderboardEntry[]>(`/contests/${contestId}/leaderboard`);
  }

  // Submission endpoints
  async submitCode(submission: SubmissionRequest): Promise<SubmissionResponse> {
    return this.request<SubmissionResponse>('/submissions', {
      method: 'POST',
      body: JSON.stringify(submission),
    });
  }

  async getSubmission(submissionId: number): Promise<SubmissionResponse> {
    return this.request<SubmissionResponse>(`/submissions/${submissionId}`);
  }
}

export const apiService = new ApiService();
