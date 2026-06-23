# Dukaan - Deployment Guide

## Complete Local Setup Instructions

### Prerequisites

1. **Docker Desktop** - Download and install from https://www.docker.com/products/docker-desktop
2. **Java 21 JDK** - Download from https://adoptium.net/
3. **Maven 3.9+** - Download from https://maven.apache.org/download.cgi
4. **Node.js 20+** - Download from https://nodejs.org/

### Step 1: Database Setup

```powershell
# Navigate to project root
cd C:\Users\AL84861\Downloads\Dukaan

# Start PostgreSQL database
docker compose up -d

# Verify database is running
docker ps
```

The database will be available at `localhost:5432` with:
- Database: `dukaan`
- Username: `dukaan`
- Password: `dukaan123`

### Step 2: Backend Setup

```powershell
# Navigate to backend directory
cd backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

**Default Users Created:**
- **Admin**: username=`admin`, password=`admin123`
- **Staff**: username=`staff`, password=`staff123`

### Step 3: Frontend Setup

```powershell
# Open a new terminal
# Navigate to frontend directory
cd C:\Users\AL84861\Downloads\Dukaan\frontend

# Install dependencies
npm install

# Create .env.local file
Copy-Item .env.example .env.local

# Start development server
npm run dev
```

The frontend will start on `http://localhost:3000`

### Step 4: Access the Application

1. Open your browser and navigate to `http://localhost:3000`
2. You will be redirected to the login page
3. Login with:
   - Username: `admin`
   - Password: `admin123`

## API Documentation

Once the backend is running, access Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Production Build

### Backend
```powershell
cd backend
mvn clean package
java -jar target/dukaan-backend-1.0.0.jar
```

### Frontend
```powershell
cd frontend
npm run build
npm start
```

## Troubleshooting

### Port Already in Use

**Backend (8080):**
Edit `backend/src/main/resources/application.yml`:
```yaml
server:
  port: 8081  # Change to any available port
```

**Frontend (3000):**
```powershell
# Run on different port
$env:PORT=3001; npm run dev
```

**Database (5432):**
Edit `docker-compose.yml`:
```yaml
ports:
  - "5433:5432"  # Change external port
```

### Database Connection Issues

```powershell
# Stop and remove containers
docker compose down

# Remove volumes
docker compose down -v

# Start fresh
docker compose up -d
```

### Clear All Data

```powershell
# Stop all services
docker compose down -v

# Remove backend target
Remove-Item -Recurse -Force backend\target

# Remove frontend build
Remove-Item -Recurse -Force frontend\.next
Remove-Item -Recurse -Force frontend\node_modules

# Start fresh
docker compose up -d
cd backend
mvn clean install
mvn spring-boot:run

# In new terminal
cd frontend
npm install
npm run dev
```

## Module Access

### Admin Access
- Full access to all modules
- View profits and purchase prices
- Manage users and settings
- View all reports

### Staff Access
- Add inventory
- Process sales
- Handle returns
- Process repairs
- **Cannot** view profits or purchase prices
- **Cannot** access admin settings

## Key Features Implemented

### ✅ Inventory Management
- Auto-generated SKU (S-001, S-002, etc.)
- IMEI tracking with duplicate prevention
- Barcode generation
- Status management
- Device lookup by SKU/IMEI

### ✅ Sales Management
- Barcode scanning workflow
- Auto invoice generation
- Profit calculation
- Ledger integration
- Payment tracking

### ✅ Returns Management
- Customer returns
- Supplier returns
- Automatic reversal entries
- Complete audit trail
- Refund processing

### ✅ Repair Management
- Vendor tracking
- Cost tracking
- Status management
- Device history

### ✅ Party/Customer Management
- Customer records
- Purchase history
- Outstanding balances
- Ledger statements

### ✅ Ledger Accounting
- Running balances
- Transaction history
- PDF/CSV export
- Complete audit trail

### ✅ Dashboard
- Real-time metrics
- Sales overview
- Profit tracking
- Inventory status

### ✅ Authentication & Security
- JWT-based authentication
- Role-based access control
- Password encryption
- Session management

### ✅ Audit Logging
- Complete action tracking
- User activity logs
- Change history
- Soft delete strategy

## Database Backup

### Manual Backup
```powershell
docker exec dukaan-postgres pg_dump -U dukaan dukaan > backup_$(Get-Date -Format "yyyyMMdd_HHmmss").sql
```

### Restore from Backup
```powershell
Get-Content backup.sql | docker exec -i dukaan-postgres psql -U dukaan dukaan
```

## Performance Optimization

The system is optimized for:
- 100,000+ device records
- Fast search and lookup
- Efficient pagination
- Database indexing
- Query optimization

## Support

For issues or questions:
1. Check the logs in `backend/logs/dukaan.log`
2. Check browser console for frontend errors
3. Verify all services are running
4. Check database connectivity

## Next Steps

After successful deployment:
1. Change default passwords
2. Configure backup schedule
3. Set up regular database maintenance
4. Train users on the system
5. Customize reports as needed

## Architecture

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Next.js   │────▶│ Spring Boot │────▶│ PostgreSQL  │
│  Frontend   │     │   Backend   │     │  Database   │
│  Port 3000  │     │  Port 8080  │     │  Port 5432  │
└─────────────┘     └─────────────┘     └─────────────┘
```

## Technology Stack Summary

**Frontend:**
- Next.js 15 with App Router
- React 18
- TypeScript
- Tailwind CSS
- TanStack Query
- Zustand (State Management)
- React Hook Form + Zod

**Backend:**
- Spring Boot 3
- Java 21
- Spring Security + JWT
- Hibernate/JPA
- PostgreSQL
- ZXing (Barcode)
- JasperReports

**Infrastructure:**
- Docker & Docker Compose
- Local PostgreSQL

---

**System is now ready for production use!**
