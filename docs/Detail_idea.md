### **Detail Konsep Aplikasi: "Buku Pemasok"**

#### **1. Filosofi Inti & Elevator Pitch**

*   **Filosofi:** Kesederhanaan adalah kunci. Aplikasi ini tidak berusaha menjadi software akuntansi atau manajemen inventori yang rumit. Tujuannya hanya satu: **Mengubah catatan pemasok yang berantakan di buku, chat WhatsApp, dan ingatan, menjadi sebuah database cerdas yang ada di genggaman tangan.**
*   **Elevator Pitch:** "Untuk pemilik warung makan, kedai kopi, dan pengrajin yang pusing melacak harga bahan baku dari banyak supplier, **Buku Pemasok** adalah aplikasi sederhana untuk mencatat kontak supplier, melacak riwayat harga barang, dan membandingkan harga termurah dalam hitungan detik. Hemat waktu, hemat uang."

#### **2. Profil Pengguna Target (User Persona)**

*   **Nama:** Ibu Aning, 45 tahun.
*   **Usaha:** Usaha Katering Rumahan "Dapur Ibu Aning".
*   **Keseharian:** Pagi belanja ke pasar, siang memasak pesanan, sore mengurus pengiriman dan keuangan.
*   **Masalah:** Dia punya 3-4 pemasok langganan untuk ayam, 5 untuk sayuran, 2 untuk kemasan. Harga sering berubah. Untuk tahu harga termurah, dia harus membuka chat WhatsApp satu per satu atau mengandalkan ingatan. Kadang lupa sudah bayar lunas atau belum ke Pemasok A.
*   **Kebutuhan:** Sebuah alat yang sangat mudah untuk:
    1.  Melihat nomor telepon/WA semua pemasok dalam satu tempat.
    2.  Mengecek, "Minggu lalu saya beli ayam di Pak Budi harganya berapa ya?"
    3.  Menjawab, "Antara Pak Budi dan Pak Joko, siapa yang jual telur lebih murah hari ini?"
    4.  Mengingat, "Saya masih ada utang ke toko kemasan berapa?"

#### **3. Rincian Fitur Utama (Fokus untuk MVP - Minimum Viable Product)**

Ini adalah fitur inti yang harus ada di versi pertama untuk membuktikan nilai aplikasi.

**a. Dashboard Super Sederhana**
*   Saat membuka aplikasi, pengguna langsung melihat informasi paling krusial:
    *   **Total Utang ke Pemasok:** Angka besar yang bisa diklik untuk melihat detail.
    *   **Pesanan Terakhir:** Tampilkan 3-4 pesanan terakhir yang dicatat.
    *   **Tombol Aksi Cepat:** Tombol besar "+ Catat Pesanan Baru" dan "+ Tambah Pemasok Baru".

**b. Modul Pemasok (Buku Kontak Digital)**
*   Ini adalah daftar semua pemasok. Setiap entri pemasok memiliki:
    *   Nama Pemasok & Nama Toko/Usaha.
    *   Nomor Telepon & Nomor WhatsApp (dengan tombol *one-click-to-call/chat*).
    *   Alamat (opsional, bisa terintegrasi Google Maps untuk navigasi).
    *   Kategori Pemasok (misal: "Sayuran", "Daging", "Kemasan", "ATK").
    *   Catatan Khusus (misal: "Buka hanya pagi", "Bisa antar").

**c. Modul Produk/Bahan Baku (Katalog Harga Pribadi)**
*   Pengguna bisa membuat daftar produk/bahan baku yang sering mereka beli (misal: "Ayam Broiler", "Cabai Rawit Merah", "Tepung Terigu", "Box Nasi Ukuran 20x20").
*   Setiap produk memiliki **Riwayat Harga**. Saat pengguna mencatat pesanan, harga akan otomatis masuk ke riwayat ini.
    *   Contoh Riwayat Harga untuk "Ayam Broiler":
        *   15 Okt 2023 - Rp 35.000/kg (dari Pemasok A)
        *   18 Okt 2023 - Rp 34.000/kg (dari Pemasok B)
        *   22 Okt 2023 - Rp 36.000/kg (dari Pemasok A)

**d. Fitur Unggulan: Pembanding Harga**
*   Ini adalah *killer feature*-nya.
*   Pengguna memilih satu produk dari daftar mereka (misal: "Telur Ayam Negeri").
*   Aplikasi akan menampilkan daftar pemasok yang pernah menjual produk tersebut, diurutkan dari **harga termurah atau tanggal terbaru**.
    *   **Hasil Pencarian untuk "Telur Ayam Negeri":**
        1.  **Pemasok C:** Rp 27.000/kg (tercatat 2 hari lalu)
        2.  **Pemasok D:** Rp 27.500/kg (tercatat kemarin)
        3.  **Pemasok E:** Rp 28.000/kg (tercatat 5 hari lalu)
*   Ini memberikan kekuatan pengambilan keputusan instan kepada pengguna.

**e. Pencatatan Pesanan & Utang Sederhana**
*   Formulir yang sangat simpel untuk mencatat pembelian:
    *   Pilih Pemasok.
    *   Tanggal Transaksi.
    *   Tambah Item (Pilih dari daftar produk, masukkan jumlah & harga total).
    *   Status Pembayaran: **Lunas / Belum Lunas**.
*   Jika status "Belum Lunas", transaksi otomatis masuk ke daftar utang di Dashboard. Saat utang dilunasi, pengguna tinggal mengubah statusnya.

#### **4. Fitur Lanjutan (Untuk Pengembangan Berikutnya)**

Setelah MVP berhasil dan mendapatkan pengguna, fitur ini bisa ditambahkan.

*   **Analitik Sederhana:** Grafik tren harga untuk satu produk selama 1, 3, atau 6 bulan. Membantu UMKM melihat fluktuasi harga.
*   **Pengingat Cerdas (Smart Reminder):** Notifikasi seperti, "Harga 'Bawang Merah' dari Pemasok X belum di-update selama 1 bulan" atau "Jatuh tempo pembayaran ke Pemasok Y dalam 3 hari."
*   **Ekspor Data ke Excel/PDF:** Untuk keperluan rekapitulasi bulanan yang lebih serius.
*   **Manajemen Multi-Outlet:** Bagi UMKM yang punya lebih dari satu cabang, agar bisa memisahkan data pemasok per cabang.

#### **5. Model Monetisasi (Bagaimana Aplikasi Ini Menghasilkan Uang)**

Gunakan model **Freemium** agar mudah diadopsi.

*   **Versi Gratis:**
    *   Semua fitur utama (Pencatatan Pemasok, Produk, Pesanan, Pembanding Harga).
    *   **Batasan:** Misal, hanya bisa mencatat hingga 15 Pemasok dan 30 Produk. Ini sudah lebih dari cukup untuk UMKM yang sangat mikro.
*   **Versi Premium (Berbayar):**
    *   **Harga terjangkau:** Misal Rp 19.000/bulan atau Rp 150.000/tahun.
    *   **Keuntungan:**
        *   Tanpa batasan jumlah Pemasok dan Produk.
        *   Akses ke Fitur Lanjutan (Analitik, Pengingat Cerdas).
        *   Ekspor data.
        *   Bebas iklan (jika versi gratis memutuskan untuk memasang iklan).

#### **6. Strategi Pemasaran (Go-to-Market)**

*   **Guerilla Marketing:** Datangi langsung komunitas target. Masuk ke grup Facebook/WhatsApp UMKM Kuliner, Kerajinan, Fashion. Berpartisipasi aktif, lalu tawarkan solusi saat ada yang mengeluh tentang masalah yang relevan.
*   **Konten Edukasi:** Buat konten TikTok/Instagram Reels pendek yang menunjukkan "Cara Lama (Buku kusut)" vs "Cara Baru (Aplikasi Buku Pemasok)". Tunjukkan value-nya secara visual.
*   **Kolaborasi Mikro-Influencer:** Gandeng mentor bisnis UMKM atau food blogger yang punya audiens relevan untuk mereview aplikasi.
*   **Program Referral:** Beri insentif (misal: 1 bulan gratis premium) bagi pengguna yang berhasil mengajak temannya sesama pelaku UMKM untuk menggunakan aplikasi.

Aplikasi **"Buku Pemasok"** ini sangat potensial karena ia tidak mencoba bersaing di pasar yang sudah ramai (POS, akuntansi, marketplace). Ia menciptakan kategorinya sendiri dengan fokus pada masalah pra-produksi yang sering diabaikan namun sangat krusial bagi profitabilitas UMKM.
