-- ============================================
-- UNIVERSITY MANAGEMENT SYSTEM — SCHEMA
-- ============================================

CREATE TABLE IF NOT EXISTS filieres (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    code        VARCHAR(20)   NOT NULL,
    description VARCHAR(500),
    CONSTRAINT uk_filiere_code UNIQUE (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS students (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name        VARCHAR(50)   NOT NULL,
    last_name         VARCHAR(50)   NOT NULL,
    cin               VARCHAR(20)   NOT NULL,
    cne               VARCHAR(20)   NOT NULL,
    date_of_birth     DATE          NOT NULL,
    email             VARCHAR(100)  NOT NULL,
    phone             VARCHAR(20),
    address           VARCHAR(255),
    registration_date DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status            VARCHAR(15)   NOT NULL DEFAULT 'ACTIVE',
    level             VARCHAR(5)    NOT NULL,
    filiere_id        BIGINT        NOT NULL,
    CONSTRAINT uk_student_cin   UNIQUE (cin),
    CONSTRAINT uk_student_cne   UNIQUE (cne),
    CONSTRAINT uk_student_email UNIQUE (email),
    CONSTRAINT fk_student_filiere FOREIGN KEY (filiere_id)
        REFERENCES filieres(id) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS professors (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(50)   NOT NULL,
    last_name   VARCHAR(50)   NOT NULL,
    email       VARCHAR(100)  NOT NULL,
    filiere_id  BIGINT        NOT NULL,
    CONSTRAINT uk_professor_email UNIQUE (email),
    CONSTRAINT fk_professor_filiere FOREIGN KEY (filiere_id)
        REFERENCES filieres(id) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS courses (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(150)  NOT NULL,
    code          VARCHAR(20)   NOT NULL,
    credits       INT           NOT NULL,
    professor_id  BIGINT,
    filiere_id    BIGINT        NOT NULL,
    CONSTRAINT uk_course_code UNIQUE (code),
    CONSTRAINT fk_course_professor FOREIGN KEY (professor_id)
        REFERENCES professors(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_course_filiere FOREIGN KEY (filiere_id)
        REFERENCES filieres(id) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS users (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)   NOT NULL,
    password  VARCHAR(255)  NOT NULL,
    full_name VARCHAR(100)  NOT NULL,
    role      VARCHAR(20)   NOT NULL,
    enabled   BOOLEAN       NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_user_username UNIQUE (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes for common queries
CREATE INDEX idx_student_filiere ON students(filiere_id);
CREATE INDEX idx_student_level   ON students(level);
CREATE INDEX idx_student_status  ON students(status);
CREATE INDEX idx_professor_filiere ON professors(filiere_id);
CREATE INDEX idx_course_filiere    ON courses(filiere_id);
