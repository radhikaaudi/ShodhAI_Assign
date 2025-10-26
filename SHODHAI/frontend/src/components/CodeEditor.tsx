'use client';

import { useState, useEffect } from 'react';

interface CodeEditorProps {
  value: string;
  onChange: (value: string) => void;
  language: string;
}

export default function CodeEditor({ value, onChange, language }: CodeEditorProps) {
  const [isClient, setIsClient] = useState(false);

  useEffect(() => {
    setIsClient(true);
  }, []);

  if (!isClient) {
    return (
      <div className="w-full h-96 bg-gray-100 rounded-lg flex items-center justify-center">
        <p className="text-gray-500">Loading editor...</p>
      </div>
    );
  }

  return (
    <div className="w-full">
      <textarea
        value={value}
        onChange={(e) => onChange(e.target.value)}
        className="w-full h-96 p-4 bg-gray-900 text-green-400 border border-gray-700 rounded-lg font-mono text-sm focus:ring-2 focus:ring-blue-500 focus:border-blue-500 resize-none"
        placeholder={`Enter your ${language} code here...`}
        spellCheck={false}
        style={{ 
          fontFamily: 'Monaco, Consolas, "Courier New", monospace',
          lineHeight: '1.5',
        }}
      />
    </div>
  );
}
