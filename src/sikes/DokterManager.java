package sikes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class DokterManager {
    private static DokterManager instance;
    private ObservableList<Dokter> dokterList;
    
    private DokterManager() {
        dokterList = FXCollections.observableArrayList();
        initializeDefaultDokter();
    }
    
    public static DokterManager getInstance() {
        if (instance == null) {
            instance = new DokterManager();
        }
        return instance;
    }
    
    private void initializeDefaultDokter() {
        // Dokter 1
        Dokter dokter1 = new Dokter(
            "DOK001",
            "Sumanto",
            "Dokter Umum",
            "10 tahun pengalaman",
            "Universitas Indonesia",
            "SIP.123456.2020",
            "RS Umum Daerah",
            "08:00 - 17:00"
        );
        dokter1.setJamOnline(LocalTime.of(8, 0));
        dokter1.setJamOffline(LocalTime.of(17, 0));
        dokter1.setRating(4.8);
        dokter1.setJumlahKonsultasi(150);
        dokter1.addSertifikasi("Dokter Umum");
        dokter1.addSertifikasi("Pertolongan Pertama");
        
        // Dokter 2
        Dokter dokter2 = new Dokter(
            "DOK002",
            "Silpi",
            "Dokter Spesialis Penyakit Dalam",
            "5 tahun pengalaman",
            "Universitas Gadjah Mada",
            "SIP.234567.2019",
            "RS Siloam",
            "09:00 - 18:00"
        );
        dokter2.setJamOnline(LocalTime.of(9, 0));
        dokter2.setJamOffline(LocalTime.of(18, 0));
        dokter2.setRating(4.6);
        dokter2.setJumlahKonsultasi(89);
        dokter2.addSertifikasi("Spesialis Penyakit Dalam");
        dokter2.addSertifikasi("Endokrinologi");
        
        // Dokter 3
        Dokter dokter3 = new Dokter(
            "DOK003",
            "Lis",
            "Dokter Spesialis Gizi",
            "8 tahun pengalaman",
            "Universitas Airlangga",
            "SIP.345678.2018",
            "RS Mitra Keluarga",
            "08:30 - 16:30"
        );
        dokter3.setJamOnline(LocalTime.of(8, 30));
        dokter3.setJamOffline(LocalTime.of(16, 30));
        dokter3.setRating(4.9);
        dokter3.setJumlahKonsultasi(234);
        dokter3.addSertifikasi("Spesialis Gizi Klinik");
        dokter3.addSertifikasi("Nutrisi Diabetes");
        
        // Dokter 4
        Dokter dokter4 = new Dokter(
            "DOK004",
            "Alesandro",
            "Dokter Spesialis Jantung",
            "15 tahun pengalaman",
            "Universitas Padjadjaran",
            "SIP.456789.2015",
            "RS Harapan Kita",
            "07:00 - 19:00"
        );
        dokter4.setJamOnline(LocalTime.of(7, 0));
        dokter4.setJamOffline(LocalTime.of(19, 0));
        dokter4.setRating(4.7);
        dokter4.setJumlahKonsultasi(312);
        dokter4.addSertifikasi("Spesialis Jantung dan Pembuluh Darah");
        dokter4.addSertifikasi("Intervensi Kardiologi");
        
        // Dokter 5
        Dokter dokter5 = new Dokter(
            "DOK005",
            "Rina",
            "Dokter Spesialis Mata",
            "12 tahun pengalaman",
            "Universitas Hasanuddin",
            "SIP.567890.2016",
            "RS Mata Cicendo",
            "08:00 - 16:00"
        );
        dokter5.setJamOnline(LocalTime.of(8, 0));
        dokter5.setJamOffline(LocalTime.of(16, 0));
        dokter5.setRating(4.5);
        dokter5.setJumlahKonsultasi(67);
        dokter5.addSertifikasi("Spesialis Mata");
        dokter5.addSertifikasi("Retina");
        
        dokterList.addAll(dokter1, dokter2, dokter3, dokter4, dokter5);
        
        // Update status online berdasarkan waktu saat ini
        updateAllDokterStatus();
    }
    
    public ObservableList<Dokter> getDokterList() {
        return dokterList;
    }
    
    public Dokter getDokterById(String id) {
        return dokterList.stream()
                .filter(dokter -> dokter.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Dokter> getDokterBySpesialisasi(String spesialisasi) {
        return dokterList.stream()
                .filter(dokter -> dokter.getSpesialisasi().toLowerCase().contains(spesialisasi.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public List<Dokter> getDokterOnline() {
        return dokterList.stream()
                .filter(Dokter::isOnline)
                .collect(Collectors.toList());
    }
    
    public List<Dokter> searchDokter(String keyword) {
        return dokterList.stream()
                .filter(dokter -> dokter.getNama().toLowerCase().contains(keyword.toLowerCase()) ||
                                 dokter.getSpesialisasi().toLowerCase().contains(keyword.toLowerCase()) ||
                                 dokter.getRumahSakit().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public List<Dokter> getDokterByRating(double minRating) {
        return dokterList.stream()
                .filter(dokter -> dokter.getRating() >= minRating)
                .collect(Collectors.toList());
    }
    
    public void addDokter(Dokter dokter) {
        dokterList.add(dokter);
    }
    
    public void removeDokter(Dokter dokter) {
        dokterList.remove(dokter);
    }
    
    public void updateDokter(Dokter dokter) {
        int index = dokterList.indexOf(dokter);
        if (index != -1) {
            dokterList.set(index, dokter);
        }
    }
    
    public void updateAllDokterStatus() {
        for (Dokter dokter : dokterList) {
            dokter.updateStatusOnline();
        }
    }
    
    public void incrementKonsultasi(String dokterId) {
        Dokter dokter = getDokterById(dokterId);
        if (dokter != null) {
            dokter.incrementKonsultasi();
        }
    }
    
    public List<Dokter> getTopRatedDokter(int limit) {
        return dokterList.stream()
                .sorted((d1, d2) -> Double.compare(d2.getRating(), d1.getRating()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    public List<Dokter> getMostActiveDokter(int limit) {
        return dokterList.stream()
                .sorted((d1, d2) -> Integer.compare(d2.getJumlahKonsultasi(), d1.getJumlahKonsultasi()))
                .limit(limit)
                .collect(Collectors.toList());
    }
} 