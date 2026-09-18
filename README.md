# NexFlow — Commerce Operations Platform

Full-stack commerce operations platform built with Angular, Spring Boot and PostgreSQL.

## Stack

- Angular 22
- TypeScript 6
- Java 21
- Spring Boot 4.1.1
- Spring Security + JWT
- Spring Data JPA / Hibernate
- Flyway
- PostgreSQL 17
- Docker + Docker Compose
- Nginx

The Angular compiler configuration is TypeScript 6 compatible and avoids removed/deprecated compiler options.

## Recommended local setup — Docker Compose

The supported local setup runs **PostgreSQL, backend and frontend in Docker**.
The backend connects to PostgreSQL through Docker's internal network using `postgres:5432`, so it does not depend on a PostgreSQL installation or port 5432 on Windows.

### First start

From the project root:

```powershell
docker compose up --build -d
```

Check the containers:

```powershell
docker compose ps
```

Expected services:

- `nexflow-postgres` — healthy
- `nexflow-backend` — running
- `nexflow-frontend` — running

Open:

- App: http://localhost:4200
- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- Actuator: http://localhost:8080/actuator/health

### Demo account

```text
Email: demo@nexflow.dev
Password: Demo@123
```

### PostgreSQL access from Windows tools

PostgreSQL is intentionally exposed on **host port 5433** to avoid conflicts with local PostgreSQL installations that commonly use 5432.

```text
Host: 127.0.0.1
Port: 5433
Database: nexflow
User: nexflow
Password: nexflow
```

## One-time migration from the old local setup

If you previously started an older NexFlow compose configuration, remove only the old NexFlow containers/volume once so PostgreSQL is initialized from this configuration:

```powershell
docker compose down -v --remove-orphans
docker compose up --build -d
```

`down -v` deletes the local NexFlow database volume. Use it only for this first reset or when you intentionally want a fresh database.

After that, normal starts are simply:

```powershell
docker compose up -d
```

and stops are:

```powershell
docker compose down
```

## Optional: run the backend with Maven

Keep PostgreSQL running through Docker:

```powershell
docker compose up postgres -d
cd backend
mvn spring-boot:run
```

No environment variables are required. `application.yml` points local Maven development to `127.0.0.1:5433`.

## Optional: run the Angular dev server

```powershell
cd frontend
npm install
npm start
```

The Angular development server proxies `/api` to `http://localhost:8080`.

## Business modules

- Authentication and role-based access
- Executive dashboard with KPIs
- Product catalog
- Inventory availability and low-stock alerts
- Stock adjustments and movement history
- Order creation with automatic stock reservation
- Fulfillment workflow
- Cancellation with stock release
- Audit trail

## Order workflow

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

## Author

**Pamela Abreu** — Full Stack / Frontend Developer

Angular • Java • Spring Boot • Go • PostgreSQL

## Database migrations

The backend uses **Flyway managed by Spring Boot**. On startup, `V1__create_schema.sql` is executed before Hibernate validation.

Spring Boot 4 separates Flyway auto-configuration into its own module, so the project uses `spring-boot-starter-flyway` plus `flyway-database-postgresql`. Do not replace the starter with only `flyway-core`, otherwise migrations will not be auto-configured before JPA.

You can inspect applied migrations after startup at `http://localhost:8080/actuator/flyway`.
