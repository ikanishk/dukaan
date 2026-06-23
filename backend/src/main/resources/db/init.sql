-- Database initialization script
-- This runs automatically when the PostgreSQL container starts

-- Create extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create indexes will be handled by JPA/Hibernate
-- Initial schema will be created by Hibernate DDL

-- Grant permissions
GRANT ALL PRIVILEGES ON DATABASE dukaan TO dukaan;
