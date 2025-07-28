Skema ini menggunakan dialek **PostgreSQL** karena dukungannya yang kuat untuk tipe data seperti `UUID` dan `ENUM`, yang sangat cocok untuk aplikasi modern. Komentar ditambahkan untuk menjelaskan tujuan setiap tabel dan kolom.

---

### **SQL DDL (PostgreSQL) - Buku Pemasok v1.0**

```sql
-- DDL untuk Aplikasi "Buku Pemasok" v1.0 (MVP)
-- Dialek: PostgreSQL
-- Dokumen ini mendefinisikan struktur database untuk mendukung fitur-fitur MVP
-- yang tercantum dalam PRD dan SRS.

-- Mengaktifkan ekstensi untuk menghasilkan UUID secara otomatis
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =================================================================
-- Tabel 1: Pengguna (Users)
-- Menyimpan data dasar pengguna yang mendaftar ke aplikasi.
-- =================================================================
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(), -- ID unik untuk setiap pengguna
    google_id VARCHAR(255) UNIQUE,                  -- ID unik dari Google, untuk login via Google
    phone_number VARCHAR(20) UNIQUE,                -- Nomor telepon, untuk login via OTP
    full_name VARCHAR(100),                         -- Nama lengkap pengguna
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),  -- Waktu pendaftaran
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE users IS 'Menyimpan data akun pengguna aplikasi.';
COMMENT ON COLUMN users.id IS 'Primary Key unik untuk pengguna.';
COMMENT ON COLUMN users.google_id IS 'ID unik dari provider Google OAuth.';
COMMENT ON COLUMN users.phone_number IS 'Nomor telepon unik yang digunakan untuk pendaftaran/login.';


-- =================================================================
-- Tabel 2: Pemasok (Suppliers)
-- Menyimpan daftar pemasok yang dimiliki oleh setiap pengguna.
-- =================================================================
CREATE TABLE suppliers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE, -- Foreign Key ke pengguna
    name VARCHAR(150) NOT NULL,                     -- Nama pemasok (misal: "Pak Budi Ayam")
    store_name VARCHAR(150),                        -- Nama toko/usaha pemasok (misal: "Toko Berkah Jaya")
    phone_number VARCHAR(20),                       -- Nomor telepon pemasok
    whatsapp_number VARCHAR(20),                    -- Nomor WhatsApp pemasok
    category VARCHAR(100),                          -- Kategori pemasok (misal: "Sayuran", "Daging")
    notes TEXT,                                     -- Catatan tambahan tentang pemasok
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    -- Memastikan seorang pengguna tidak bisa menambahkan pemasok dengan nama yang sama persis.
    CONSTRAINT suppliers_user_id_name_unique UNIQUE (user_id, name)
);

COMMENT ON TABLE suppliers IS 'Menyimpan daftar pemasok yang spesifik untuk setiap pengguna.';
COMMENT ON COLUMN suppliers.user_id IS 'Menghubungkan pemasok ke pengguna yang membuatnya.';


-- =================================================================
-- Tabel 3: Produk (Products)
-- Menyimpan daftar produk/bahan baku yang sering dibeli oleh pengguna.
-- =================================================================
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE, -- Foreign Key ke pengguna
    name VARCHAR(150) NOT NULL,                     -- Nama produk (misal: "Cabai Rawit Merah")
    unit VARCHAR(50) NOT NULL,                      -- Satuan produk (misal: "kg", "liter", "pcs", "ikat")
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    -- Memastikan seorang pengguna tidak bisa menambahkan produk dengan nama yang sama persis.
    CONSTRAINT products_user_id_name_unique UNIQUE (user_id, name)
);

COMMENT ON TABLE products IS 'Menyimpan katalog produk/bahan baku yang dibuat oleh pengguna.';
COMMENT ON COLUMN products.unit IS 'Satuan pengukuran untuk produk, misal: kg, liter, pcs.';


-- =================================================================
-- Tabel 4: Transaksi Pembelian (Purchase Transactions)
-- Tabel header untuk setiap transaksi pembelian yang dilakukan pengguna.
-- =================================================================
CREATE TYPE payment_status_enum AS ENUM ('LUNAS', 'BELUM_LUNAS');

CREATE TABLE purchase_transactions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    supplier_id UUID NOT NULL REFERENCES suppliers(id) ON DELETE RESTRICT, -- Mencegah penghapusan supplier jika ada transaksi
    transaction_date TIMESTAMPTZ NOT NULL,          -- Tanggal dan waktu transaksi dilakukan
    payment_status payment_status_enum NOT NULL DEFAULT 'BELUM_LUNAS', -- Status pembayaran
    total_amount DECIMAL(15, 2) NOT NULL DEFAULT 0, -- Total nilai transaksi, dihitung dari item-itemnya
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE purchase_transactions IS 'Mencatat setiap transaksi pembelian (header).';
COMMENT ON COLUMN purchase_transactions.supplier_id IS 'Menghubungkan transaksi ke pemasok. ON DELETE RESTRICT untuk integritas data.';
COMMENT ON COLUMN purchase_transactions.payment_status IS 'Status utang transaksi: LUNAS atau BELUM_LUNAS.';
COMMENT ON COLUMN purchase_transactions.total_amount IS 'Nilai total dari semua item dalam transaksi ini.';


-- =================================================================
-- Tabel 5: Item Transaksi (Transaction Items)
-- Tabel detail untuk setiap transaksi. Satu transaksi bisa memiliki banyak item.
-- Ini adalah tabel inti untuk fitur perbandingan harga.
-- =================================================================
CREATE TABLE transaction_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    transaction_id UUID NOT NULL REFERENCES purchase_transactions(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE RESTRICT, -- Mencegah penghapusan produk jika ada transaksi
    quantity DECIMAL(10, 2) NOT NULL,               -- Jumlah produk yang dibeli (mendukung desimal, misal: 1.5 kg)
    price DECIMAL(15, 2) NOT NULL,                  -- Harga total untuk item ini (quantity * harga satuan)
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE transaction_items IS 'Menyimpan detail item per transaksi (detail).';
COMMENT ON COLUMN transaction_items.price IS 'Harga total untuk line item ini (bukan harga per unit).';

-- =================================================================
-- INDEKS UNTUK OPTIMASI PERFORMA
-- Indeks dibuat untuk mempercepat query yang sering dijalankan.
-- =================================================================

-- Mempercepat pencarian pemasok dan produk oleh pengguna
CREATE INDEX idx_suppliers_user_id ON suppliers(user_id);
CREATE INDEX idx_products_user_id ON products(user_id);

-- Mempercepat pencarian transaksi oleh pengguna, terutama untuk melacak utang
CREATE INDEX idx_purchase_transactions_user_id ON purchase_transactions(user_id);
CREATE INDEX idx_purchase_transactions_supplier_id ON purchase_transactions(supplier_id);
CREATE INDEX idx_purchase_transactions_user_payment_status ON purchase_transactions(user_id, payment_status);

-- Indeks KRUSIAL untuk fitur "Perbandingan Harga"
-- Mempercepat pencarian semua riwayat pembelian untuk suatu produk.
CREATE INDEX idx_transaction_items_product_id ON transaction_items(product_id);
CREATE INDEX idx_transaction_items_transaction_id ON transaction_items(transaction_id);

-- Trigger untuk memperbarui kolom 'updated_at' secara otomatis
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Menerapkan trigger ke setiap tabel yang memiliki kolom updated_at
CREATE TRIGGER set_timestamp_users BEFORE UPDATE ON users FOR EACH ROW EXECUTE PROCEDURE trigger_set_timestamp();
CREATE TRIGGER set_timestamp_suppliers BEFORE UPDATE ON suppliers FOR EACH ROW EXECUTE PROCEDURE trigger_set_timestamp();
CREATE TRIGGER set_timestamp_products BEFORE UPDATE ON products FOR EACH ROW EXECUTE PROCEDURE trigger_set_timestamp();
CREATE TRIGGER set_timestamp_purchase_transactions BEFORE UPDATE ON purchase_transactions FOR EACH ROW EXECUTE PROCEDURE trigger_set_timestamp();

```

### Penjelasan Desain

1.  **Penggunaan `UUID`:** Dipilih sebagai Primary Key untuk menghindari masalah ID yang berurutan dan memudahkan sinkronisasi data antara perangkat (offline-first) dan server tanpa konflik ID.
2.  **Relasi yang Jelas:** Setiap tabel data (pemasok, produk, transaksi) terhubung ke `users(id)`. Ini memastikan bahwa data setiap pengguna terisolasi dengan baik (multi-tenancy).
3.  **Integritas Data (`ON DELETE`)**:
    *   `ON DELETE CASCADE` pada `user_id`: Jika seorang pengguna dihapus, semua data terkaitnya (pemasok, produk, transaksi) akan ikut terhapus.
    *   `ON DELETE RESTRICT` pada `supplier_id` dan `product_id`: Mencegah pengguna menghapus pemasok atau produk jika masih ada riwayat transaksi yang terkait. Ini menjaga keutuhan data historis.
4.  **Struktur Transaksi:** Dipisahkan menjadi `purchase_transactions` (header) dan `transaction_items` (detail). Ini adalah praktik standar untuk menangani relasi "one-to-many" (satu transaksi bisa berisi banyak produk).
5.  **Tipe Data yang Tepat:**
    *   `DECIMAL` digunakan untuk nilai moneter dan kuantitas untuk menghindari masalah presisi floating-point.
    *   `TIMESTAMPTZ` (Timestamp with Time Zone) digunakan untuk tanggal dan waktu agar tidak ambigu di zona waktu yang berbeda.
    *   `ENUM` digunakan untuk `payment_status` agar nilainya konsisten dan terbatas.
6.  **Indeks (Indexes):** Indeks ditambahkan pada kolom Foreign Key dan kolom yang sering digunakan dalam klausa `WHERE` (seperti `product_id` di `transaction_items` untuk fitur perbandingan harga) untuk memastikan performa query tetap cepat seiring bertambahnya data.
