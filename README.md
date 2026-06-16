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
- sistem login dan register berbasis role untuk `admin` dan `traveler`
- redirect otomatis ke dashboard yang sesuai setelah login

## Fitur Autentikasi

### Role yang didukung
- **Admin**: akun diambil dari tabel `user` yang memiliki pasangan data di tabel `admin`.
- **Traveler**: akun diambil dari tabel `user` yang memiliki pasangan data di tabel `traveler`.

### Alur Login
1. User memasukkan email dan password pada halaman `/login`.
2. Sistem mencari data pada tabel `user`.
3. Jika password cocok, sistem mengecek role:
   - ada data di tabel `admin` → redirect ke `/admin/dashboard`
   - ada data di tabel `traveler` → redirect ke `/dashboard`
4. Jika kredensial salah, user kembali ke halaman login dengan pesan error.

### Alur Register
1. User mengisi nama, email, dan password pada halaman `/register`.
2. Sistem menyimpan data ke tabel `user`.
3. Sistem otomatis membuat data pasangan di tabel `traveler`.
4. Setelah berhasil, user diarahkan kembali ke halaman login.

### Catatan Penting
- Di `database.sql`, nama tabel autentikasi yang dipakai adalah **`user`** (bukan `users`).
- Akun admin **tidak** dibuat dari form register, tetapi dari data di database pada tabel `admin`.
- Password baru dari register disimpan dalam bentuk hash, tetapi sample data lama tetap bisa dibaca oleh sistem ini agar kompatibel dengan isi `database.sql`.

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
3. Import file `database.sql` agar tabel `user`, `admin`, `traveler`, dan tabel lainnya terbentuk beserta sample data.
4. Jalankan aplikasi menggunakan IDE atau perintah `mvn spring-boot:run` di terminal.
5. Akses aplikasi melalui browser di `http://localhost:8080/`.

## Database
Pastikan untuk membuat database `db_tripinaja` di MySQL sebelum menjalankan aplikasi. Anda dapat menggunakan perintah SQL berikut:

```sql
CREATE DATABASE db_tripinaja;
```

### Contoh akun sample
- **Admin**
  - Email: `admin@gmail.com`
  - Password: `admin123`
- **Traveler**
  - Email: `akbar@gmail.com`
  - Password: `akbar123`

### Endpoint utama autentikasi
- `GET /login` → halaman login
- `GET /register` → halaman register traveler
- `POST /auth/login` → proses login
- `POST /auth/register` → proses register
- `GET /auth/logout` → logout

### Redirect setelah login
- Admin → `/admin/dashboard`
- Traveler → `/dashboard`

### Jalankan aplikasi

```bash
./mvnw spring-boot:run
```

