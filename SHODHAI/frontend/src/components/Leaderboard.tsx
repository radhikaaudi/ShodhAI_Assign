'use client';

import { LeaderboardEntry } from '@/types';
import { useLeaderboardPolling } from '@/hooks/usePolling';

interface LeaderboardProps {
  contestId: number;
}

export default function Leaderboard({ contestId }: LeaderboardProps) {
  const { leaderboard, loading, error } = useLeaderboardPolling(contestId);

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'ACCEPTED':
        return 'text-green-600';
      case 'WRONG_ANSWER':
        return 'text-red-600';
      case 'TIME_LIMIT_EXCEEDED':
        return 'text-yellow-600';
      case 'MEMORY_LIMIT_EXCEEDED':
        return 'text-orange-600';
      case 'RUNTIME_ERROR':
        return 'text-red-500';
      case 'COMPILATION_ERROR':
        return 'text-purple-600';
      default:
        return 'text-gray-600';
    }
  };

  if (loading && leaderboard.length === 0) {
    return (
      <div className="bg-white rounded-lg shadow-sm border">
        <div className="p-4 border-b">
          <h2 className="text-lg font-semibold text-gray-900">Leaderboard</h2>
        </div>
        <div className="p-4">
          <div className="animate-pulse space-y-3">
            {[...Array(5)].map((_, i) => (
              <div key={i} className="h-8 bg-gray-200 rounded"></div>
            ))}
          </div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-white rounded-lg shadow-sm border">
        <div className="p-4 border-b">
          <h2 className="text-lg font-semibold text-gray-900">Leaderboard</h2>
        </div>
        <div className="p-4">
          <p className="text-red-600 text-sm">{error}</p>
        </div>
      </div>
    );
  }

  return (
    <div className="bg-white rounded-lg shadow-sm border">
      <div className="p-4 border-b">
        <h2 className="text-lg font-semibold text-gray-900">Leaderboard</h2>
        <p className="text-sm text-gray-500">Updates every 15 seconds</p>
      </div>
      <div className="p-4">
        {leaderboard.length === 0 ? (
          <p className="text-gray-500 text-center py-4">No submissions yet</p>
        ) : (
          <div className="space-y-2">
            {leaderboard.map((entry, index) => (
              <div
                key={entry.username}
                className={`flex items-center justify-between p-3 rounded-lg ${
                  index < 3 ? 'bg-gradient-to-r from-yellow-50 to-yellow-100' : 'bg-gray-50'
                }`}
              >
                <div className="flex items-center space-x-3">
                  <div className={`w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold ${
                    index === 0 ? 'bg-yellow-400 text-white' :
                    index === 1 ? 'bg-gray-300 text-white' :
                    index === 2 ? 'bg-orange-400 text-white' :
                    'bg-gray-200 text-gray-700'
                  }`}>
                    {index + 1}
                  </div>
                  <div>
                    <p className="font-medium text-gray-900">{entry.username}</p>
                    <p className="text-xs text-gray-500">
                      Solved {entry.totalAccepted} problem{entry.totalAccepted !== 1 ? 's' : ''} ({entry.totalSubmissions} submissions)
                    </p>
                  </div>
                </div>
                <div className="text-right">
                  <p className="font-semibold text-gray-900">{entry.totalScore}</p>
                  <p className="text-xs text-gray-500">points</p>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
