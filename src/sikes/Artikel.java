package sikes;

import java.time.LocalDateTime;

public class Artikel {
    private String id;
    private String judul;
    private String deskripsi;
    private String konten;
    private String gambarUrl;
    private String kategori;
    private LocalDateTime tanggalPublish;
    private String penulis;
    private int viewCount;
    
    public Artikel(String id, String judul, String deskripsi, String konten, 
                   String gambarUrl, String kategori, String penulis) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.konten = konten;
        this.gambarUrl = gambarUrl;
        this.kategori = kategori;
        this.penulis = penulis;
        this.tanggalPublish = LocalDateTime.now();
        this.viewCount = 0;
    }
    
    // Getters
    public String getId() { return id; }
    public String getJudul() { return judul; }
    public String getDeskripsi() { return deskripsi; }
    public String getKonten() { return konten; }
    public String getGambarUrl() { return gambarUrl; }
    public String getKategori() { return kategori; }
    public LocalDateTime getTanggalPublish() { return tanggalPublish; }
    public String getPenulis() { return penulis; }
    public int getViewCount() { return viewCount; }
    
    // Setters
    public void setJudul(String judul) { this.judul = judul; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public void setKonten(String konten) { this.konten = konten; }
    public void setGambarUrl(String gambarUrl) { this.gambarUrl = gambarUrl; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public void setPenulis(String penulis) { this.penulis = penulis; }
    
    public void incrementViewCount() {
        this.viewCount++;
    }
    
    public String getFormattedDate() {
        return tanggalPublish.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
    
    @Override
    public String toString() {
        return judul;
    }
} 