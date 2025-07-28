Perbedaan utama dari versi PostgreSQL adalah:
*   Tidak ada tipe data `UUID` bawaan. Kita akan menggunakan `TEXT` untuk menyimpan UUID yang di-generate oleh aplikasi.
*   Tidak ada tipe data `ENUM`. Kita akan menggunakan `TEXT` dengan `CHECK` constraint untuk meniru fungsionalitas ini.
*   Sintaks untuk Foreign Key dan `PRIMARY KEY` sedikit berbeda dan lebih sederhana.
*   Tidak ada fungsi `uuid_generate_v4()` atau `NOW()`. Nilai-nilai ini harus disediakan oleh aplikasi saat melakukan `INSERT`.
*   Tidak ada skema atau ekstensi.

---

### **SQL DDL (SQLite) - Buku Pemasok v1.0**

```sql
-- DDL untuk Aplikasi "Buku Pemasok" v1.0 (MVP)
-- Dialek: SQLite 3
-- Dokumen ini mendefinisikan struktur database untuk penyimpanan lokal di aplikasi Android.

-- =================================================================
-- Tabel 1: Pengguna (Users)
-- Di database lokal, tabel ini mungkin hanya berisi satu baris untuk pengguna yang sedang login.
-- =================================================================
CREATE TABLE IF NOT EXISTS users (
    id TEXT PRIMARY KEY,                            -- ID unik pengguna (UUID format, dari server/app)
    google_id TEXT UNIQUE,                          -- ID unik dari Google, untuk login via Google
    phone_number TEXT UNIQUE,                       -- Nomor telepon, untuk login via OTP
    full_name TEXT,                                 -- Nama lengkap pengguna
    created_at TEXT NOT NULL,                       -- Waktu pendaftaran (ISO 8601 format: YYYY-MM-DD HH:MM:SS)
    updated_at TEXT NOT NULL
);

-- =================================================================
-- Tabel 2: Pemasok (Suppliers)
-- Menyimpan daftar pemasok yang dimiliki oleh setiap pengguna.
-- =================================================================
CREATE TABLE IF NOT EXISTS suppliers (
    id TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,                          -- Foreign Key ke pengguna
    name TEXT NOT NULL,                             -- Nama pemasok
    store_name TEXT,                                -- Nama toko/usaha pemasok
    phone_number TEXT,                              -- Nomor telepon pemasok
    whatsapp_number TEXT,                           -- Nomor WhatsApp pemasok
    category TEXT,                                  -- Kategori pemasok
    notes TEXT,                                     -- Catatan tambahan tentang pemasok
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (user_id, name) -- Memastikan nama pemasok unik per pengguna
);

-- =================================================================
-- Tabel 3: Produk (Products)
-- Menyimpan daftar produk/bahan baku yang sering dibeli oleh pengguna.
-- =================================================================
CREATE TABLE IF NOT EXISTS products (
    id TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,
    name TEXT NOT NULL,                             -- Nama produk
    unit TEXT NOT NULL,                             -- Satuan produk (misal: kg, liter, pcs)
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (user_id, name) -- Memastikan nama produk unik per pengguna
);

-- =================================================================
-- Tabel 4: Transaksi Pembelian (Purchase Transactions)
-- Tabel header untuk setiap transaksi pembelian.
-- =================================================================
CREATE TABLE IF NOT EXISTS purchase_transactions (
    id TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,
    supplier_id TEXT NOT NULL,
    transaction_date TEXT NOT NULL,                 -- Tanggal dan waktu transaksi (ISO 8601 format)
    payment_status TEXT NOT NULL CHECK(payment_status IN ('LUNAS', 'BELUM_LUNAS')) DEFAULT 'BELUM_LUNAS',
    total_amount REAL NOT NULL DEFAULT 0.0,         -- Total nilai transaksi
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE RESTRICT
);

-- =================================================================
-- Tabel 5: Item Transaksi (Transaction Items)
-- Tabel detail untuk setiap transaksi.
-- =================================================================
CREATE TABLE IF NOT EXISTS transaction_items (
    id TEXT PRIMARY KEY,
    transaction_id TEXT NOT NULL,
    product_id TEXT NOT NULL,
    quantity REAL NOT NULL,                         -- Jumlah produk yang dibeli
    price REAL NOT NULL,                            -- Harga total untuk item ini
    created_at TEXT NOT NULL,

    FOREIGN KEY (transaction_id) REFERENCES purchase_transactions(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
);

-- =================================================================
-- INDEKS UNTUK OPTIMASI PERFORMA
-- Indeks dibuat untuk mempercepat query yang sering dijalankan di perangkat mobile.
-- =================================================================

-- Mempercepat pencarian data yang terikat pada pengguna
CREATE INDEX IF NOT EXISTS idx_suppliers_user_id ON suppliers(user_id);
CREATE INDEX IF NOT EXISTS idx_products_user_id ON products(user_id);
CREATE INDEX IF NOT EXISTS idx_purchase_transactions_user_id ON purchase_transactions(user_id);

-- Mempercepat filter transaksi berdasarkan pemasok dan status utang
CREATE INDEX IF NOT EXISTS idx_purchase_transactions_supplier_id ON purchase_transactions(supplier_id);
CREATE INDEX IF NOT EXISTS idx_purchase_transactions_user_payment_status ON purchase_transactions(user_id, payment_status);

-- Indeks KRUSIAL untuk fitur "Perbandingan Harga"
CREATE INDEX IF NOT EXISTS idx_transaction_items_product_id ON transaction_items(product_id);
CREATE INDEX IF NOT EXISTS idx_transaction_items_transaction_id ON transaction_items(transaction_id);

```

### Penjelasan Perubahan untuk SQLite

1.  **Tipe Data `PRIMARY KEY`:** Semua `UUID` diubah menjadi `TEXT`. Aplikasi (misalnya menggunakan library `java.util.UUID`) bertanggung jawab untuk men-generate nilai ini sebelum melakukan `INSERT`.
2.  **Tipe Data `ENUM`:** Tipe data `payment_status_enum` dihilangkan. Sebagai gantinya, kolom `payment_status` menggunakan tipe `TEXT` dengan constraint `CHECK(payment_status IN ('LUNAS', 'BELUM_LUNAS'))` untuk memastikan hanya nilai-nilai tersebut yang bisa dimasukkan.
3.  **Tipe Data Waktu:** `TIMESTAMPTZ` diubah menjadi `TEXT`. Praktik terbaiknya adalah menyimpan timestamp dalam format standar yang mudah di-parse dan diurutkan, seperti **ISO 8601** (`"YYYY-MM-DDTHH:MM:SS.SSSZ"`). Aplikasi bertanggung jawab untuk memformat tanggal dan waktu ke dalam string ini.
4.  **Tipe Data Angka:** `DECIMAL` diubah menjadi `REAL`, yang merupakan tipe data floating-point di SQLite. Untuk aplikasi finansial di mana presisi sangat penting, cara yang lebih aman adalah menyimpan nilai moneter sebagai `INTEGER` dalam satuan terkecil (misal: Rupiah disimpan dalam sen), kemudian dikonversi kembali saat ditampilkan di UI. Namun, `REAL` sudah cukup untuk MVP ini.
5.  **`DEFAULT` Values:** `DEFAULT uuid_generate_v4()` dan `DEFAULT NOW()` dihapus. Aplikasi harus menyediakan nilai untuk kolom `id`, `created_at`, dan `updated_at` pada setiap operasi `INSERT`.
6.  **`IF NOT EXISTS`:** Perintah `CREATE TABLE IF NOT EXISTS` digunakan. Ini adalah praktik yang baik di SQLite untuk mencegah error jika skema sudah pernah dibuat.
7.  **`ON DELETE RESTRICT`:** SQLite mendukung aksi `ON DELETE RESTRICT`, yang berfungsi untuk mencegah penghapusan baris induk jika masih ada baris anak yang mereferensikannya. Ini penting untuk menjaga integritas data transaksi.
8.  **Triggers:** Trigger untuk `updated_at` dihapus demi kesederhanaan. Logika untuk memperbarui timestamp ini harus ditangani di level kode aplikasi. Setiap kali ada operasi `UPDATE`, aplikasi juga harus memperbarui kolom `updated_at`.

Skema ini siap digunakan dengan library seperti **Room Persistence Library** di Android, di mana Anda akan mendefinisikan Entity yang sesuai dengan struktur tabel ini.
