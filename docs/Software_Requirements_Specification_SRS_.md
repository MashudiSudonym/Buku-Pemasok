---

### **Software Requirements Specification (SRS): Buku Pemasok v1.0**

*   **Versi Dokumen:** 1.0
*   **Tanggal:** 28 Juli 2025
*   **Penyusun:** Muhamad Mashudi Ardi Winata
*   **Referensi:** PRD Buku Pemasok v1.0

---

### **1. Pendahuluan**

#### **1.1. Tujuan**
Dokumen ini mendefinisikan secara detail persyaratan fungsional (Functional Requirements - FR) dan non-fungsional (Non-Functional Requirements - NFR) untuk pengembangan aplikasi mobile **"Buku Pemasok" versi 1.0 (MVP)**. Dokumen ini ditujukan sebagai panduan teknis bagi tim pengembang, desainer UI/UX, dan tim penjaminan mutu (QA).

#### **1.2. Ruang Lingkup Produk**
Aplikasi ini adalah sebuah aplikasi mobile untuk platform **Android** yang berfungsi sebagai alat bantu manajemen pemasok untuk UMKM. Ruang lingkup untuk v1.0 (MVP) mencakup:
*   **Dalam Ruang Lingkup:** Pendaftaran pengguna, manajemen data pemasok, manajemen data produk, pencatatan transaksi pembelian, pelacakan utang, dan perbandingan harga dasar.
*   **Di Luar Ruang Lingkup:** Analitik visual, notifikasi cerdas, ekspor data, manajemen inventori, dan versi iOS.

#### **1.3. Definisi, Akronim, dan Singkatan**
*   **UMKM:** Usaha Mikro, Kecil, dan Menengah.
*   **MVP:** Minimum Viable Product.
*   **FR:** Functional Requirement (Persyaratan Fungsional).
*   **NFR:** Non-Functional Requirement (Persyaratan Non-Fungsional).
*   **API:** Application Programming Interface.
*   **UI/UX:** User Interface / User Experience.
*   **OTP:** One-Time Password.
*   **CRUD:** Create, Read, Update, Delete.

### **2. Deskripsi Umum**

#### **2.1. Perspektif Produk**
Buku Pemasok adalah aplikasi mandiri (standalone) untuk Android. Aplikasi akan berinteraksi dengan sebuah backend server melalui REST API untuk sinkronisasi dan penyimpanan data. Aplikasi dirancang dengan pendekatan **offline-first**.

#### **2.2. Kendala (Constraints)**
1.  **Platform:** Aplikasi harus dikembangkan secara native untuk Android.
2.  **Kompatibilitas:** Mendukung Android API level 21 (Lollipop) ke atas.
3.  **Bahasa:** Antarmuka dan semua konten harus dalam Bahasa Indonesia.
4.  **Monetisasi:** Mengikuti model Freemium seperti yang dijelaskan dalam PRD.

### **3. Persyaratan Fungsional (Functional Requirements - FR)**

Setiap fitur diuraikan menjadi persyaratan spesifik yang dapat diuji.

#### **FR-1: Pendaftaran & Autentikasi Pengguna**
*   **FR-1.1:** Sistem harus menyediakan opsi pendaftaran menggunakan Akun Google.
*   **FR-1.2:** Sistem harus menyediakan opsi pendaftaran menggunakan nomor telepon. Proses ini harus melibatkan verifikasi melalui kode OTP yang dikirim via SMS.
*   **FR-1.3:** Sistem harus menyediakan fungsionalitas login untuk pengguna yang sudah terdaftar.
*   **FR-1.4:** Sistem harus menyimpan sesi login pengguna sehingga pengguna tidak perlu login setiap kali membuka aplikasi.
*   **FR-1.5:** Sistem harus menyediakan fungsionalitas logout.

#### **FR-2: Manajemen Pemasok**
*   **FR-2.1:** Pengguna harus dapat **membuat (Create)** entri pemasok baru dengan atribut berikut: Nama Pemasok (wajib), Nama Toko (opsional), No. Telepon (opsional), No. WhatsApp (opsional, divalidasi format angka), Kategori (opsional, teks bebas), Catatan (opsional).
*   **FR-2.2:** Pengguna harus dapat **melihat (Read)** daftar semua pemasok yang telah ditambahkan. Daftar ini harus dapat diurutkan berdasarkan abjad atau tanggal penambahan.
*   **FR-2.3:** Pengguna harus dapat melakukan pencarian pada daftar pemasok berdasarkan Nama Pemasok atau Nama Toko.
*   **FR-2.4:** Pengguna harus dapat **memperbarui (Update)** semua atribut dari entri pemasok yang ada.
*   **FR-2.5:** Pengguna harus dapat **menghapus (Delete)** entri pemasok. Sistem harus meminta konfirmasi sebelum penghapusan.
*   **FR-2.6:** Pada detail pemasok, menekan ikon telepon harus memicu *intent* panggilan ke nomor yang tersimpan.
*   **FR-2.7:** Pada detail pemasok, menekan ikon WhatsApp harus membuka aplikasi WhatsApp dan memulai chat ke nomor yang tersimpan.

#### **FR-3: Manajemen Produk**
*   **FR-3.1:** Pengguna harus dapat **membuat (Create)** entri produk baru dengan atribut: Nama Produk (wajib) dan Satuan (wajib, misal: kg, liter, pcs, ikat, karung).
*   **FR-3.2:** Pengguna harus dapat **melihat (Read)** daftar semua produk yang telah ditambahkan.
*   **FR-3.3:** Pengguna harus dapat **memperbarui (Update)** dan **menghapus (Delete)** entri produk yang ada.

#### **FR-4: Pencatatan Transaksi Pembelian**
*   **FR-4.1:** Pengguna harus dapat membuat catatan transaksi baru.
*   **FR-4.2:** Form transaksi harus memiliki field: Pemasok (dipilih dari daftar pemasok), Tanggal Transaksi (default hari ini, bisa diubah).
*   **FR-4.3:** Dalam satu transaksi, pengguna harus dapat menambahkan satu atau lebih item produk (dipilih dari daftar produk), beserta kuantitas dan harga total untuk item tersebut.
*   **FR-4.4:** Form transaksi harus memiliki opsi status pembayaran: **"Lunas"** atau **"Belum Lunas"**.
*   **FR-4.5:** Setelah disimpan, data transaksi, termasuk harga per unit (dihitung otomatis: harga total / kuantitas), harus tersimpan dalam riwayat harga produk terkait.
*   **FR-4.6:** Pengguna harus dapat melihat riwayat semua transaksi dan memfilternya berdasarkan pemasok atau rentang tanggal.

#### **FR-5: Fitur Inti (Dashboard, Pelacakan Utang, Perbandingan Harga)**
*   **FR-5.1 (Dashboard):** Halaman utama aplikasi harus menampilkan kartu ringkasan (summary card) yang menunjukkan total nominal utang dari semua transaksi berstatus "Belum Lunas".
*   **FR-5.2 (Pelacakan Utang):** Harus ada halaman khusus yang menampilkan daftar semua transaksi "Belum Lunas", dikelompokkan berdasarkan pemasok.
*   **FR-5.3 (Pelunasan Utang):** Pengguna harus dapat mengubah status transaksi dari "Belum Lunas" menjadi "Lunas". Setelah diubah, transaksi tersebut harus hilang dari daftar utang.
*   **FR-5.4 (Perbandingan Harga):** Harus ada fitur di mana pengguna memilih satu produk dari daftarnya.
*   **FR-5.5 (Hasil Perbandingan):** Sistem akan menampilkan daftar harga terakhir untuk produk yang dipilih dari setiap pemasok. Informasi yang ditampilkan: Nama Pemasok, Harga per Satuan, Tanggal Transaksi. Daftar ini harus diurutkan dari harga termurah ke termahal.

### **4. Persyaratan Non-Fungsional (Non-Functional Requirements - NFR)**

#### **NFR-1: Performa**
*   **NFR-1.1 (Waktu Muat):** Waktu untuk aplikasi terbuka dari kondisi dingin (cold start) tidak boleh lebih dari 3 detik pada perangkat Android kelas menengah (RAM 3GB).
*   **NFR-1.2 (Responsivitas UI):** Semua interaksi UI (scrolling, tap, transisi layar) harus berjalan lancar tanpa lag atau jank, menargetkan render 60 frame per detik.
*   **NFR-1.3 (Ukuran Aplikasi):** Ukuran file instalasi (.apk) tidak boleh melebihi 20 MB.
*   **NFR-1.4 (Konsumsi Baterai):** Aplikasi harus dioptimalkan untuk konsumsi daya baterai yang rendah saat aktif maupun di latar belakang.

#### **NFR-2: Keamanan**
*   **NFR-2.1 (Transmisi Data):** Seluruh komunikasi antara aplikasi dan API backend harus dienkripsi menggunakan protokol HTTPS/TLS 1.2 atau lebih tinggi.
*   **NFR-2.2 (Penyimpanan Lokal):** Data sensitif pengguna seperti token autentikasi harus disimpan secara aman menggunakan Android Keystore System, bukan di SharedPreferences biasa.
*   **NFR-2.3 (Manajemen Sesi):** Token sesi harus memiliki masa berlaku dan mekanisme refresh yang aman.
*   **NFR-2.4 (Validasi Input):** Semua input dari pengguna di sisi klien harus divalidasi untuk mencegah data yang tidak valid dikirim ke server.

#### **NFR-3: Keandalan & Ketersediaan (Reliability & Availability)**
*   **NFR-3.1 (Mode Offline):** Pengguna harus dapat melakukan operasi CRUD pada Pemasok, Produk, dan Transaksi meskipun perangkat tidak terhubung ke internet.
*   **NFR-3.2 (Sinkronisasi Otomatis):** Aplikasi harus memiliki mekanisme untuk mendeteksi ketersediaan koneksi internet dan secara otomatis menyinkronkan data lokal yang tertunda ke server.
*   **NFR-3.3 (Penanganan Konflik):** Harus ada strategi dasar untuk menangani konflik data saat sinkronisasi (misal: "last write wins").
*   **NFR-3.4 (Ketersediaan Backend):** API backend harus dirancang untuk ketersediaan tinggi dengan target uptime 99.5%.

#### **NFR-4: Kegunaan (Usability)**
*   **NFR-4.1 (Konsistensi):** Desain antarmuka harus konsisten di seluruh aplikasi, mengikuti panduan Google Material Design.
*   **NFR-4.2 (Aksesibilitas):** Ukuran font harus dapat dibaca dengan jelas, dan target sentuh (tombol, input) harus berukuran minimal 48x48 dp.
*   **NFR-4.3 (Umpan Balik):** Sistem harus memberikan umpan balik visual yang jelas untuk setiap tindakan pengguna (misal: loading indicator, pesan sukses/error).

#### **NFR-5: Pemeliharaan (Maintainability)**
*   **NFR-5.1 (Struktur Kode):** Kode harus ditulis dengan bersih, terstruktur (misal: menggunakan arsitektur MVVM atau MVI), dan diberi komentar yang memadai untuk memudahkan pengembangan di masa depan.
*   **NFR-5.2 (Logging):** Aplikasi harus mengimplementasikan sistem logging untuk error dan crash (misal: Firebase Crashlytics) untuk membantu proses debugging.
