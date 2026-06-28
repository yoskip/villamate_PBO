-- ============================================================
-- VillaMate Database Schema (v2 - improved)
-- ============================================================

CREATE DATABASE IF NOT EXISTS villamate;
USE villamate;

-- ----------------------------------------
-- Tabel user (admin)
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS user (
    id_user INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(100) NOT NULL,
    role ENUM('admin', 'petugas') DEFAULT 'admin',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Password default: admin123 (using SHA2, auto-migrated to PBKDF2 on first login)
INSERT INTO user (username, password, nama, role) VALUES
('admin', SHA2('admin123', 256), 'Administrator', 'admin'),
('petugas', SHA2('petugas123', 256), 'Petugas Villa', 'petugas');

-- ----------------------------------------
-- Tabel villa
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS villa (
    id_villa INT PRIMARY KEY AUTO_INCREMENT,
    nama_villa VARCHAR(100) NOT NULL,
    kapasitas INT NOT NULL,
    harga_per_hari DECIMAL(15, 2) NOT NULL,
    status ENUM('Tersedia', 'Tidak Tersedia') DEFAULT 'Tersedia',
    deskripsi TEXT,
    gambar VARCHAR(255) DEFAULT NULL
);

INSERT INTO villa (nama_villa, kapasitas, harga_per_hari, status, deskripsi) VALUES
('Villa Bambu', 4, 500000, 'Tersedia', 'Villa nyaman dengan pemandangan sawah, cocok untuk keluarga kecil.'),
('Villa Cemara', 6, 850000, 'Tersedia', 'Villa luas dengan kolam renang pribadi dan taman.'),
('Villa Anggrek', 2, 350000, 'Tersedia', 'Villa romantis untuk pasangan, dilengkapi gazebo.'),
('Villa Mawar', 8, 1500000, 'Tersedia', 'Villa mewah dengan 3 kamar tidur, kolam renang, dan ruang keluarga.'),
('Villa Melati', 4, 600000, 'Tersedia', 'Villa asri di pegunungan, udara sejuk dan pemandangan indah.'),
('Villa Kenanga', 6, 750000, 'Tersedia', 'Villa dengan akses langsung ke pantai, cocok untuk liburan.'),
('Villa Dahlia', 10, 2000000, 'Tersedia', 'Villa premium dengan 4 kamar tidur, kolam renang, dan ruang meeting.'),
('Villa Soka', 3, 450000, 'Tersedia', 'Villa minimalis dengan halaman luas dan area barbekyu.'),
('Villa Flamboyan', 12, 2500000, 'Tersedia', 'Villa terbesar dengan 5 kamar tidur, kolam renang, dan lapangan.'),
('Villa Teratai', 4, 550000, 'Tersedia', 'Villa tepi danau dengan pemandangan eksotis dan perahu dayung.');

-- ----------------------------------------
-- Tabel penyewa
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS penyewa (
    id_penyewa INT PRIMARY KEY AUTO_INCREMENT,
    nama VARCHAR(100) NOT NULL,
    no_ktp VARCHAR(20) NOT NULL UNIQUE,
    no_hp VARCHAR(20) NOT NULL,
    alamat TEXT
);

-- ----------------------------------------
-- Tabel transaksi
-- ----------------------------------------
CREATE TABLE IF NOT EXISTS transaksi (
    id_transaksi INT PRIMARY KEY AUTO_INCREMENT,
    id_villa INT NOT NULL,
    id_penyewa INT NOT NULL,
    tgl_checkin DATE NOT NULL,
    tgl_checkout DATE NOT NULL,
    total_hari INT NOT NULL,
    total_biaya DECIMAL(15, 2) NOT NULL,
    status ENUM('Aktif', 'Selesai', 'Dibatalkan') DEFAULT 'Aktif',
    tanggal_transaksi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_villa) REFERENCES villa(id_villa) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_penyewa) REFERENCES penyewa(id_penyewa) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_transaksi_id_villa (id_villa),
    INDEX idx_transaksi_id_penyewa (id_penyewa),
    INDEX idx_transaksi_status (status),
    INDEX idx_transaksi_tgl_checkout (tgl_checkout)
);
