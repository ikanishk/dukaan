# Dukaan - Second-Hand Mobile Retail & Wholesale Management System

## Overview
Complete business management platform for second-hand mobile retail and wholesale operations. Replaces Google Sheets, Khatabook, and manual tracking systems.

## Features
- ✅ Inventory Management with IMEI tracking
- ✅ Barcode-first workflows
- ✅ Sales & Returns Management
- ✅ Repair Tracking
- ✅ Party/Customer Management
- ✅ Ledger Accounting
- ✅ Expense Management
- ✅ Finance Tracking
- ✅ Comprehensive Reporting
- ✅ Label Printing
- ✅ Role-Based Access Control
- ✅ Audit Logging

## Technology Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Security + JWT
- Hibernate/JPA
- PostgreSQL
- JasperReports
- ZXing (Barcode)

### Frontend
- Next.js 15
- React 18
- TypeScript
- Tailwind CSS
- Shadcn UI
- TanStack Query
- React Hook Form
- Zod

### Infrastructure
- Docker & Docker Compose
- Local PostgreSQL

## Prerequisites
- Docker Desktop
- Java 21 JDK
- Node.js 20+
- Maven 3.9+

## Quick Start

### 1. Clone and Navigate
```bash
cd Dukaan
```

### 2. Start Database
```bash
docker compose up -d
```

### 3. Start Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend runs on: http://localhost:8080

### 4. Start Frontend
```bash
cd frontend
npm install
npm run dev
```

Frontend runs on: http://localhost:3000

## Default Credentials

### Admin User
- Username: `admin`
- Password: `admin123`

### Staff User
- Username: `staff`
- Password: `staff123`

## Project Structure

```
Dukaan/
├── backend/                 # Spring Boot Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/dukaan/
│   │   │   │       ├── config/
│   │   │   │       ├── controller/
│   │   │   │       ├── dto/
│   │   │   │       ├── entity/
│   │   │   │       ├── repository/
│   │   │   │       ├── service/
│   │   │   │       ├── security/
│   │   │   │       ├── exception/
│   │   │   │       └── util/
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── application-dev.yml
│   │   │       └── db/migration/
│   │   └── test/
│   └── pom.xml
├── frontend/                # Next.js Application
│   ├── src/
│   │   ├── app/
│   │   ├── components/
│   │   ├── lib/
│   │   ├── hooks/
│   │   ├── types/
│   │   └── styles/
│   ├── public/
│   ├── package.json
│   └── tsconfig.json
├── docker-compose.yml
├── .env.example
└── README.md
```

## Module Overview

### 1. Authentication
- JWT-based authentication
- Role-based access control (ADMIN/STAFF)
- Session management

### 2. Dashboard
- Real-time metrics
- Sales overview
- Inventory status
- Recent activities

### 3. Inventory Management
- Auto-generated SKU (S-001, S-002...)
- IMEI tracking
- Duplicate prevention
- Status tracking
- Barcode generation

### 4. Sales Management
- Barcode scanning workflow
- Invoice generation
- Automatic profit calculation
- Ledger integration

### 5. Returns Management
- Return processing
- Automatic reversal entries
- Complete audit trail
- Return reason tracking

### 6. Repair Management
- Vendor tracking
- Cost tracking
- Status management
- History maintenance

### 7. Party Management
- Customer records
- Purchase history
- Payment tracking
- Outstanding balances

### 8. Ledger Accounting
- Running balances
- Transaction history
- Statement generation
- PDF/CSV export

### 9. Expense Management
- Category-based tracking
- Monthly summaries
- Impact on net profit

### 10. Finance Management
- Loans tracking
- SIP management
- Committee tracking
- Progress monitoring

### 11. Reporting
- Daily/Weekly/Monthly/Yearly reports
- Profit analysis
- Expense summaries
- Export capabilities

### 12. Label Printing
- Barcode labels
- Thermal printer support
- PDF generation

### 13. Audit Logging
- Complete action tracking
- Soft delete strategy
- Change history

## API Documentation
Swagger UI available at: http://localhost:8080/swagger-ui.html

## Database Schema
ERD and schema documentation available in `/backend/docs/database-schema.md`

## Security Features
- Password hashing (BCrypt)
- JWT token authentication
- Role-based authorization
- Input validation
- SQL injection protection
- XSS protection

## Barcode Support
- IMEI scanning
- SKU scanning
- Keyboard wedge support
- Instant device lookup

## Printing Support
- PDF labels
- Thermal printer compatible
- Batch printing

## Performance
- Optimized for 100,000+ device records
- Indexed database queries
- Efficient pagination
- Caching strategy

## Backup & Recovery
```bash
# Backup database
docker exec dukaan-postgres pg_dump -U dukaan dukaan > backup.sql

# Restore database
docker exec -i dukaan-postgres psql -U dukaan dukaan < backup.sql
```

## Troubleshooting

### Database Connection Issues
```bash
docker compose down
docker compose up -d
```

### Port Already in Use
- Backend: Change port in `application.yml`
- Frontend: Change port in `package.json`
- Database: Change port in `docker-compose.yml`

### Clear All Data
```bash
docker compose down -v
docker compose up -d
```

## Support
For issues and questions, refer to the documentation in `/docs` folder.

## License
Proprietary - All Rights Reserved
"# dukaan" 
