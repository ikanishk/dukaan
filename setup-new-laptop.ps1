# Dukaan - Quick Setup Script for New Laptop (Windows PowerShell)
# This script sets up the entire application using Docker

Write-Host "🏪 Dukaan - Setup Script" -ForegroundColor Cyan
Write-Host "========================" -ForegroundColor Cyan
Write-Host ""

# Check if Docker is installed
try {
    docker --version | Out-Null
    Write-Host "✅ Docker is installed" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker is not installed. Please install Docker Desktop first." -ForegroundColor Red
    Write-Host "Visit: https://www.docker.com/products/docker-desktop" -ForegroundColor Yellow
    exit 1
}

# Check if Docker Compose is installed
try {
    docker-compose --version | Out-Null
    Write-Host "✅ Docker Compose is installed" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker Compose is not installed." -ForegroundColor Red
    exit 1
}

Write-Host ""

# Clone repository (if not already cloned)
if (!(Test-Path ".git")) {
    Write-Host "📥 Cloning repository..." -ForegroundColor Yellow
    $repoUrl = Read-Host "Enter GitLab repository URL"
    git clone $repoUrl .
}

# Create .env file if it doesn't exist
if (!(Test-Path ".env")) {
    Write-Host "📝 Creating .env file..." -ForegroundColor Yellow
    Copy-Item ".env.example" ".env"
    Write-Host "⚠️  Please edit .env file with your configuration" -ForegroundColor Yellow
    Read-Host "Press Enter to continue after editing .env"
}

# Pull latest images (if using registry)
Write-Host "📦 Pulling Docker images..." -ForegroundColor Yellow
docker-compose -f docker-compose.prod.yml pull 2>$null

# Build and start services
Write-Host "🚀 Building and starting services..." -ForegroundColor Yellow
docker-compose -f docker-compose.prod.yml up --build -d

# Wait for services to be ready
Write-Host "⏳ Waiting for services to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

# Check if services are running
Write-Host "🔍 Checking service status..." -ForegroundColor Yellow
docker-compose -f docker-compose.prod.yml ps

# Display access information
Write-Host ""
Write-Host "✅ Setup Complete!" -ForegroundColor Green
Write-Host "==================" -ForegroundColor Green
Write-Host ""
Write-Host "🌐 Access your application:" -ForegroundColor Cyan
Write-Host "   Frontend: http://localhost:3000" -ForegroundColor White
Write-Host "   Backend:  http://localhost:8080/api" -ForegroundColor White
Write-Host ""
Write-Host "🔐 Default Credentials:" -ForegroundColor Cyan
Write-Host "   Admin: admin / admin123" -ForegroundColor White
Write-Host "   Staff: staff / staff123" -ForegroundColor White
Write-Host ""
Write-Host "📊 View logs:" -ForegroundColor Cyan
Write-Host "   docker-compose -f docker-compose.prod.yml logs -f" -ForegroundColor White
Write-Host ""
Write-Host "🛑 Stop services:" -ForegroundColor Cyan
Write-Host "   docker-compose -f docker-compose.prod.yml down" -ForegroundColor White
Write-Host ""
Write-Host "Happy selling! 🎉" -ForegroundColor Green
