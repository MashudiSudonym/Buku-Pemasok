### **Dokumen Timeline & Rencana Proyek: MVP "Buku Pemasok"**

*   **Versi Dokumen:** 1.0
*   **Arsitektur Kunci:** Offline-First
*   **Total Estimasi Hari Kerja:** **38 Hari**
*   **Rekomendasi Durasi Proyek (dengan buffer 25%):** **~47.5 Hari Kerja** (~9-10 Minggu Kalender)

| **Fase / Sprint** | **Fitur (SRS Ref)** | **Tugas Spesifik untuk Tim Android** | **Est. (Hari)** | **Dependensi** | **Catatan & Kriteria Selesai (Done Criteria)** |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Fase 1: Fondasi & Setup** | **A-1, B-1, FR-1** | **(Supabase)** Setup project, jalankan skrip DDL SQL untuk membuat tabel, dan konfigurasikan RLS (Row Level Security) dasar untuk User. | 1.5 | - | **Selesai jika:** Tabel `users`, `suppliers`, `products`, `purchase_transactions`, `transaction_items` ada di Supabase. RLS memastikan user hanya bisa melihat data miliknya. |
| | | **(Android)** Setup project Android: Arsitektur (MVVM), DI (Hilt/Koin), DB Room dengan skema SQLite. | 2 | - | **Selesai jika:** Project Android bisa di-build, arsitektur dasar sudah ada, dan skema Room untuk semua tabel MVP sudah didefinisikan. |
| | | **(Android)** Integrasi Supabase SDK ke project Android, konfigurasi klien. | 0.5 | Android Setup | **Selesai jika:** Klien Supabase bisa diinisialisasi tanpa error saat aplikasi dimulai. |
| | | **(Android)** Membuat UI untuk layar Login/Register. | 1 | Android Setup | **Selesai jika:** Layar login dengan opsi Google dan Nomor HP sudah dibuat dalam bentuk layout XML/Compose. |
| | | **(Android)** Implementasi logic Autentikasi menggunakan Supabase Auth SDK (Google & Nomor HP). | 1.5 | SDK & UI Login | **Selesai jika:** Pengguna bisa mendaftar dan login menggunakan kedua metode. Session pengguna tersimpan. |
| **Total Fase 1** | | | **6.5 Hari** | | **Milestone: Prototipe berfungsi dengan alur autentikasi end-to-end.** |
| **Fase 2: Pemasok & Produk** | **FR-2 & FR-3** | **(Android)** Membuat Entity Room & DAO untuk `suppliers` dan `products`. | 1 | Fase 1 | **Selesai jika:** Semua @Entity dan @Dao untuk `suppliers` & `products` sudah dibuat dan terintegrasi dengan AppDatabase. |
| | | **(Android)** Membuat UI untuk Daftar Pemasok & Form Tambah/Edit Pemasok. | 2 | Entity Room | **Selesai jika:** Layar daftar pemasok (RecyclerView) dan form input untuk data pemasok sudah dibuat. |
| | | **(Android)** Implementasi logic CRUD Pemasok di ViewModel yang terhubung ke Room (mode offline). | 1.5 | UI Pemasok | **Selesai jika:** Pengguna bisa menambah, melihat, mengedit, dan menghapus pemasok secara lokal tanpa koneksi internet. |
| | | **(Android)** Membuat UI & logic CRUD untuk Produk (reuse dari Pemasok). | 2 | CRUD Pemasok | **Selesai jika:** Fungsionalitas CRUD lokal untuk produk berfungsi penuh, mengadopsi komponen dari fitur Pemasok. |
| | | **(Sinkronisasi)** Membuat service/repository untuk sinkronisasi `suppliers` & `products` ke Supabase (query `select`, `insert`, `update` via SDK). | 2.5 | CRUD Lokal | **Selesai jika:** Perubahan lokal pada pemasok & produk bisa disinkronkan ke Supabase saat online. |
| **Total Fase 2** | | | **9 Hari** | | **Milestone: Fitur data master berfungsi penuh offline & online.** |
| **Fase 3: Transaksi** | **FR-4** | **(Android)** Membuat Entity Room & DAO untuk `purchase_transactions` & `transaction_items`. | 1 | Fase 1 | **Selesai jika:** @Entity dan @Dao untuk kedua tabel transaksi sudah dibuat. |
| | | **(Android)** Membuat UI Form Catat Transaksi yang dinamis (tambah/hapus item). | 2.5 | Entity Room | **Selesai jika:** Pengguna bisa membuat transaksi, memilih pemasok, dan menambah/menghapus beberapa item produk dalam satu form. |
| | | **(Android)** Implementasi logic ViewModel untuk menyimpan transaksi ke Room. | 1.5 | UI Transaksi | **Selesai jika:** Transaksi baru berhasil disimpan ke dalam dua tabel di Room secara atomik. |
| | | **(Sinkronisasi)** Menambahkan logic untuk sinkronisasi `purchase_transactions` & `transaction_items` ke Supabase. | 2 | Logic Lokal Transaksi | **Selesai jika:** Transaksi yang dibuat offline berhasil diunggah ke Supabase saat online. |
| **Total Fase 3** | | | **7 Hari** | | **Milestone: Pengguna dapat mencatat data transaksional inti.** |
| **Fase 4: Nilai Jual & Dashboard** | **FR-5** | **(Android)** Membuat UI Dashboard & Halaman Rincian Utang. | 2 | Fase 1 | **Selesai jika:** Layout untuk Dashboard (kartu summary) dan layar Rincian Utang (list dikelompokkan) sudah dibuat. |
| | | **(Android)** Implementasi logic query lokal untuk menampilkan data di Dashboard & Halaman Utang. | 1.5 | UI Dashboard | **Selesai jika:** Data di UI Dashboard dan Rincian Utang diambil dari database Room. |
| | | **(Supabase)** Membuat & menguji **RPC/DB Function** di Supabase untuk kalkulasi Total Utang. | 1.5 | Skema DB Fase 1 | **Selesai jika:** Fungsi PostgreSQL yang menghitung `SUM` utang untuk satu user_id sudah ada dan bisa diuji di Supabase SQL Editor. |
| | | **(Android)** Memanggil fungsi RPC Total Utang dari aplikasi Android dan menampilkannya di UI. | 1 | RPC Utang | **Selesai jika:** Angka di kartu "Total Utang" di Dashboard menampilkan hasil dari panggilan RPC. |
| | | **(Supabase)** Membuat & menguji **RPC/DB Function** di Supabase untuk Perbandingan Harga. | 2 | Skema DB Fase 1 | **Selesai jika:** Fungsi PostgreSQL yang mengembalikan daftar harga termurah untuk suatu produk sudah dibuat. |
| | | **(Android)** Membuat UI Halaman Perbandingan Harga & memanggil RPC-nya. | 1.5 | RPC Harga | **Selesai jika:** Saat pengguna memilih produk, aplikasi memanggil RPC dan menampilkan daftar harga yang diurutkan. |
| **Total Fase 4** | | | **9.5 Hari** | | **Milestone: Semua fitur inti MVP telah dikembangkan.** |
| **Fase 5: Finalisasi & Rilis** | **All** | **(QA)** Pengujian End-to-End untuk semua alur (termasuk skenario offline/online). | 3 | Semua Fitur | **Selesai jika:** Ceklis pengujian untuk semua user flow telah dijalankan dan hasilnya didokumentasikan. |
| | | **(Bug Fixing)** Perbaikan bug dari hasil pengujian internal. | 2 | Pengujian | **Selesai jika:** Semua bug kritis dan mayor yang ditemukan telah diperbaiki. |
| | | **(Rilis)** Menyiapkan build rilis untuk Play Store (signing, ProGuard, optimasi). | 1 | Bug Fixing | **Selesai jika:** File AAB (Android App Bundle) yang sudah ditandatangani siap untuk diunggah ke Google Play Console. |
| **Total Fase 5** | | | **6 Hari** | | **Milestone: Aplikasi Siap Rilis (Release Candidate).** |
