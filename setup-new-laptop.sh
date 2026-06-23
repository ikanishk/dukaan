#!/bin/bash

# Dukaan - Quick Setup Script for New Laptop
# This script sets up the entire application using Docker

echo "🏪 Dukaan - Setup Script"
echo "========================"

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    echo "Visit: https://www.docker.com/products/docker-desktop"
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

echo "✅ Docker and Docker Compose are installed"

# Clone repository (if not already cloned)
if [ ! -d ".git" ]; then
    echo "📥 Cloning repository..."
    read -p "Enter GitLab repository URL: " REPO_URL
    git clone $REPO_URL .
fi

# Create .env file if it doesn't exist
if [ ! -f ".env" ]; then
    echo "📝 Creating .env file..."
    cp .env.example .env
    echo "⚠️  Please edit .env file with your configuration"
    read -p "Press Enter to continue after editing .env..."
fi

# Pull latest images (if using registry)
echo "📦 Pulling Docker images..."
docker-compose -f docker-compose.prod.yml pull || true

# Build and start services
echo "🚀 Building and starting services..."
docker-compose -f docker-compose.prod.yml up --build -d

# Wait for services to be ready
echo "⏳ Waiting for services to start..."
sleep 10

# Check if services are running
echo "🔍 Checking service status..."
docker-compose -f docker-compose.prod.yml ps

# Display access information
echo ""
echo "✅ Setup Complete!"
echo "=================="
echo ""
echo "🌐 Access your application:"
echo "   Frontend: http://localhost:3000"
echo "   Backend:  http://localhost:8080/api"
echo ""
echo "🔐 Default Credentials:"
echo "   Admin: admin / admin123"
echo "   Staff: staff / staff123"
echo ""
echo "📊 View logs:"
echo "   docker-compose -f docker-compose.prod.yml logs -f"
echo ""
echo "🛑 Stop services:"
echo "   docker-compose -f docker-compose.prod.yml down"
echo ""
echo "Happy selling! 🎉"
