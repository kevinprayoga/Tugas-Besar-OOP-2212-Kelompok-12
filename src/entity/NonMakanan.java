/*
 * FILE NAME    : NonMakanan.java
 * AUTHOR       : 18221045 Ivan Aldy Ganesen
 * DESKRIPSI    :
 * Kelas ini adalah implementasi dari objek NonMakanan yang diperlukan pada Sims.
 * NonMakanan mencakup barang-barang yang tidak termasuk Makanan maupun Bahan Makanan,
 * yaitu Jam, KasurKingSize, KasurQueenSize, KasurSingleSize, KomporGas, KomporListrik,
 * MejaKursi, RakBuku, Shower dan Toilet.
*/

// import java.util.ArrayList;
// import java.util.Arrays;

// Tipe kelas: Public
public class NonMakanan {
    // Definisi variabel
    private Dimensi dimensi; // dimensi barang
    private int harga;       // harga dari barang
    private String aksi;     // aksi apa saja yang didukung barang
    private Posisi posisi;   // Letak NonMakanan (apabila berada di World)
    // Variabel Tambahan
    private String jenis;    // Tipe dari barang

    /* INSERT IN MAIN LATER ON, HANDLING NON-LIST ENTRIES
    final ArrayList<String> NonMakananList = new ArrayList<>(Arrays.asList("KasurKingSize", "KasurQueenSize", "KasurSingleSize",
    "Shower", "Toilet", "KomporGas", "KomporListrik", "RakBuku", "MejaKursi", "Jam"));
    */

    // KONSTRUKTOR
    // Asumsikan posisi hanya akan ada saat NonMakanan ditempatkan ke World
    // Parameter yang diperlukan: Tipe barang yang akan dibuat
    public NonMakanan(String type) {
        // Asumsikan type adalah nilai yang valid; tidak valid akan di-handle pada main
        if (type.equals("KasurKingSize")) { // barang Kasur King Size
            setDimensi(5, 2);
            setHarga(150);
            setAksi("Tidur");
        } else if (type.equals("KasurQueenSize")) { // barang Kasur Queen Size
            setDimensi(4, 2);
            setHarga(100);
            setAksi("Tidur");
        } else if (type.equals("KasurSingleSize")) { // barang Kasur Single Size
            setDimensi(4, 1);
            setHarga(50);
            setAksi("Tidur");
        } else if (type.equals("Shower")) { // barang Shower
            setDimensi(1, 1);
            setHarga(25);
            setAksi("Bath");
        } else if (type.equals("Toilet")) { // barang Toilet
            setDimensi(1, 1);
            setHarga(50);
            setAksi("Buang Air");
        } else if (type.equals("KomporGas")) { // barang Kompor Gas
            setDimensi(2, 1);
            setHarga(100);
            setAksi("Memasak");
        } else if (type.equals("KomporListrik")) { // barang Kompor Listrik
            setDimensi(1, 1);
            setHarga(200);
            setAksi("Memasak");
        } else if (type.equals("RakBuku")) { // barang Rak Buku
            setDimensi(1, 1);
            setHarga(5);
            setAksi("Read");
        } else if (type.equals("MejaKursi")) { // barang Meja Kursi
            setDimensi(3, 3);
            setHarga(50);
            setAksi("Makan");
        } else if (type.equals("Jam")) { // barang Jam
            setDimensi(1, 1);
            setHarga(10);
            setAksi("Melihat Waktu");
        }
        setJenis(type); // tipe ditentukan sesuai masukan
    }

    // DAFTAR GETTER
    public int getHarga() { // getter harga
        return harga;
    }

    public String getDimensi() { // getter dimensi
        return String.format("<%d, %d>", dimensi.getLength(), dimensi.getWidth());
    }

    public String getAksi() { // getter aksi
        return aksi;
    }

    public Posisi getPosisi() { // getter posisi
        return posisi;
    }

    public String getJenis() {
        return jenis;
    }

    // DAFTAR SETTER
    public void setDimensi(int x, int y) { // setter dimensi
        dimensi = new Dimensi(x, y);
    }

    public void setHarga(int har) { // setter harga
        harga = har;
    }

    public void setAksi(String aks) { // setter aksi
        aksi = aks;
    }

    public void setJenis(String jen) { // setter jenis
        jenis = jen;
    }
}