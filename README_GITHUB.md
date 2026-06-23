# 🏪 Dukaan - Second-Hand Mobile Retail & Wholesale Management System

A complete business management platform for second-hand mobile retail and wholesale operations, built with Spring Boot, Next.js, and PostgreSQL.

![Java](https://img.shields.io/badge/Java-11-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-green)
![Next.js](https://img.shields.io/badge/Next.js-15-black)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
- [Docker Deployment](#docker-deployment)
- [Development Setup](#development-setup)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## ✨ Features

### 📦 Inventory Management
- Add, edit, and track devices with IMEI and SKU
- Automatic SKU generation (S-001, S-002, etc.)
- Barcode generation for labels
- Device status tracking (In Stock, Sold, Under Repair, Returned)
- Search and filter capabilities

### 💰 Sales Management
- Create and track sales transactions
- Invoice generation
- Payment mode tracking
- Profit calculation
- Sales history and analytics

### 🔄 Returns Management
- Customer returns processing
- Supplier returns tracking
- Refund management
- Return status workflow

### 👥 Customer Management
- Customer/Party database
- Outstanding balance tracking
- Contact information management
- Transaction history

### 📊 Dashboard & Reports
- Real-time business metrics
- Sales and profit analytics
- Inventory statistics
- Financial overview
- Export to PDF/CSV

### 🔐 Security
- JWT-based authentication
- Role-based access control (Admin/Staff)
- Audit logging
- Secure password encryption

---

## 🛠️ Tech Stack

### Backend
- **Framework:** Spring Boot 2.7.18
- **Language:** Java 11
- **Database:** PostgreSQL 15 / H2 (for testing)
- **Security:** Spring Security + JWT
- **Build Tool:** Maven
- **ORM:** Hibernate/JPA

### Frontend
- **Framework:** Next.js 15
- **Language:** TypeScript
- **Styling:** Tailwind CSS + Shadcn UI
- **State Management:** Zustand
- **Data Fetching:** TanStack Query (React Query)
- **Forms:** React Hook Form + Zod

### DevOps
- **Containerization:** Docker + Docker Compose
- **CI/CD:** GitLab CI/CD
- **Database:** PostgreSQL with Docker volumes

---

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/dukaan.git
cd dukaan
```

### 2. Start with Docker

```bash
# Start all services
docker-compose -f docker-compose.prod.yml up -d

# View logs
docker-compose -f docker-compose.prod.yml logs -f
```

### 3. Access the Application

- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080/api
- **H2 Console:** http://localhost:8080/h2-console (dev mode)

### 4. Default Credentials

- **Admin:** `admin` / `admin123`
- **Staff:** `staff` / `staff123`

---

## 🐳 Docker Deployment

### Production Deployment

```bash
# Build and start
docker-compose -f docker-compose.prod.yml up --build -d

# Stop
docker-compose -f docker-compose.prod.yml down

# View logs
docker-compose -f docker-compose.prod.yml logs -f backend
docker-compose -f docker-compose.prod.yml logs -f frontend
```

### Environment Variables

Create a `.env` file:

```env
# Database
POSTGRES_PASSWORD=your_secure_password

# JWT
JWT_SECRET=your_super_secret_jwt_key_minimum_256_bits

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:3000,https://yourdomain.com
```

See [DOCKER_SETUP.md](./DOCKER_SETUP.md) for detailed instructions.

---

## 💻 Development Setup

### Backend Setup

```bash
cd backend

# Build
mvn clean package -DskipTests

# Run with H2 (no Docker needed)
java -jar target/dukaan-backend-1.0.0.jar --spring.profiles.active=h2 --server.port=8081

# Or run with PostgreSQL
java -jar target/dukaan-backend-1.0.0.jar --spring.profiles.active=prod
```

### Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Run development server
npm run dev

# Build for production
npm run build

# Start production server
npm start
```

### Database Setup

**Option 1: H2 (Development)**
- No setup required
- In-memory database
- Access console: http://localhost:8081/h2-console

**Option 2: PostgreSQL (Production)**
```bash
# Using Docker
docker run -d \
  --name dukaan-postgres \
  -e POSTGRES_DB=dukaan \
  -e POSTGRES_USER=dukaan \
  -e POSTGRES_PASSWORD=dukaan123 \
  -p 5432:5432 \
  postgres:15-alpine
```

---

## 📚 API Documentation

### Authentication

```bash
# Login
POST /api/auth/login
{
  "username": "admin",
  "password": "admin123"
}

# Response
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "user": {
    "id": 1,
    "username": "admin",
    "fullName": "Admin User",
    "role": "ADMIN"
  }
}
```

### Devices

```bash
# Create Device
POST /api/devices
Authorization: Bearer {token}
{
  "imei": "123456789012345",
  "brand": "Apple",
  "model": "iPhone 13",
  "color": "Blue",
  "storage": "128GB",
  "ram": "4GB",
  "purchasePrice": 45000,
  "supplierId": 1
}

# Get All Devices
GET /api/devices
Authorization: Bearer {token}

# Search by SKU
GET /api/devices/sku/{sku}
Authorization: Bearer {token}
```

### Dashboard

```bash
# Get Dashboard Metrics
GET /api/dashboard
Authorization: Bearer {token}

# Response
{
  "totalDevices": 10,
  "devicesInStock": 7,
  "devicesSold": 3,
  "totalSalesToday": 2,
  "revenueToday": 90000,
  "profitToday": 10000,
  ...
}
```

---

## 📁 Project Structure

```
dukaan/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/dukaan/
│   │   │   │   ├── config/          # Configuration classes
│   │   │   │   ├── controller/      # REST controllers
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── entity/          # JPA entities
│   │   │   │   ├── repository/      # Data repositories
│   │   │   │   ├── service/         # Business logic
│   │   │   │   ├── security/        # Security components
│   │   │   │   └── exception/       # Exception handling
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── application-h2.yml
│   │   │       └── application-prod.yml
│   ├── Dockerfile
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── app/                     # Next.js app directory
│   │   │   ├── dashboard/           # Dashboard pages
│   │   │   ├── login/               # Login page
│   │   │   └── layout.tsx
│   │   ├── components/              # React components
│   │   └── lib/                     # Utilities
│   │       ├── api/                 # API client
│   │       ├── store/               # State management
│   │       └── utils.ts
│   ├── Dockerfile
│   ├── package.json
│   └── next.config.js
├── docker-compose.yml               # Development compose
├── docker-compose.prod.yml          # Production compose
├── .gitlab-ci.yml                   # GitLab CI/CD pipeline
├── .gitignore
├── README.md
└── DOCKER_SETUP.md
```

---

## 🔄 GitLab CI/CD

The project includes a complete CI/CD pipeline:

1. **Build** - Compiles backend and frontend
2. **Test** - Runs unit tests
3. **Docker Build** - Creates Docker images
4. **Deploy Staging** - Deploys to staging environment
5. **Deploy Production** - Deploys to production

See [DOCKER_SETUP.md](./DOCKER_SETUP.md) for CI/CD configuration.

---

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm run test
npm run lint
```

---

## 📝 Environment Profiles

### Development (H2)
```bash
--spring.profiles.active=h2
```

### Production (PostgreSQL)
```bash
--spring.profiles.active=prod
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🙏 Acknowledgments

- Spring Boot Team
- Next.js Team
- Shadcn UI
- All open-source contributors

---

## 📞 Support

For issues and questions:
- **Issues:** [GitHub Issues](https://github.com/your-username/dukaan/issues)
- **Discussions:** [GitHub Discussions](https://github.com/your-username/dukaan/discussions)

---

## 🗺️ Roadmap

- [ ] Mobile app (React Native)
- [ ] Barcode scanner integration
- [ ] Thermal printer support
- [ ] Advanced analytics
- [ ] Multi-store support
- [ ] WhatsApp integration
- [ ] SMS notifications

---

**Made with ❤️ for second-hand mobile retailers**
