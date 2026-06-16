-- =========================================================
-- DATABASE : db_tripinaja
-- MYSQL SCHEMA
-- =========================================================

SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS db_tripinaja;
CREATE DATABASE db_tripinaja
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE db_tripinaja;

-- =========================================================
-- TABLE user
-- Base table untuk login / register
-- =========================================================
CREATE TABLE user (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY ux_user_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE admin
-- Inheritance dari user (1 user = 1 admin)
-- =========================================================
CREATE TABLE admin (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id INT UNSIGNED NOT NULL,
  admin_level INT UNSIGNED NOT NULL DEFAULT 1,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY ux_admin_user_id (user_id),
  CONSTRAINT fk_admin_user
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE traveler
-- Inheritance dari user (1 user = 1 traveler)
-- =========================================================
CREATE TABLE traveler (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id INT UNSIGNED NOT NULL,
  preference VARCHAR(255) DEFAULT NULL,
  budget DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY ux_traveler_user_id (user_id),
  CONSTRAINT fk_traveler_user
    FOREIGN KEY (user_id)
    REFERENCES user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE provinsi
-- =========================================================
CREATE TABLE provinsi (
  provinsi_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  nama VARCHAR(100) NOT NULL,
  deskripsi TEXT DEFAULT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (provinsi_id),
  UNIQUE KEY ux_provinsi_nama (nama)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE destinasi
-- Relasi banyak destinasi ke satu provinsi
-- =========================================================
CREATE TABLE destinasi (
  destinasi_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  provinsi_id INT UNSIGNED NOT NULL,
  nama VARCHAR(100) NOT NULL,
  lokasi VARCHAR(255) DEFAULT NULL,
  deskripsi TEXT DEFAULT NULL,
  harga DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  rating DECIMAL(3,2) NOT NULL DEFAULT 0.00,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (destinasi_id),
  KEY idx_destinasi_provinsi_id (provinsi_id),
  CONSTRAINT fk_destinasi_provinsi
    FOREIGN KEY (provinsi_id)
    REFERENCES provinsi (provinsi_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE accommodation
-- Relasi banyak accommodation ke satu provinsi
-- =========================================================
CREATE TABLE accommodation (
  accommodation_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  provinsi_id INT UNSIGNED NOT NULL,
  nama VARCHAR(100) NOT NULL,
  lokasi VARCHAR(255) DEFAULT NULL,
  harga_per_malam DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  rating DECIMAL(3,2) NOT NULL DEFAULT 0.00,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (accommodation_id),
  KEY idx_accommodation_provinsi_id (provinsi_id),
  CONSTRAINT fk_accommodation_provinsi
    FOREIGN KEY (provinsi_id)
    REFERENCES provinsi (provinsi_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE transport
-- Relasi banyak transport ke satu provinsi
-- =========================================================
CREATE TABLE transport (
  transport_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  provinsi_id INT UNSIGNED NOT NULL,
  jenis VARCHAR(100) NOT NULL,
  provider VARCHAR(100) NOT NULL,
  harga DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (transport_id),
  KEY idx_transport_provinsi_id (provinsi_id),
  CONSTRAINT fk_transport_provinsi
    FOREIGN KEY (provinsi_id)
    REFERENCES provinsi (provinsi_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE itinerary_total
-- Satu traveler bisa punya banyak itinerary
-- =========================================================
CREATE TABLE itinerary_total (
  itinerary_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  traveler_id INT UNSIGNED NOT NULL,
  title VARCHAR(100) NOT NULL,
  total_hari INT UNSIGNED NOT NULL DEFAULT 1,
  total_biaya DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  status ENUM('DRAFT','ACTIVE','COMPLETED','CANCELLED') NOT NULL DEFAULT 'DRAFT',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (itinerary_id),
  KEY idx_itinerary_traveler_id (traveler_id),
  CONSTRAINT fk_itinerary_traveler
    FOREIGN KEY (traveler_id)
    REFERENCES traveler (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE itinerary_day
-- Satu itinerary memiliki banyak hari
-- =========================================================
CREATE TABLE itinerary_day (
  day_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  itinerary_id INT UNSIGNED NOT NULL,
  hari_ke INT UNSIGNED NOT NULL,
  catatan TEXT DEFAULT NULL,
  biaya_hari DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (day_id),
  UNIQUE KEY ux_itinerary_day_unique (itinerary_id, hari_ke),
  CONSTRAINT fk_itinerary_day
    FOREIGN KEY (itinerary_id)
    REFERENCES itinerary_total (itinerary_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE itinerary_day_destinasi
-- Junction table itinerary_day <-> destinasi
-- =========================================================
CREATE TABLE itinerary_day_destinasi (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  day_id INT UNSIGNED NOT NULL,
  destinasi_id INT UNSIGNED NOT NULL,
  urutan INT UNSIGNED NOT NULL DEFAULT 1,
  durasi_menit INT UNSIGNED DEFAULT NULL,
  biaya DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  catatan TEXT DEFAULT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY ux_day_destinasi (day_id, destinasi_id),
  UNIQUE KEY ux_day_destinasi_urutan (day_id, urutan),
  CONSTRAINT fk_itineraryday_destinasi_day
    FOREIGN KEY (day_id)
    REFERENCES itinerary_day (day_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_itineraryday_destinasi_destinasi
    FOREIGN KEY (destinasi_id)
    REFERENCES destinasi (destinasi_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE itinerary_day_accommodation
-- Junction table itinerary_day <-> accommodation
-- =========================================================
CREATE TABLE itinerary_day_accommodation (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  day_id INT UNSIGNED NOT NULL,
  accommodation_id INT UNSIGNED NOT NULL,
  checkin_time DATETIME DEFAULT NULL,
  checkout_time DATETIME DEFAULT NULL,
  malam INT UNSIGNED NOT NULL DEFAULT 1,
  biaya DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  catatan TEXT DEFAULT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY ux_day_accommodation (day_id, accommodation_id),
  CONSTRAINT fk_itineraryday_accommodation_day
    FOREIGN KEY (day_id)
    REFERENCES itinerary_day (day_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_itineraryday_accommodation_accommodation
    FOREIGN KEY (accommodation_id)
    REFERENCES accommodation (accommodation_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- TABLE itinerary_day_transport
-- Junction table itinerary_day <-> transport
-- =========================================================
CREATE TABLE itinerary_day_transport (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  day_id INT UNSIGNED NOT NULL,
  transport_id INT UNSIGNED NOT NULL,
  urutan INT UNSIGNED NOT NULL DEFAULT 1,
  waktu_berangkat DATETIME DEFAULT NULL,
  waktu_tiba DATETIME DEFAULT NULL,
  biaya DECIMAL(14,2) NOT NULL DEFAULT 0.00,
  catatan TEXT DEFAULT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY ux_day_transport (day_id, transport_id),
  UNIQUE KEY ux_day_transport_urutan (day_id, urutan),
  CONSTRAINT fk_itineraryday_transport_day
    FOREIGN KEY (day_id)
    REFERENCES itinerary_day (day_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_itineraryday_transport_transport
    FOREIGN KEY (transport_id)
    REFERENCES transport (transport_id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================================
-- SAMPLE DATA
-- =========================================================

-- USER
INSERT INTO user (name, email, password) VALUES
('Admin Utama', 'admin@gmail.com', 'admin123'),
('Akbar Rizqullah', 'akbar@gmail.com', 'akbar123');

-- ADMIN
INSERT INTO admin (user_id, admin_level) VALUES
(1, 1);

-- TRAVELER
INSERT INTO traveler (user_id, preference, budget) VALUES
(2, 'Pantai dan wisata alam', 5000000.00);

-- PROVINSI
INSERT INTO provinsi (nama, deskripsi) VALUES
('Bali', 'Provinsi wisata populer di Indonesia'),
('Jawa Barat', 'Provinsi di Pulau Jawa');

-- DESTINASI
INSERT INTO destinasi (provinsi_id, nama, lokasi, deskripsi, harga, rating) VALUES
(1, 'Pantai Kuta', 'Bali', 'Pantai terkenal di Bali', 50000.00, 4.80),
(1, 'Tanah Lot', 'Bali', 'Destinasi wisata religi dan sunset', 60000.00, 4.90),
(2, 'Kawah Putih', 'Bandung', 'Wisata alam kawah', 30000.00, 4.70);

-- ACCOMMODATION
INSERT INTO accommodation (provinsi_id, nama, lokasi, harga_per_malam, rating) VALUES
(1, 'Hotel Kuta Indah', 'Bali', 750000.00, 4.50),
(2, 'Villa Bandung Asri', 'Bandung', 500000.00, 4.40);

-- TRANSPORT
INSERT INTO transport (provinsi_id, jenis, provider, harga) VALUES
(1, 'Pesawat', 'Garuda Indonesia', 1500000.00),
(1, 'Mobil Sewa', 'Bali Rent Car', 300000.00),
(2, 'Kereta', 'KAI', 250000.00);

-- ITINERARY TOTAL
INSERT INTO itinerary_total (traveler_id, title, total_hari, total_biaya, status) VALUES
(1, 'Liburan ke Bali', 2, 0.00, 'DRAFT');

-- ITINERARY DAY
INSERT INTO itinerary_day (itinerary_id, hari_ke, catatan, biaya_hari) VALUES
(1, 1, 'Hari pertama: tiba di Bali, check-in hotel, jalan ke Pantai Kuta', 0.00),
(1, 2, 'Hari kedua: wisata Tanah Lot dan transport lokal', 0.00);

-- RELASI DAY - DESTINASI
INSERT INTO itinerary_day_destinasi (day_id, destinasi_id, urutan, durasi_menit, biaya, catatan) VALUES
(1, 1, 1, 180, 50000.00, 'Sunset di Pantai Kuta'),
(2, 2, 1, 120, 60000.00, 'Wisata sore');

-- RELASI DAY - ACCOMMODATION
INSERT INTO itinerary_day_accommodation (day_id, accommodation_id, checkin_time, checkout_time, malam, biaya, catatan) VALUES
(1, 1, '2026-07-01 14:00:00', '2026-07-02 12:00:00', 1, 750000.00, 'Ocean view');

-- RELASI DAY - TRANSPORT
INSERT INTO itinerary_day_transport (day_id, transport_id, urutan, waktu_berangkat, waktu_tiba, biaya, catatan) VALUES
(1, 1, 1, '2026-07-01 07:00:00', '2026-07-01 09:00:00', 1500000.00, 'Penerbangan ke Bali'),
(2, 2, 1, '2026-07-02 08:00:00', '2026-07-02 17:00:00', 300000.00, 'Transport lokal');

-- =========================================================
-- UPDATE BIAYA HARI & TOTAL ITINERARY
-- =========================================================
UPDATE itinerary_day d
SET d.biaya_hari = (
  IFNULL((SELECT SUM(biaya) FROM itinerary_day_destinasi x WHERE x.day_id = d.day_id), 0)
  + IFNULL((SELECT SUM(biaya) FROM itinerary_day_accommodation y WHERE y.day_id = d.day_id), 0)
  + IFNULL((SELECT SUM(biaya) FROM itinerary_day_transport z WHERE z.day_id = d.day_id), 0)
);

UPDATE itinerary_total t
SET t.total_biaya = (
  SELECT IFNULL(SUM(d.biaya_hari), 0)
  FROM itinerary_day d
  WHERE d.itinerary_id = t.itinerary_id
)
WHERE t.title = 'Liburan ke Bali';

SET FOREIGN_KEY_CHECKS = 1;

-- =========================================================
-- END OF DATABASE
-- =========================================================
