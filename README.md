# PBO Project - Kelompok 1
## Anggota Kelompok
- 103012430043 - Akbar Rizqullah Putra Susanto
- 103012430039 - Raditya Kemal Thooriq
- 103012400148 - Pangeran Clevario Decaesario
- 103012430046 - Muhammad Nafiz Safaraz DA
- 103012400330 - Raihan Wendra Baswara

## Latar Belakang
Merencanakan perjalanan wisata secara manual memakan waktu karena wisatawan
harus mencari informasi dari berbagai sumber terpisah, membandingkan harga,
dan menghitung estimasi waktu antar lokasi sendiri. Hal ini sering berujung
pada pemborosan waktu, biaya membengkak, atau gagal mengunjungi destinasi utama.

Aplikasi ini hadir sebagai solusi travel planner berbasis teknologi yang
memungkinkan pengguna menyusun itinerary secara otomatis dalam satu platform,
dilengkapi rekomendasi rute terbaik dan estimasi biaya yang akurat.

## Deskripsi Project
Project ini adalah aplikasi web berbasis **Spring Boot** dengan dukungan **Spring Web**, **Spring Data JPA**, **Thymeleaf**, dan **MySQL**.

Saat ini project sudah menyediakan:
- struktur dasar aplikasi Spring Boot
- konfigurasi koneksi database MySQL

## Teknologi yang Digunakan

- Java 17
- Spring Boot 4.0.6
- Maven
- Spring Web
- Spring Data JPA
- Spring Validation
- Thymeleaf
- MySQL / MariaDB
- Lombok

## Struktur Project

```bash
src/main/java/com/tubes/pbo/
├── PboApplication.java
├── controller/
├── config/
├── model/
├── repository/
└── service/

src/main/resources/
├── application.properties
├── static/
│   ├── css/
│   ├── images/
│   └── js/
└── templates/
    ├── index.html
    ├── fragments/
    ├── home/
    └── layout/
```

## Cara
1. Pastikan MySQL sudah terinstal dan berjalan di komputer Anda.
2. Buat database baru dengan nama `db_tripinaja` (atau sesuaikan dengan konfigurasi di `application.properties`).
3. Jalankan aplikasi menggunakan IDE atau perintah `mvn spring-boot:run` di terminal.
4. Akses aplikasi melalui browser di `http://localhost:8080/`.

## Database
Pastikan untuk membuat database `db_tripinaja` di MySQL sebelum menjalankan aplikasi. Anda dapat menggunakan perintah SQL berikut:

```sql
CREATE DATABASE db_tripinaja;
```

