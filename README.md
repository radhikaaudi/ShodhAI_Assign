# Shodh AI Coding Contest Platform

A real-time coding contest platform with live leaderboards and automatic code evaluation using Docker.

## 🎯 What It Does

- Students can join contests and solve coding problems
- Real-time leaderboard showing who solved what
- Automatic code evaluation using Docker containers
- Secure execution environment with resource limits

## 🏗️ Tech Stack

- **Backend**: Spring Boot (Java 17)
- **Frontend**: Next.js (React + TypeScript)
- **Code Execution**: Docker containers for isolation
- **Database**: H2 (dev), MySQL (production)

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 18+
- Docker Desktop

### Local Setup

1. **Build the judge Docker image**
   ```bash
   docker build -t java-judge:latest judge/
   ```

2. **Start backend** (in one terminal)
   ```bash
   cd backend
   mvn spring-boot:run
   ```

3. **Start frontend** (in another terminal)
   ```bash
   cd frontend
   npm run dev
   ```

4. **Access the app**
   - Open http://localhost:3000
   - Use Contest ID: `1`
   - Enter any username

## 🎮 How to Use

1. Enter contest ID (`1`) and your username
2. Select a problem from the sidebar
3. Write your Java code
4. Click "Submit Code" to evaluate
5. Check the leaderboard to see your ranking

## 🌐 Deploy on Render

The app is ready for Render deployment with:
- Backend: Spring Boot JAR
- Frontend: Next.js static export
- Environment variables for database and Docker

## 📁 Project Structure

```
shodh/
├── backend/          # Spring Boot API
│   ├── src/main/java/
│   │   └── com/shodh/codingcontest/
│   └── pom.xml
├── frontend/         # Next.js app
│   ├── src/
│   └── package.json
├── judge/           # Docker execution environment
│   └── Dockerfile
└── docker-compose.yml
```

## 🔒 Security

- Code runs in isolated Docker containers
- CPU and memory limits enforced
- No network access in containers
- Automatic container cleanup

## 📞 Contact

For questions or issues, contact the development team.
