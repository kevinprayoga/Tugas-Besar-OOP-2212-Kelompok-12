package entity;
/*
 * FILE NAME    : Produk.java
 * AUTHOR       : 18221045 Ivan Aldy Ganesen
 * DESKRIPSI    :
 * Kelas ini adalah implementasi dari objek Produk yang diperlukan pada Sims.
 * Kelas ini bertindak sebagai parent class dari Makanan, NonMakanan, dan BahanMakanan.
*/

public class Produk {
    // definisi variabel
    private String namaProduk; // nama produk

    // KONSTRUKTOR
    public Produk(String namaProduk) {
        // parameter berupa nama dari produk
        setNamaProduk(namaProduk);
    }

    // GETTER NAMA PRODUK
    public String getNamaProduk() {
        return namaProduk;
    }

    // SETTER NAMA PRODUK
    public void setNamaProduk(String nampro) {
        namaProduk = nampro;
    }
}
