package entity;
/*
 * FILE NAME    : Inventory.java
 * AUTHOR       : 18221045 Ivan Aldy Ganesen
 * DESKRIPSI    :
 * Kelas ini adalah implementasi dari objek Inventory yang diperlukan pada Sims.
 * Nantinya Inventory akan menyimpan barang-barang berupa turunan dari kelas parent Produk,
 * yaitu Makanan, BahanMakanan, dan NonMakanan.
*/

package entity;

import java.util.Arrays;    // untuk keperluan konversi list
import java.util.ArrayList; // untuk membuat ArrayList
import java.util.Map;       // untuk konversi entry
import java.util.HashMap;   // untuk membuat HashMap

public class Inventory {
    // definisi variabel
    private HashMap<String, Integer> inventory; // untuk inventory
    private ArrayList<String> listmakanan;      // menyimpan nama-nama produk yang merupakan Makanan
    private ArrayList<String> listbahanmakanan; // menyimpan nama-nama produk yang merupakan BahanMakanan
    private ArrayList<String> listnonmakanan;   // menyimpan nama-nama produk yang merupakan NonMakanan

    // KONSTRUKTOR
    // tidak memerlukan parameter apapun
    public Inventory() {
        // membuat sebuah HashMap baru dengan tipe key berupa String dan value berupa Integer
        inventory = new HashMap<String, Integer>();

        // membuat ArrayList berisi kemungkinan nama-nama Makanan
        listmakanan = new ArrayList<String>(Arrays.asList("Nasi Ayam", "Nasi Kari",  "Susu Kacang", "Tumis Sayur", "Bistik"));
        
        // membuat ArrayList berisi kemungkinan nama-nama BahanMakanan
        listbahanmakanan = new ArrayList<String>(Arrays.asList("Nasi", "Kentang", "Ayam", "Sapi", "Wortel", "Bayam", "Kacang", "Susu"));
        
        // membuat ArrayList berisi kemungkinan nama-nama NonMakanan
        listnonmakanan = new ArrayList<String>(Arrays.asList("KasurKingSize", "KasurQueenSize", "KasurSingleSize", "Shower", "Toilet", "KomporGas", "KomporListrik", "RakBuku", "MejaKursi", "Jam"));
    }

    public <M extends Produk> void addItem(M item) {
        /*
            * fungsi ini akan menambahkan item ke dalam inventory.
            * asumsikan parameter fungsi selalu valid, yaitu merupakan
            * subclass dari Produk (Makanan, BahanMakanan, NonMakanan)
        */

        String it = item.getNamaProduk(); // Menampung nama produk agar mempermudah statement kondisional
        if (inventory.containsKey(it)) { // sudah terdapat key di dalam HashMap
            // value bertambah menjadi 1
            inventory.replace(it, inventory.get(it), inventory.get(it) + 1);
        } else { // key belum ada di HashMap
            // dimasukkan key baru dengan value-nya berupa 1
            inventory.put(it, 1);
        }
    }

    public Object getItem(String item) {
        /*
            * fungsi ini akan mengambil item dari inventory.
            * setiap kali fungsi ini dijalankan, value untuk key yang sesuai
            * dengan nama item yang bersangkutan akan berkurang 1.
        */
        if (!listmakanan.contains(item) && !listbahanmakanan.contains(item) && !listnonmakanan.contains(item)) {
            // barang yang ingin diambil bukan Produk yang tersedia
            System.out.println("Item isn't available!");
            return null; // mengembalikan null
        } else {
            if (listmakanan.contains(item)) { // item adalah Makanan
                Makanan itemmak = new Makanan(item); // membuat Makanan baru
                if (inventory.get(item) == 1) {
                    // apabila item tersisa 1 sebelum pengambilan,
                    // key item akan dihapus dari inventory.
                    inventory.remove(item);
                } else {
                    // apabila item tersisa lebih dari 1 sebelum pengambilan,
                    // value dari key yang sesuai dengan item akan berkurang 1
                    inventory.replace(item, inventory.get(item), inventory.get(item) - 1);
                }
                return itemmak; // mengembalikan item
            } else if (listbahanmakanan.contains(item)) { // item adalan BahanMakanan
                BahanMakanan itembahmak = new BahanMakanan(item); // membuat BahanMakanan baru
                if (inventory.get(item) == 1) {
                    // apabila item tersisa 1 sebelum pengambilan,
                    // key item akan dihapus dari inventory.
                    inventory.remove(item);
                } else {
                    // apabila item tersisa lebih dari 1 sebelum pengambilan,
                    // value dari key yang sesuai dengan item akan berkurang 1
                    inventory.replace(item, inventory.get(item), inventory.get(item) - 1);
                }
                return itembahmak; // mengembalikan item
            } else { // item adalah NonMakanan
                NonMakanan itemnonmak = new NonMakanan(item); // membuat NonMakanan baru
                if (inventory.get(item) == 1) {
                    // apabila item tersisa 1 sebelum pengambilan,
                    // key item akan dihapus dari inventory.
                    inventory.remove(item);
                } else {
                    // apabila item tersisa lebih dari 1 sebelum pengambilan,
                    // value dari key yang sesuai dengan item akan berkurang 1
                    inventory.replace(item, inventory.get(item), inventory.get(item) - 1);
                }
                return itemnonmak; // mengembalikan item
            }
        }
        
    }

    public void printInventory() {
        /*
            * fungsi ini akan menampilkan isi dari inventory.
            * Jika inventory kosong, maka akan ditampilkan pesan bahwa inventory kosong.
            * Jika inventory tidak kosong, maka inventory akan ditampilkan ke layar dengan format:
            * x. y: z item(s),
            * dengan x adalah nomor barang sekarang, y adalah nama barang, dan z adalah jumlah barang.
        */

        if (inventory.size() == 0) { // inventory kosong
            System.out.printf("Inventory is empty!\n"); // pesan bahwa inventory kosong dimunculkan
        } else { // inventory tidak kosong
            int num = 1; // variabel untuk keperluan penomoran
            for (Map.Entry<String, Integer> curr : inventory.entrySet()) {
                // curr adalah pasangan String dan Integer
                if (curr.getValue() == 1) { // hanya ada satu item, item bersifat singular
                    System.out.printf("%d. %s: %d item\n", num, curr.getKey(), curr.getValue());
                } else { // terdapat lebih dari satu item, item bersifat plural
                    System.out.printf("%d. %s: %d items\n", num, curr.getKey(), curr.getValue());
                }
                num++; // penomoran terus berlanjut sampai looping berakhir
            }
        }
    }
}
