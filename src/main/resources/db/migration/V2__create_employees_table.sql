CREATE TABLE employees (
                           id BIGINT PRIMARY KEY,

    -- Personal Information
                           last_name     VARCHAR(100) NOT NULL,
                           first_name    VARCHAR(100) NOT NULL,
                           birthday      DATE,
                           address       TEXT,
                           phone_number  VARCHAR(30),

    -- Government Numbers
                           sss_number        VARCHAR(30) UNIQUE,
                           philhealth_number VARCHAR(30) UNIQUE,
                           tin_number        VARCHAR(30) UNIQUE,
                           pag_ibig_number   VARCHAR(30) UNIQUE,

    -- Employment Details
                           status        VARCHAR(50),
                           position      VARCHAR(100),
                           supervisor    VARCHAR(150),

    -- Work Schedule
                           start_shift   TIME DEFAULT '08:00:00',
                           end_shift     TIME DEFAULT '17:00:00',

    -- Compensation
                           basic_salary        NUMERIC(12,2) DEFAULT 0,
                           meal_allowance      NUMERIC(12,2) DEFAULT 0,
                           phone_allowance     NUMERIC(12,2) DEFAULT 0,
                           clothing_allowance  NUMERIC(12,2) DEFAULT 0,
                           semi_monthly_rate   NUMERIC(12,2) DEFAULT 0,
                           hourly_rate         NUMERIC(12,2) DEFAULT 0
);
