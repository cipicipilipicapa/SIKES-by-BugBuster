package sikes;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Dokter {
    private String id;
    private String nama;
    private String spesialisasi;
    private String pengalaman;
    private String pendidikan;
    private String noSIP; // Surat Izin Praktik
    private String rumahSakit;
    private String jamPraktik;
    private boolean online;
    private LocalTime jamOnline;
    private LocalTime jamOffline;
    private double rating;
    private int jumlahKonsultasi;
    private String fotoUrl;
    private List<String> sertifikasi;
    
    public Dokter(String id, String nama, String spesialisasi, String pengalaman, 
                  String pendidikan, String noSIP, String rumahSakit, String jamPraktik) {
        this.id = id;
        this.nama = nama;
        this.spesialisasi = spesialisasi;
        this.pengalaman = pengalaman;
        this.pendidikan = pendidikan;
        this.noSIP = noSIP;
        this.rumahSakit = rumahSakit;
        this.jamPraktik = jamPraktik;
        this.online = false;
        this.rating = 0.0;
        this.jumlahKonsultasi = 0;
        this.fotoUrl = "images/Dokter.png";
        this.sertifikasi = new ArrayList<>();
        this.jamOnline = LocalTime.of(8, 0); // Default 08:00
        this.jamOffline = LocalTime.of(17, 0); // Default 17:00
    }
    
    // Getters
    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getSpesialisasi() { return spesialisasi; }
    public String getPengalaman() { return pengalaman; }
    public String getPendidikan() { return pendidikan; }
    public String getNoSIP() { return noSIP; }
    public String getRumahSakit() { return rumahSakit; }
    public String getJamPraktik() { return jamPraktik; }
    public boolean isOnline() { return online; }
    public LocalTime getJamOnline() { return jamOnline; }
    public LocalTime getJamOffline() { return jamOffline; }
    public double getRating() { return rating; }
    public int getJumlahKonsultasi() { return jumlahKonsultasi; }
    public String getFotoUrl() { return fotoUrl; }
    public List<String> getSertifikasi() { return new ArrayList<>(sertifikasi); }
    
    // Setters
    public void setNama(String nama) { this.nama = nama; }
    public void setSpesialisasi(String spesialisasi) { this.spesialisasi = spesialisasi; }
    public void setPengalaman(String pengalaman) { this.pengalaman = pengalaman; }
    public void setPendidikan(String pendidikan) { this.pendidikan = pendidikan; }
    public void setNoSIP(String noSIP) { this.noSIP = noSIP; }
    public void setRumahSakit(String rumahSakit) { this.rumahSakit = rumahSakit; }
    public void setJamPraktik(String jamPraktik) { this.jamPraktik = jamPraktik; }
    public void setOnline(boolean online) { this.online = online; }
    public void setJamOnline(LocalTime jamOnline) { this.jamOnline = jamOnline; }
    public void setJamOffline(LocalTime jamOffline) { this.jamOffline = jamOffline; }
    public void setRating(double rating) { this.rating = rating; }
    public void setJumlahKonsultasi(int jumlahKonsultasi) { this.jumlahKonsultasi = jumlahKonsultasi; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    
    public void addSertifikasi(String sertifikasi) {
        this.sertifikasi.add(sertifikasi);
    }
    
    public void incrementKonsultasi() {
        this.jumlahKonsultasi++;
    }
    
    public void updateStatusOnline() {
        LocalTime now = LocalTime.now();
        this.online = now.isAfter(jamOnline) && now.isBefore(jamOffline);
    }
    
    public String getStatusText() {
        return online ? "ONLINE" : "OFFLINE";
    }
    
    public String getStatusColor() {
        return online ? "#28a745" : "#dc3545";
    }
    
    public String getInfoLengkap() {
        return String.format("Dr. %s\nSpesialisasi: %s\nPengalaman: %s\nRumah Sakit: %s\nJam Praktik: %s", 
                           nama, spesialisasi, pengalaman, rumahSakit, jamPraktik);
    }
    
    @Override
    public String toString() {
        return String.format("Dr. %s - %s (%s)", nama, spesialisasi, online ? "Online" : "Offline");
    }
} 