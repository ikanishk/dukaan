# 🚀 SUPER SIMPLE SETUP - Dukaan

## ⚡ Clone and Run - Just 2 Steps!

### Prerequisites:
- Docker Desktop installed
- Git installed

---

## 📦 On Your New Laptop (Mac M5):

### Step 1: Clone and Start
```bash
# Clone repository
git clone https://github.com/YOUR_USERNAME/dukaan.git
cd dukaan

# Make sure Docker Desktop is running, then:
docker-compose up -d
```

### Step 2: Open Browser
```bash
# Wait 60 seconds for build, then:
open http://localhost:3000
```

**Login:**
- Username: `admin`
- Password: `admin123`

---

## ✅ That's It!

**No database setup needed!**
- Uses H2 in-memory database (same as your current laptop)
- Data resets when you restart (perfect for testing)
- No environment files needed
- No configuration needed

---

## 🔍 Useful Commands:

```bash
# View logs
docker-compose logs -f

# Stop everything
docker-compose down

# Restart
docker-compose restart

# Fresh start
docker-compose down
docker-compose up -d
```

---

## 📊 What's Running:

| Service | Port | URL |
|---------|------|-----|
| Frontend | 3000 | http://localhost:3000 |
| Backend | 8080 | http://localhost:8080/api |
| Database | - | H2 in-memory (no external access needed) |

---

## 🎯 Features:

- ✅ Inventory Management
- ✅ Sales Tracking
- ✅ Returns Management
- ✅ Customer Database
- ✅ Dashboard & Reports
- ✅ Default users (admin/staff)
- ✅ Default supplier

---

## 🐛 Troubleshooting:

**Port already in use:**
```bash
# Mac:
lsof -i :3000
kill -9 <PID>

# Windows:
netstat -ano | findstr :3000
taskkill /F /PID <PID>
```

**Container won't start:**
```bash
docker-compose logs backend
docker-compose up --build -d
```

**Fresh start:**
```bash
docker-compose down
docker-compose up -d
```

---

## 💡 Notes:

- **Data is temporary** - Resets when containers restart
- **No passwords needed** - Everything has defaults
- **No .env file needed** - All configured automatically
- **Same as your current setup** - Uses H2 database

---

**That's all you need! Just clone and run!** 🎉
