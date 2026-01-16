# Spring Batch CSV Import Demo

This project demonstrates the fundamentals of Spring Batch by implementing a complete ETL (Extract, Transform, Load) pipeline for employee data management.

## Overview

The application reads employee records from a CSV file, validates and transforms the data, and persists it into a PostgreSQL database. This demonstrates real-world batch processing  commonly found in enterprise systems and data migration tasks.

## Spring Batch Core Concepts

### Job
A **Job** represents the complete batch process. In this application, the `importEmployeesJob` is responsible for the entire employee data import workflow. A job consists of one or more steps and can be configured with:
- Job parameters (e.g., input file path, execution date)
- Job listeners (for pre/post-execution logic)
- Incrementers (for unique job instance identification)

### Step
A **Step** is an independent, sequential phase of a job. This project uses a single step called `importEmployeeStep` which:
- Reads data from the CSV file (Reader)
- Transforms DTO objects to entities (Processor)
- Writes validated entities to the database (Writer)

Steps can be configured with:
- Chunk size (number of items processed per transaction)
- Fault tolerance (skip/retry policies)
- Transaction management

### JobRepository
The **JobRepository** persists job execution metadata including:
- Job instances and executions
- Step executions
- Execution context (state between restarts)

Spring Batch automatically creates and manages these tables.

### ItemReader
The **ItemReader** extracts data from a source. This project uses `FlatFileItemReader` to:
- Read from `employees.csv`
- Skip the header row
- Map CSV columns to `EmployeeDto` fields
- Handle delimited data parsing

### ItemProcessor
The **ItemProcessor** transforms and validates data. The `employeeProcessor` bean:
- Converts `EmployeeDto` to `Employee` entity
- Applies business logic and transformations
- Can filter items by returning `null`

### ItemWriter
The **ItemWriter** loads data to the target destination. This project uses `JpaItemWriter` to:
- Persist employee entities in chunks
- Manage database transactions
- Handle constraint violations

### Skip Listener
The **SkipListener** provides hooks for handling skipped items during processing and writing, enabling detailed logging of errors without stopping the entire job.

## Configuration

Key configuration properties in `application.properties`:

```properties
# Application
spring.application.name=spring-batch-demo

# Database (using environment variables)
spring.datasource.url=jdbc:postgresql://${POSTGRES_DB_HOST}:${POSTGRES_DB_PORT}/${POSTGRES_DB}
spring.datasource.username=${POSTGRES_USER}
spring.datasource.password=${POSTGRES_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

# Batch Processing
batch.employee.chunk-sisze=50      # Items processed per transaction
batch.employee.skip-limit:100      # Maximum errors before job fails
```

### Setup

1. **Configure Environment Variables**
   
   Copy the `.env.template` file to `.env` and fill in the database configuration:
   ```bash
   cp .env.template .env
   ```
   
   Edit `.env` with your database credentials:
   ```properties
   POSTGRES_DB_HOST=localhost
   POSTGRES_DB_PORT=5432
   POSTGRES_DB=spring-batch-db
   POSTGRES_USER=postgres
   POSTGRES_PASSWORD=your_secure_password
   ```

2. **Start PostgreSQL Database**
   ```bash
   docker-compose -f compose.db.yml up -d
   ```
   
   This will start a PostgreSQL container using the environment variables from your `.env` file.

3. **Set Environment Variables for the Application**
   
   Before running the application, ensure the environment variables are available.
   ```bash
   set POSTGRES_DB_HOST=localhost
   set POSTGRES_DB_PORT=5432
   set POSTGRES_DB=spring-batch-db
   set POSTGRES_USER=postgres
   set POSTGRES_PASSWORD=your_secure_password
   ```

4. **Build the Project**
   ```bash
   mvn clean install
   ```

5. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

The application will automatically:
- Run Flyway migrations to set up the schema
- Execute the employee import job
- Process all records from `employees.csv`
- Log the results and any skipped records

## Data Model

The `Employee` entity includes:
- **Personal Information**: firstName, lastName, birthday, address, phoneNumber
- **Government IDs**: sssNumber, philhealthNumber, tinNumber, pagIbigNumber
- **Employment Details**: status, position, supervisor
- **Work Schedule**: startShift, endShift
- **Compensation**: basicSalary, allowances, hourlyRate, semiMonthlyRate

## Error Handling

The batch job is configured with fault tolerance:
- **Skip on DataIntegrityViolationException**: Duplicate SSS numbers or constraint violations
- **Skip on ValidationException**: Invalid data format or business rule violations
- **Skip Limit**: Up to 100 errors before the job fails
- **Skip Logging**: All skipped records are logged with detailed error messages

## Monitoring

Spring Batch provides built-in monitoring through the `BATCH_*` tables:
- `BATCH_JOB_INSTANCE` - Unique job runs
- `BATCH_JOB_EXECUTION` - Job execution details and status
- `BATCH_STEP_EXECUTION` - Step-level metrics (read/write/skip counts)
- `BATCH_JOB_EXECUTION_CONTEXT` - Job state for restartability

