# NexFlow: Commerce Operations Platform

NexFlow is a full-stack commerce operations platform designed to manage products, inventory, orders and fulfillment through a centralized web application.

The project was built as a portfolio application focused on real-world business workflows, frontend architecture, REST API development, authentication, database migrations and containerized environments.

## 🚀 Tech Stack

### Frontend

* Angular 22
* TypeScript 6
* Angular Router
* HTTP Interceptors
* JWT Authentication
* Responsive UI

### Backend

* Java 21
* Spring Boot 4.1.1
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* Flyway
* REST APIs
* Spring Boot Actuator

### Database & Infrastructure

* PostgreSQL 17
* Docker
* Docker Compose
* Nginx

---

## ✨ Features

NexFlow currently includes:

* Authentication with JWT
* Role-based access control
* Executive dashboard with KPIs
* Product catalog management
* Inventory availability tracking
* Low-stock alerts
* Stock adjustments
* Stock movement history
* Order creation
* Automatic stock reservation
* Fulfillment workflow
* Order cancellation with stock release
* Audit trail

---

## 🏗️ Architecture

The project follows a separated frontend/backend architecture:

```text
nexflow/
├── backend/
│   └── Spring Boot REST API
│
├── frontend/
│   └── Angular application
│
├── docs/
│
├── .github/
│   └── workflows/
│
├── docker-compose.yml
├── .gitignore
└── README.md
```

The Angular application communicates with the Spring Boot REST API through `/api`.

Spring Boot handles business rules, authentication, persistence and database access.

PostgreSQL is used as the relational database, while Flyway manages database migrations.

---

## 🔐 Authentication

Authentication is based on JWT.

The application supports:

* Login
* Access tokens
* Refresh tokens
* Protected frontend routes
* Angular HTTP authentication interceptor
* Spring Security protected endpoints
* Role-based authorization

### Demo account

```text
Email: demo@nexflow.dev
Password: Demo@123
```

These credentials are intended exclusively for the local demo environment.

---

## 📦 Business Modules

### Dashboard

Provides an overview of operational information and business KPIs.

### Products

Handles the product catalog and product information.

### Inventory

Manages:

* Available stock
* Stock adjustments
* Low-stock alerts
* Movement history

### Orders

Handles the complete order lifecycle, including automatic stock reservation.

### Fulfillment

Tracks orders through the operational fulfillment process.

### Audit

Stores relevant business actions and system events for traceability.

---

## 🔄 Order Workflow

```text
CREATED
   ↓
PAYMENT_APPROVED
   ↓
SEPARATING
   ↓
READY_TO_SHIP
   ↓
SHIPPED
   ↓
DELIVERED
```

Order cancellation releases previously reserved inventory when applicable.

---

## 🐳 Running with Docker

The recommended way to run NexFlow locally is using Docker Compose.

### Requirements

Make sure you have installed:

* Docker
* Docker Compose

### Start the application

From the project root:

```bash
docker compose up --build -d
```

Check the containers:

```bash
docker compose ps
```

Expected services:

```text
nexflow-postgres
nexflow-backend
nexflow-frontend
```

Then open:

| Service         | URL                                   |
| --------------- | ------------------------------------- |
| Frontend        | http://localhost:4200                 |
| Backend API     | http://localhost:8080                 |
| Swagger         | http://localhost:8080/swagger-ui.html |
| Actuator Health | http://localhost:8080/actuator/health |

---

## 🗄️ PostgreSQL

PostgreSQL runs inside Docker.

For connections from database tools installed on Windows:

```text
Host: 127.0.0.1
Port: 5433
Database: nexflow
User: nexflow
Password: nexflow
```

Port `5433` is intentionally exposed on the host to avoid conflicts with local PostgreSQL installations that normally use port `5432`.

These credentials are intended for local development only.

---

## 🗃️ Database Migrations

Database versioning is handled by Flyway.

When the backend starts, migrations are executed before Hibernate validates the database schema.

The initial migration is:

```text
V1__create_schema.sql
```

Applied migrations can be inspected through:

```text
http://localhost:8080/actuator/flyway
```

The project uses:

```text
spring-boot-starter-flyway
flyway-database-postgresql
```

---

## 💻 Running the Backend Separately

You can run only PostgreSQL through Docker:

```bash
docker compose up postgres -d
```

Then start the backend:

```bash
cd backend
mvn spring-boot:run
```

For local Maven development, the backend connects to PostgreSQL through:

```text
127.0.0.1:5433
```

---

## 🅰️ Running Angular Separately

Open the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm start
```

The Angular development server proxies `/api` requests to:

```text
http://localhost:8080
```

---

## 🔁 Resetting the Local Environment

If you need to completely recreate the NexFlow database:

```bash
docker compose down -v --remove-orphans
docker compose up --build -d
```

> `docker compose down -v` deletes the local NexFlow database volume. Use it only when you intentionally want a fresh database.

For normal usage:

### Start

```bash
docker compose up -d
```

### Stop

```bash
docker compose down
```

---

## 🎯 Project Goals

NexFlow was developed to practice and demonstrate concepts commonly found in production applications, including:

* Full-stack application architecture
* Modern Angular development
* REST API design
* Authentication and authorization
* Business rule implementation
* Relational database modeling
* Database migrations
* Inventory and order workflows
* Containerized development environments
* Separation of frontend and backend responsibilities

---

## 👩‍💻 Author

**Pamela Abreu**

Full Stack / Frontend Developer

Angular • TypeScript • Java • Spring Boot • Go • PostgreSQL

GitHub: [github.com/pamelaaabreu](https://github.com/pamelaaabreu)
