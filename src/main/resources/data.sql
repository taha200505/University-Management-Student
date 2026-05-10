-- ============================================
-- SEED DATA — Moroccan University
-- ============================================

-- Repair previously corrupted mojibake values like 'MathÃ©matiques' -> 'Mathématiques'
-- The condition limits conversion to rows that are clearly corrupted.
UPDATE filieres
SET name = CONVERT(BINARY(CONVERT(name USING latin1)) USING utf8mb4)
WHERE name LIKE '%Ã%';

UPDATE filieres
SET description = CONVERT(BINARY(CONVERT(description USING latin1)) USING utf8mb4)
WHERE description LIKE '%Ã%';

UPDATE courses
SET title = CONVERT(BINARY(CONVERT(title USING latin1)) USING utf8mb4)
WHERE title LIKE '%Ã%';

UPDATE students
SET address = CONVERT(BINARY(CONVERT(address USING latin1)) USING utf8mb4)
WHERE address LIKE '%Ã%';

-- Filières
INSERT IGNORE INTO filieres (id, name, code, description) VALUES
(1, 'Sciences Mathématiques et Informatique', 'SMI', 'Filière dédiée aux mathématiques appliquées et informatique'),
(2, 'Sciences Mathématiques et Applications', 'SMA', 'Filière de mathématiques fondamentales et appliquées'),
(3, 'Sciences de la Vie', 'SVT', 'Filière des sciences de la vie et de la terre'),
(4, 'Sciences Physiques', 'SP', 'Filière de physique fondamentale et appliquée'),
(5, 'Droit en Français', 'DF', 'Filière de droit en langue française');

-- Professors
INSERT IGNORE INTO professors (id, first_name, last_name, email, filiere_id) VALUES
(1, 'Ahmed',    'Bennani',   'a.bennani@univ.ma',   1),
(2, 'Fatima',   'El Amrani', 'f.elamrani@univ.ma',  1),
(3, 'Mohamed',  'Tazi',      'm.tazi@univ.ma',      2),
(4, 'Khadija',  'Ouazzani',  'k.ouazzani@univ.ma',  3),
(5, 'Youssef',  'Alaoui',    'y.alaoui@univ.ma',    4);

-- Courses
INSERT IGNORE INTO courses (id, title, code, credits, professor_id, filiere_id) VALUES
(1, 'Programmation Java',         'SMI-JAVA',  6, 1, 1),
(2, 'Bases de Données',           'SMI-BDD',   5, 2, 1),
(3, 'Algèbre Linéaire',           'SMA-ALG',   6, 3, 2),
(4, 'Analyse Réelle',             'SMA-ANA',   6, 3, 2),
(5, 'Biologie Cellulaire',        'SVT-BIO',   5, 4, 3),
(6, 'Mécanique Quantique',        'SP-MQ',     6, 5, 4),
(7, 'Réseaux Informatiques',      'SMI-RES',   4, 1, 1),
(8, 'Intelligence Artificielle',  'SMI-IA',    6, 2, 1);

-- Students (Moroccan-style CINs/CNEs)
INSERT IGNORE INTO students (id, first_name, last_name, cin, cne, date_of_birth, email, phone, address, status, level, filiere_id) VALUES
(1,  'Yassine',  'El Idrissi',  'AB123456', 'R123456789', '2002-03-15', 'yassine.idrissi@etu.univ.ma',  '+212612345678', 'Casablanca',       'ACTIVE',    'S3', 1),
(2,  'Imane',    'Bouazza',     'BK234567', 'K234567890', '2001-07-22', 'imane.bouazza@etu.univ.ma',     '+212623456789', 'Rabat',            'ACTIVE',    'S5', 1),
(3,  'Omar',     'Fassi',       'JB345678', 'L345678901', '2003-01-10', 'omar.fassi@etu.univ.ma',        '+212634567890', 'Fès',              'ACTIVE',    'S1', 2),
(4,  'Sara',     'Tazi',        'BH456789', 'M456789012', '2002-11-28', 'sara.tazi@etu.univ.ma',         '+212645678901', 'Marrakech',        'ACTIVE',    'S3', 3),
(5,  'Amine',    'Berrada',     'JE567890', 'N567890123', '2000-05-03', 'amine.berrada@etu.univ.ma',     '+212656789012', 'Tanger',           'GRADUATED', 'S6', 1),
(6,  'Hajar',    'Chraibi',     'BB678901', 'P678901234', '2002-09-17', 'hajar.chraibi@etu.univ.ma',     '+212667890123', 'Meknès',           'ACTIVE',    'S2', 4),
(7,  'Mehdi',    'Laaroussi',   'BJ789012', 'Q789012345', '2001-12-05', 'mehdi.laaroussi@etu.univ.ma',   '+212678901234', 'Oujda',            'SUSPENDED', 'S4', 2),
(8,  'Nadia',    'Senhaji',     'JK890123', 'R890123456', '2003-04-20', 'nadia.senhaji@etu.univ.ma',     '+212689012345', 'Agadir',           'ACTIVE',    'S1', 5),
(9,  'Karim',    'Ziani',       'BM901234', 'S901234567', '2002-08-12', 'karim.ziani@etu.univ.ma',       '+212690123456', 'Kénitra',          'ACTIVE',    'S3', 1),
(10, 'Salma',    'Rahmani',     'JT012345', 'T012345678', '2001-02-28', 'salma.rahmani@etu.univ.ma',     '+212701234567', 'Tétouan',          'GRADUATED', 'S6', 3);

-- Users (passwords are BCrypt of 'admin123' and 'staff123')
-- admin123 => $2b$12$vAOQpsiz1lTFOlDzvyf9wOzm8nK64yrCsYE6G27lCV4Am3TtgkspW
-- staff123 => $2b$12$OvwSguJViQJIG4eMxdRtMee1th7y9FeUXbIDziWb0L7jjyEW/tWju
INSERT IGNORE INTO users (id, username, password, full_name, role, enabled) VALUES
(1, 'admin', '$2b$12$vAOQpsiz1lTFOlDzvyf9wOzm8nK64yrCsYE6G27lCV4Am3TtgkspW', 'Administrateur', 'ROLE_ADMIN', true),
(2, 'staff', '$2b$12$OvwSguJViQJIG4eMxdRtMee1th7y9FeUXbIDziWb0L7jjyEW/tWju', 'Personnel',      'ROLE_STAFF', true);
