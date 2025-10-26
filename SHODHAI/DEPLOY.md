# Deploy on Render - Step by Step

## 1. Create PostgreSQL Database

1. Go to https://render.com
2. Click "New +" → **"Postgres"**
3. Configure:
   - Name: `coding-contest-db`
   - Plan: Free
   - Region: Choose closest
   - PostgreSQL Version: 15
   - Database: `codingcontest`
   - Click "Create Database"
4. Wait for database to be created
5. **Copy the Internal Database URL** from the dashboard - it looks like: `postgresql://user:password@host:5432/database`

## 2. Deploy Backend Service

1. Go to https://render.com → "New +" → "Web Service"
2. Connect your GitHub repo: `kediasomya/coding_contest_shodh`
3. Configure:
   - Name: `coding-contest-backend`
   - Branch: `main`
   - Root Directory: `backend`
   - Environment: `Java`
   - Build Command: `mvn clean package -DskipTests`
   - Start Command: `java -jar target/coding-contest-platform-0.0.1-SNAPSHOT.jar`

### Environment Variables:
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=postgresql://user:pass@host:5432/db  # Get from Postgres dashboard (Internal Database URL)
```

**OR** set individual variables:
```
SPRING_PROFILES_ACTIVE=prod
DB_HOST=<hostname>
DB_PORT=5432
DB_NAME=codingcontest
DB_USER=<username>
DB_PASSWORD=<password>
```

4. Click "Create Web Service"
5. Wait for deployment (5-10 mins)

## 3. Deploy Frontend Service

1. In Render, click "New +" → "Web Service"
2. Connect same repo: `kediasomya/coding_contest_shodh`
3. Configure:
   - Name: `coding-contest-frontend`
   - Branch: `main`
   - Root Directory: `frontend`
   - Environment: `Node`
   - Build Command: `npm install && npm run build`
   - Start Command: `npm start`

### Environment Variables:
```
NEXT_PUBLIC_API_URL=https://coding-contest-backend.onrender.com/api
```

4. Click "Create Web Service"
5. Wait for deployment

## 4. Test Your Deployment

1. Open your frontend URL
2. Use Contest ID: `1`
3. Test the code submission

## Troubleshooting

- If backend fails: Check database credentials
- If frontend can't connect: Verify `NEXT_PUBLIC_API_URL` is correct
- Check logs in Render dashboard for errors

## Database Connection String

Your database connection should look like:
```
Host: <name>.render.com
Port: 3306
Database: codingcontest
Username: <from dashboard>
Password: <from dashboard>
```

