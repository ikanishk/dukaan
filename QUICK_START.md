# 🚀 Quick Start Guide - Dukaan

## ⚡ 3-Step Setup (Any Laptop)

### Step 1: Clone Repository
```bash
git clone https://github.com/YOUR_USERNAME/dukaan.git
cd dukaan
```

### Step 2: Create Environment File
```bash
cp .env.example .env
```

**Edit `.env` and change these values:**
```env
POSTGRES_PASSWORD=YourSecurePassword123!
JWT_SECRET=your-super-secret-random-string-at-least-64-characters-long-12345
```

### Step 3: Start with Docker
```bash
# Make sure Docker Desktop is running first!
docker-compose -f docker-compose.prod.yml up -d
```

**Wait 30-60 seconds, then open:** http://localhost:3000

**Login:**
- Username: `admin`
- Password: `admin123`

---

## 📋 Prerequisites

- **Docker Desktop** (Download: https://www.docker.com/products/docker-desktop)
- **Git**

That's it! No Java, Maven, Node.js, or PostgreSQL needed.

---

## 🔍 Check Status

```bash
# View running containers
docker-compose -f docker-compose.prod.yml ps

# View logs
docker-compose -f docker-compose.prod.yml logs -f

# Stop everything
docker-compose -f docker-compose.prod.yml down
```

---

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Mac/Linux
lsof -i :3000
kill -9 <PID>

# Windows
netstat -ano | findstr :3000
taskkill /F /PID <PID>
```

### Container Won't Start
```bash
# View detailed logs
docker-compose -f docker-compose.prod.yml logs backend

# Rebuild
docker-compose -f docker-compose.prod.yml up --build -d
```

### Fresh Start
```bash
# Remove everything and start fresh
docker-compose -f docker-compose.prod.yml down -v
docker-compose -f docker-compose.prod.yml up -d
```

---

## 📊 What's Running

| Service | Port | URL |
|---------|------|-----|
| Frontend | 3000 | http://localhost:3000 |
| Backend | 8080 | http://localhost:8080/api |
| PostgreSQL | 5432 | Internal (Docker network) |

---

## 🎯 Features

- ✅ Inventory Management (Add/View Devices)
- ✅ Sales Tracking
- ✅ Returns Management
- ✅ Customer Database
- ✅ Dashboard & Reports
- ✅ JWT Authentication
- ✅ Automatic Database Setup

---

## 📚 More Documentation

- **Complete Guide:** `COMPLETE_SETUP_GUIDE.txt`
- **Docker Details:** `DOCKER_SETUP.md`
- **Project Info:** `README_GITHUB.md`
- **GitLab CI/CD:** `.gitlab-ci.yml`

---

**That's it! Your application is ready to use! 🎉**
