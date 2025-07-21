package sikes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.stream.Collectors;

public class ArtikelManager {
    private static ArtikelManager instance;
    private ObservableList<Artikel> artikelList;
    
    private ArtikelManager() {
        artikelList = FXCollections.observableArrayList();
        initializeDefaultArtikel();
    }
    
    public static ArtikelManager getInstance() {
        if (instance == null) {
            instance = new ArtikelManager();
        }
        return instance;
    }
    
    private void initializeDefaultArtikel() {
        // Artikel 1 - Penyebab Diabetes
        Artikel artikel1 = new Artikel(
            "ART001",
            "Penyebab Diabetes",
            "Diabetes adalah penyakit kronis yang ditandai dengan tingginya kadar gula di dalam darah. Glukosa atau gula adalah sumber energi utama bagi tubuh. Namun, pada penderita diabetes, glukosa tidak dapat digunakan oleh tubuh dengan efektif.",
            "Diabetes adalah penyakit kronis yang ditandai dengan tingginya kadar gula di dalam darah. Glukosa atau gula adalah sumber energi utama bagi tubuh. Namun, pada penderita diabetes, glukosa tidak dapat digunakan oleh tubuh dengan efektif.\n\n" +
            "Ada beberapa jenis diabetes:\n" +
            "1. Diabetes Tipe 1: Terjadi ketika sistem kekebalan tubuh menyerang dan menghancurkan sel-sel pankreas yang memproduksi insulin.\n" +
            "2. Diabetes Tipe 2: Terjadi ketika tubuh tidak dapat menggunakan insulin dengan efektif atau tidak memproduksi cukup insulin.\n" +
            "3. Diabetes Gestasional: Terjadi selama kehamilan dan biasanya hilang setelah melahirkan.\n\n" +
            "Gejala umum diabetes meliputi:\n" +
            "- Sering haus dan buang air kecil\n" +
            "- Kelaparan yang berlebihan\n" +
            "- Penurunan berat badan yang tidak dapat dijelaskan\n" +
            "- Kelelahan\n" +
            "- Penglihatan kabur\n" +
            "- Luka yang lambat sembuh\n\n" +
            "Faktor risiko diabetes:\n" +
            "- Riwayat keluarga\n" +
            "- Obesitas\n" +
            "- Kurang aktivitas fisik\n" +
            "- Pola makan yang tidak sehat\n" +
            "- Usia di atas 45 tahun",
            "images/Rectangle 20.png",
            "Penyakit",
            "Dr. Ahmad Fauzi"
        );
        
        // Artikel 2 - Makanan untuk Penderita Diabetes
        Artikel artikel2 = new Artikel(
            "ART002",
            "Makanan untuk Penderita Diabetes",
            "Banyak orang menganggap penderita diabetes tidak bisa makan enak. Padahal, ada beragam makanan yang aman untuk penderita diabetes dan rasanya pun enak. Simak penjelasan lengkapnya di bawah ini.",
            "Banyak orang menganggap penderita diabetes tidak bisa makan enak. Padahal, ada beragam makanan yang aman untuk penderita diabetes dan rasanya pun enak.\n\n" +
            "Makanan yang baik untuk penderita diabetes:\n\n" +
            "1. Sayuran Hijau\n" +
            "- Bayam, kangkung, brokoli\n" +
            "- Kaya serat dan rendah kalori\n" +
            "- Membantu mengontrol gula darah\n\n" +
            "2. Ikan Berlemak\n" +
            "- Salmon, tuna, mackerel\n" +
            "- Kaya omega-3\n" +
            "- Baik untuk jantung\n\n" +
            "3. Kacang-kacangan\n" +
            "- Almond, walnut, kacang tanah\n" +
            "- Sumber protein dan serat\n" +
            "- Membantu mengontrol gula darah\n\n" +
            "4. Buah-buahan\n" +
            "- Apel, jeruk, pir\n" +
            "- Kaya vitamin dan serat\n" +
            "- Konsumsi dalam porsi wajar\n\n" +
            "5. Biji-bijian Utuh\n" +
            "- Oat, quinoa, beras merah\n" +
            "- Kaya serat\n" +
            "- Melepaskan gula secara perlahan\n\n" +
            "Makanan yang harus dihindari:\n" +
            "- Makanan tinggi gula\n" +
            "- Makanan berlemak jenuh\n" +
            "- Makanan olahan\n" +
            "- Minuman manis",
            "images/image 16.png",
            "Nutrisi",
            "Dr. Sarah Wijaya"
        );
        
        // Artikel 3 - Obat Diabetes
        Artikel artikel3 = new Artikel(
            "ART003",
            "5 Obat Diabetes di Apotek",
            "Obat diabetes di apotek dapat dibeli sesuai resep dari dokter. Selain mampu menurunkan kadar gula darah, obat ini juga dapat mencegah terjadinya komplikasi, khususnya pada penderita diabetes tipe 2.",
            "Obat diabetes di apotek dapat dibeli sesuai resep dari dokter. Selain mampu menurunkan kadar gula darah, obat ini juga dapat mencegah terjadinya komplikasi, khususnya pada penderita diabetes tipe 2.\n\n" +
            "Berikut adalah 5 obat diabetes yang umum digunakan:\n\n" +
            "1. Metformin\n" +
            "- Obat lini pertama untuk diabetes tipe 2\n" +
            "- Menurunkan produksi glukosa di hati\n" +
            "- Meningkatkan sensitivitas insulin\n" +
            "- Efek samping: mual, diare\n\n" +
            "2. Sulfonylurea\n" +
            "- Merangsang pankreas memproduksi insulin\n" +
            "- Contoh: Glimepiride, Gliclazide\n" +
            "- Efek samping: hipoglikemia, penambahan berat badan\n\n" +
            "3. DPP-4 Inhibitor\n" +
            "- Meningkatkan sekresi insulin\n" +
            "- Contoh: Sitagliptin, Vildagliptin\n" +
            "- Efek samping minimal\n\n" +
            "4. SGLT2 Inhibitor\n" +
            "- Mengeluarkan glukosa melalui urine\n" +
            "- Contoh: Dapagliflozin, Empagliflozin\n" +
            "- Efek samping: infeksi saluran kemih\n\n" +
            "5. Insulin\n" +
            "- Untuk diabetes tipe 1 dan tipe 2 berat\n" +
            "- Diberikan melalui suntikan\n" +
            "- Berbagai jenis: rapid-acting, long-acting\n\n" +
            "Penting untuk diingat:\n" +
            "- Selalu konsultasi dengan dokter\n" +
            "- Ikuti dosis yang ditentukan\n" +
            "- Monitor gula darah secara rutin\n" +
            "- Kombinasikan dengan diet dan olahraga",
            "images/Rectangle 50.png",
            "Pengobatan",
            "Dr. Budi Santoso"
        );
        
        artikelList.addAll(artikel1, artikel2, artikel3);
    }
    
    public ObservableList<Artikel> getArtikelList() {
        return artikelList;
    }
    
    public Artikel getArtikelById(String id) {
        return artikelList.stream()
                .filter(artikel -> artikel.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Artikel> getArtikelByKategori(String kategori) {
        return artikelList.stream()
                .filter(artikel -> artikel.getKategori().equalsIgnoreCase(kategori))
                .collect(Collectors.toList());
    }
    
    public List<Artikel> searchArtikel(String keyword) {
        return artikelList.stream()
                .filter(artikel -> artikel.getJudul().toLowerCase().contains(keyword.toLowerCase()) ||
                                 artikel.getDeskripsi().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public void addArtikel(Artikel artikel) {
        artikelList.add(artikel);
    }
    
    public void removeArtikel(Artikel artikel) {
        artikelList.remove(artikel);
    }
    
    public void updateArtikel(Artikel artikel) {
        int index = artikelList.indexOf(artikel);
        if (index != -1) {
            artikelList.set(index, artikel);
        }
    }
} 