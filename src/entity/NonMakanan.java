package entity;
/*
 * FILE NAME    : NonMakanan.java
 * AUTHOR       : 18221045 Ivan Aldy Ganesen
 * DESKRIPSI    :
 * Kelas ini adalah implementasi dari objek NonMakanan yang diperlukan pada Sims.
 * NonMakanan adalah salah satu child class dari Produk, 
 * dan mencakup barang-barang yang tidak termasuk Makanan maupun Bahan Makanan,
 * yaitu Jam, KasurKingSize, KasurQueenSize, KasurSingleSize, KomporGas, KomporListrik,
 * MejaKursi, RakBuku, Shower dan Toilet.
*/

public class NonMakanan extends Produk {
    // Definisi variabel
    private Dimensi dimensi; // dimensi barang
    private int harga;       // harga dari barang
    private String aksi;     // aksi apa saja yang didukung barang
    private Posisi posisi;   // Letak NonMakanan (apabila berada di World)

    // KONSTRUKTOR
    // Asumsikan posisi hanya akan ada saat NonMakanan ditempatkan ke World
    // Parameter yang diperlukan: Tipe barang yang akan dibuat
    public NonMakanan(String type) {
        // Asumsikan type adalah nilai yang valid; tidak valid akan di-handle pada main
        super(type); // tipe ditentukan sesuai masukan
        
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

    // DAFTAR SETTER
    private void setDimensi(int x, int y) { // setter dimensi
        dimensi = new Dimensi(x, y);
    }

    private void setHarga(int har) { // setter harga
        harga = har;
    }

    private void setAksi(String aks) { // setter aksi
        aksi = aks;
    }
}