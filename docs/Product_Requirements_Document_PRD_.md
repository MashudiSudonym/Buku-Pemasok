### **Product Requirements Document: Buku Pemasok v1.0**

*   **Tanggal:** 28 Juli 2025
*   **Penulis:** Muhamad Mashudi Ardi Winata
*   **Status:** Draft
*   **Target Rilis MVP:** Q1 2026 atau lebih cepat

### **1. Latar Belakang & Visi Produk**

**Latar Belakang:** Pelaku UMKM di Indonesia, terutama di sektor kuliner, kerajinan, dan fashion, setiap hari berinteraksi dengan banyak pemasok (supplier) bahan baku. Proses pencatatan harga, kontak, dan riwayat pembelian saat ini dilakukan secara manual (buku catatan, chat WhatsApp, ingatan) yang tidak efisien, rawan kesalahan, dan membuat UMKM kehilangan kesempatan untuk menghemat biaya operasional.

**Visi Produk:** Menjadi aplikasi *go-to* yang paling sederhana dan paling efektif bagi jutaan UMKM di Indonesia untuk mengelola hubungan dengan pemasok mereka, membantu mereka menghemat waktu dan uang secara signifikan.

### **2. Masalah yang Diselesaikan (Problem Statement)**

Pemilik UMKM mikro kesulitan untuk:
1.  **Melacak fluktuasi harga:** Mereka tidak memiliki catatan historis harga bahan baku dari berbagai pemasok, sehingga tidak tahu kapan harga naik atau turun.
2.  **Membandingkan harga secara efisien:** Untuk menemukan harga termurah, mereka harus menghubungi beberapa pemasok satu per satu atau membuka riwayat chat yang panjang.
3.  **Mengelola data pemasok:** Kontak pemasok tersebar di berbagai tempat, menyulitkan saat butuh menghubungi dengan cepat.
4.  **Mencatat utang usaha:** Seringkali lupa atau salah catat utang pembelian bahan baku, yang berisiko merusak hubungan dengan pemasok dan mengganggu arus kas.

### **3. Sasaran & Tujuan (Goals & Objectives)**

*   **Tujuan Pengguna:**
    *   Memberikan alat untuk membandingkan harga bahan baku dalam waktu kurang dari 30 detik.
    *   Mengurangi waktu yang dihabiskan untuk mencari kontak dan riwayat harga pemasok hingga 80%.
    *   Menyediakan rekapitulasi utang yang jelas dan mudah diakses untuk menghindari kesalahan pembayaran.
*   **Tujuan Bisnis (untuk MVP):**
    *   Mencapai 5.000 pengguna aktif bulanan (MAU) dalam 6 bulan pertama setelah peluncuran.
    *   Mendapatkan rating aplikasi rata-rata 4.5+ di Play Store.
    *   Memvalidasi model Freemium dengan tingkat konversi ke premium sebesar 1% dalam 6 bulan.

### **4. Profil Pengguna (User Persona)**

*   **Nama:** Ibu Rini
*   **Usaha:** Warung Nasi "Warung Rini"
*   **Demografi:** Usia 35-55 tahun, berlokasi di kota tingkat dua.
*   **Keahlian Teknologi:** Familiar dengan WhatsApp, Facebook, dan aplikasi e-commerce dasar. Tidak punya waktu atau kesabaran untuk belajar aplikasi yang rumit.
*   **Pain Points:** "Harga cabai dan bawang berubah tiap hari. Saya punya 3 langganan di pasar, pusing kalau harus tanya satu-satu setiap pagi. Kadang saya bayar ke pemasok A, tapi saya lupa catat."

### **5. User Stories & Ruang Lingkup Fitur (MVP)**

User stories dikelompokkan berdasarkan fitur utama. **Prioritas: High = Wajib ada di MVP, Medium = Sebaiknya ada, Low = Bisa ditunda.**

#### **5.1. Onboarding & Setup Awal**
*   **(High)** Sebagai pengguna baru, saya ingin bisa mendaftar dengan mudah menggunakan nomor telepon atau akun Google agar bisa cepat memulai.
*   **(Medium)** Sebagai pengguna baru, saya ingin melihat tutorial singkat (3-4 layar) yang menjelaskan fungsi utama aplikasi agar saya tidak bingung.

#### **5.2. Manajemen Pemasok (Kontak)**
*   **(High)** Sebagai pengguna, saya ingin bisa menambahkan, melihat, mengedit, dan menghapus data pemasok (Nama, Toko, No. Telepon, No. WhatsApp, Kategori, Catatan).
*   **(High)** Sebagai pengguna, saya ingin bisa menekan tombol kontak di profil pemasok untuk langsung menelepon atau memulai chat WhatsApp.
*   **(High)** Sebagai pengguna, saya ingin bisa mencari pemasok berdasarkan nama.

#### **5.3. Manajemen Produk (Bahan Baku)**
*   **(High)** Sebagai pengguna, saya ingin bisa menambahkan, melihat, mengedit, dan menghapus daftar produk/bahan baku yang sering saya beli (misal: "Ayam Broiler", "Beras Pandan Wangi").
*   **(High)** Setiap produk harus memiliki satuan (misal: kg, liter, pcs, ikat, karung).

#### **5.4. Pencatatan Transaksi Pembelian**
*   **(High)** Sebagai pengguna, saya ingin bisa mencatat transaksi pembelian baru dengan mengisi form sederhana (Pilih Pemasok, Tanggal, Daftar Item [Produk, Jumlah, Harga Satuan/Total], Status Pembayaran [Lunas/Belum Lunas]).
*   **(High)** Jika status "Belum Lunas", transaksi tersebut harus otomatis tercatat sebagai utang.
*   **(Medium)** Sebagai pengguna, saya ingin bisa melihat daftar riwayat semua transaksi dan memfilternya berdasarkan pemasok atau rentang tanggal.

#### **5.5. Fitur Inti: Pembanding Harga & Pelacakan Utang**
*   **(High)** Sebagai pengguna, saya ingin bisa memilih satu produk dan aplikasi akan menampilkan daftar harga terakhir dari semua pemasok yang menjual produk tersebut, diurutkan dari yang termurah.
*   **(High)** Sebagai pengguna, saya ingin melihat total akumulasi utang saya kepada semua pemasok di halaman utama (Dashboard).
*   **(High)** Sebagai pengguna, saya ingin melihat rincian utang per pemasok dan bisa menandainya sebagai "Lunas" saat sudah dibayar.

### **6. Persyaratan Non-Fungsional**

*   **Performa:** Aplikasi harus ringan dan dapat berjalan lancar di ponsel Android kelas menengah ke bawah. Waktu muat tidak boleh lebih dari 3 detik.
*   **Usability:** Antarmuka harus sangat intuitif, menggunakan ikon yang jelas dan bahasa Indonesia yang mudah dipahami. Alur kerja harus bisa diselesaikan dengan jumlah klik minimal.
*   **Offline First:** Pengguna harus bisa mencatat transaksi baru bahkan saat tidak ada koneksi internet. Data akan disinkronkan secara otomatis saat koneksi kembali tersedia. (**Ini adalah Medium yang sangat penting untuk nilai jual**).
*   **Keamanan:** Data pengguna (kontak, transaksi) harus disimpan dengan aman di server dan dienkripsi saat transit.
*   **Platform:** Aplikasi ini akan dibuat untuk platform **Android** terlebih dahulu.

### **7. Desain & Pengalaman Pengguna (UX)**

*   **Bahasa Desain:** Minimalis, bersih, dan fungsional. Hindari elemen dekoratif yang tidak perlu.
*   **Palet Warna:** Warna yang menenangkan dan kontras yang baik untuk keterbacaan.
*   **Tipografi:** Ukuran font yang cukup besar dan mudah dibaca oleh target pengguna usia 35+.
*   **Aksesibilitas:** Tombol dan area sentuh harus cukup besar untuk menghindari salah tekan.

### **8. Model Monetisasi**

Aplikasi akan menggunakan model **Freemium**:
*   **Versi Gratis:**
    *   Akses ke semua fitur High yang tercantum di atas.
    *   Batasan: Maksimal 20 Pemasok dan 50 Produk.
    *   Mungkin akan menampilkan iklan yang tidak mengganggu (misal: banner kecil).
*   **Versi Premium (Berbayar - Model Langganan Tahunan/Bulanan):**
    *   Menghilangkan semua batasan (Pemasok & Produk tidak terbatas).
    *   Bebas iklan.
    *   Akses ke fitur premium di masa depan (lihat bagian 10).

### **9. Metrik Keberhasilan (KPI)**

*   **Engagement:** Daily Active Users (DAU), Monthly Active Users (MAU), Jumlah transaksi yang dicatat per pengguna per minggu.
*   **Retensi:** Retensi Hari ke-1, Hari ke-7, dan Hari ke-30.
*   **Akuisisi:** Jumlah unduhan organik.
*   **Monetisasi:** Conversion Rate (Free ke Premium), Monthly Recurring Revenue (MRR).
*   **Kualitas:** App Store Rating, Jumlah laporan bug.

### **10. Di Luar Ruang Lingkup (Future Scope / Post-MVP)**

Fitur-fitur berikut **TIDAK** akan dimasukkan dalam versi 1.0 (MVP) tetapi akan dipertimbangkan untuk rilis mendatang:
*   Grafik analitik tren harga.
*   Sistem pengingat cerdas (jatuh tempo utang, waktu restock).
*   Fitur ekspor data ke Excel/PDF.
*   Manajemen inventori/stok.
*   Dukungan untuk multi-outlet/cabang.
*   Versi iOS.

---
