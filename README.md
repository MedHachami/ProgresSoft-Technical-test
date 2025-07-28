# FX Data Warehouse 📊

A robust FX deals data warehouse system built for Bloomberg, focusing on efficient deal import and validation. This system handles FX deal persistence with strict validation rules and no-rollback policy.

![Project Status](https://img.shields.io/badge/Status-Production--Ready-green)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Coverage](https://img.shields.io/badge/Coverage-80%25-brightgreen)

## 📑 Table of Contents
- [Overview](#overview)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)

## Overview

FX Data Warehouse is designed to process and store Foreign Exchange deals with robust validation, deduplication, and persistence capabilities. The system ensures data integrity while maintaining a no-rollback policy for imported deals.

## Key Features

- 🔍 Comprehensive deal validation
    - Deal Unique ID verification
    - Currency ISO code validation
    - Timestamp format validation
    - Amount validation
- 🚫 Duplicate detection and prevention
- 💾 Partial save capability (no rollback policy)
- 📝 Detailed logging and audit trail
- 🔄 Robust error handling

## Tech Stack

### Core Technologies
- Java 21
- Spring Boot 3.2.0
- PostgreSQL 16
- Docker & Docker Compose

### Development Tools
- Maven
- JUnit 5
- AssertJ
- Mockito
- SLF4J & Logback

## Getting Started

### Prerequisites
- JDK 21
- Docker & Docker Compose
- Maven 3.8+

### Installation

1. Clone the repository
```bash
git clone https://github.com/MedHachami/ProgresSoft-Technical-test.git
cd ProgresSoft-Technical-test/technicaltest
```

2. Start Application 
```bash
make up
```

### Using Makefile

The project includes a Makefile with the following commands:
```bash
make up      # Start Docker containers
make down    # Stop Docker containers
make test    # Run tests
make clean   # Clean build files
```

## API Documentation

### Deal Import Endpoint

```
POST /api/v1/deals
Content-Type: application/json
```

Request Body:
```json
{
  "id": "DR123456",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2024-01-01T10:00:00Z",
  "amount": 1000000.00
}
```

Response:
```json
{
  "id": "GS123456",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2024-01-01T10:00:00Z",
  "amount": 1000000.00
}
```

## Project Structure
```
technicaltest/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/progressoft/technicaltest/
│   │   │       ├── controller/
|   |   |       |── dto/
|   |   |       |── exception/
|   |   |       |── mapper/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   └── resources/
│   └── test/
├── Dockerfile
└── docker-compose.yml 
└── Makefile
```
