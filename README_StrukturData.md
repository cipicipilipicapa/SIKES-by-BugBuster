# Struktur Data SIKES

## Overview
Dokumen ini menjelaskan struktur data yang telah ditambahkan pada aplikasi SIKES untuk bagian Artikel dan Konsultasi.

## 1. Struktur Data Artikel

### Kelas Artikel
```java
public class Artikel {
    private String id;                    // ID unik artikel
    private String judul;                 // Judul artikel
    private String deskripsi;             // Deskripsi singkat
    private String konten;                // Konten lengkap artikel
    private String gambarUrl;             // URL gambar artikel
    private String kategori;              // Kategori artikel
    private LocalDateTime tanggalPublish; // Tanggal publikasi
    private String penulis;               // Nama penulis
    private int viewCount;                // Jumlah view
}
```

### Kelas ArtikelManager
Singleton class untuk mengelola data artikel:
- `getInstance()` - Mendapatkan instance tunggal
- `getArtikelList()` - Mendapatkan semua artikel
- `getArtikelById(String id)` - Mencari artikel berdasarkan ID
- `getArtikelByKategori(String kategori)` - Filter artikel berdasarkan kategori
- `searchArtikel(String keyword)` - Pencarian artikel
- `addArtikel(Artikel artikel)` - Menambah artikel baru
- `removeArtikel(Artikel artikel)` - Menghapus artikel
- `updateArtikel(Artikel artikel)` - Update artikel

### Artikel Default
1. **ART001** - Penyebab Diabetes
   - Kategori: Penyakit
   - Penulis: Dr. Ahmad Fauzi
   
2. **ART002** - Makanan untuk Penderita Diabetes
   - Kategori: Nutrisi
   - Penulis: Dr. Sarah Wijaya
   
3. **ART003** - 5 Obat Diabetes di Apotek
   - Kategori: Pengobatan
   - Penulis: Dr. Budi Santoso

## 2. Struktur Data Dokter

### Kelas Dokter
```java
public class Dokter {
    private String id;                    // ID unik dokter
    private String nama;                  // Nama dokter
    private String spesialisasi;          // Spesialisasi dokter
    private String pengalaman;            // Pengalaman kerja
    private String pendidikan;            // Riwayat pendidikan
    private String noSIP;                 // Nomor Surat Izin Praktik
    private String rumahSakit;            // Rumah sakit tempat praktik
    private String jamPraktik;            // Jam praktik
    private boolean online;               // Status online/offline
    private LocalTime jamOnline;          // Jam mulai online
    private LocalTime jamOffline;         // Jam selesai online
    private double rating;                // Rating dokter
    private int jumlahKonsultasi;         // Jumlah konsultasi
    private String fotoUrl;               // URL foto dokter
    private List<String> sertifikasi;     // Daftar sertifikasi
}
```

### Kelas DokterManager
Singleton class untuk mengelola data dokter:
- `getInstance()` - Mendapatkan instance tunggal
- `getDokterList()` - Mendapatkan semua dokter
- `getDokterById(String id)` - Mencari dokter berdasarkan ID
- `getDokterBySpesialisasi(String spesialisasi)` - Filter dokter berdasarkan spesialisasi
- `getDokterOnline()` - Mendapatkan dokter yang sedang online
- `searchDokter(String keyword)` - Pencarian dokter
- `getDokterByRating(double minRating)` - Filter dokter berdasarkan rating
- `getTopRatedDokter(int limit)` - Mendapatkan dokter dengan rating tertinggi
- `getMostActiveDokter(int limit)` - Mendapatkan dokter paling aktif
- `updateAllDokterStatus()` - Update status online semua dokter
- `incrementKonsultasi(String dokterId)` - Menambah jumlah konsultasi

### Dokter Default
1. **DOK001** - Dr. Sumanto
   - Spesialisasi: Dokter Umum
   - Pengalaman: 10 tahun
   - Rumah Sakit: RS Umum Daerah
   - Rating: 4.8
   
2. **DOK002** - Dr. Silpi
   - Spesialisasi: Dokter Spesialis Penyakit Dalam
   - Pengalaman: 5 tahun
   - Rumah Sakit: RS Siloam
   - Rating: 4.6
   
3. **DOK003** - Dr. Lis
   - Spesialisasi: Dokter Spesialis Gizi
   - Pengalaman: 8 tahun
   - Rumah Sakit: RS Mitra Keluarga
   - Rating: 4.9
   
4. **DOK004** - Dr. Alesandro
   - Spesialisasi: Dokter Spesialis Jantung
   - Pengalaman: 15 tahun
   - Rumah Sakit: RS Harapan Kita
   - Rating: 4.7
   
5. **DOK005** - Dr. Rina
   - Spesialisasi: Dokter Spesialis Mata
   - Pengalaman: 12 tahun
   - Rumah Sakit: RS Mata Cicendo
   - Rating: 4.5

## 3. Fitur yang Ditambahkan

### Artikel
- ✅ Pencarian artikel berdasarkan keyword
- ✅ Kategori artikel (Penyakit, Nutrisi, Pengobatan)
- ✅ Informasi lengkap artikel (judul, deskripsi, konten, penulis, tanggal)
- ✅ Counter view artikel

### Konsultasi
- ✅ Daftar dokter dengan informasi lengkap
- ✅ Status online/offline berdasarkan jam praktik
- ✅ Rating dan jumlah konsultasi dokter
- ✅ Pencarian dokter berdasarkan nama, spesialisasi, atau rumah sakit
- ✅ Detail dokter (double-click untuk melihat detail)
- ✅ Sertifikasi dan riwayat pendidikan dokter
- ✅ Filter dokter berdasarkan spesialisasi
- ✅ Tracking jumlah konsultasi per dokter

## 4. Penggunaan

### Artikel
```java
// Mendapatkan semua artikel
ArtikelManager artikelManager = ArtikelManager.getInstance();
ObservableList<Artikel> artikelList = artikelManager.getArtikelList();

// Mencari artikel
List<Artikel> hasilPencarian = artikelManager.searchArtikel("diabetes");

// Mendapatkan artikel berdasarkan kategori
List<Artikel> artikelPenyakit = artikelManager.getArtikelByKategori("Penyakit");
```

### Dokter
```java
// Mendapatkan semua dokter
DokterManager dokterManager = DokterManager.getInstance();
ObservableList<Dokter> dokterList = dokterManager.getDokterList();

// Mencari dokter
List<Dokter> hasilPencarian = dokterManager.searchDokter("jantung");

// Mendapatkan dokter yang online
List<Dokter> dokterOnline = dokterManager.getDokterOnline();

// Mendapatkan dokter dengan rating tertinggi
List<Dokter> topDokter = dokterManager.getTopRatedDokter(3);
```

## 5. Keunggulan Struktur Data

1. **Terorganisir**: Data tersimpan dalam struktur yang jelas dan terstruktur
2. **Mudah Dikelola**: Menggunakan pattern Singleton untuk manajemen data
3. **Fleksibel**: Mudah untuk menambah, mengubah, atau menghapus data
4. **Efisien**: Menggunakan ObservableList untuk update UI secara real-time
5. **Extensible**: Mudah untuk menambah fitur baru tanpa mengubah struktur dasar
6. **Type Safe**: Menggunakan Java generics untuk type safety
7. **Searchable**: Fitur pencarian yang powerful dengan multiple criteria

## 6. Implementasi UI

### Artikel
- Search field untuk pencarian artikel
- Tampilan artikel dengan gambar, judul, dan deskripsi
- Link "Baca Selengkapnya" untuk melihat detail artikel

### Konsultasi
- ListView dengan custom cell untuk menampilkan dokter
- Informasi dokter: nama, spesialisasi, rating, status
- Double-click untuk melihat detail lengkap dokter
- Search field untuk pencarian dokter
- Chat interface untuk konsultasi dengan dokter 