'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { apiService } from '@/lib/api';

export default function JoinContest() {
  const [contestId, setContestId] = useState('');
  const [username, setUsername] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const handleJoin = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!contestId || !username) {
      setError('Please fill in all fields');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const contest = await apiService.getContest(parseInt(contestId));
      if (contest.isActive) {
        // Store username in localStorage for the session
        localStorage.setItem('username', username);
        router.push(`/contest/${contestId}`);
      } else {
        setError('Contest is not active');
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to join contest');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
      <div className="bg-white rounded-lg shadow-xl p-8 w-full max-w-md">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">Shodh AI</h1>
          <h2 className="text-xl text-gray-600">Coding Contest Platform</h2>
        </div>

        <form onSubmit={handleJoin} className="space-y-6">
          <div>
            <label htmlFor="contestId" className="block text-sm font-medium text-gray-700 mb-2">
              Contest ID
            </label>
            <input
              type="text"
              id="contestId"
              value={contestId}
              onChange={(e) => setContestId(e.target.value)}
              placeholder="Enter contest ID (Available: 1)"
              className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent text-gray-900 bg-white"
              required
            />
          </div>

          <div>
            <label htmlFor="username" className="block text-sm font-medium text-gray-700 mb-2">
              Username <span className="text-red-500">*</span> (required)
            </label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Enter your username"
              className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent text-gray-900 bg-white"
              required
            />
          </div>

          {error && (
            <div className="bg-red-50 border border-red-200 rounded-lg p-4">
              <p className="text-red-600 text-sm">{error}</p>
            </div>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 text-white py-3 px-4 rounded-lg font-medium hover:bg-blue-700 focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            {loading ? 'Joining...' : 'Join Contest'}
          </button>
        </form>

        <div className="mt-6 text-center">
          <p className="text-sm text-blue-600 font-medium mb-2">
            Available Contest: Contest ID 1 - Shodh AI Coding Challenge
          </p>
          <p className="text-sm text-gray-500">
            Don't have a contest ID? Contact your contest organizer.
          </p>
        </div>
      </div>
    </div>
  );
}