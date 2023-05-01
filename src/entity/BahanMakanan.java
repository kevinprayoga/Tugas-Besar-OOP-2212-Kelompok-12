package entity;
public class BahanMakanan extends Produk {

    private int kekenyangan;
    private int harga;

    public BahanMakanan(String namaBahan) {
        super(namaBahan);
        
        switch (namaBahan) {
            case "Nasi":
                setHarga(5);
                setKekenyangan(5);
                break;
            case "Kentang":
                setHarga(3);
                setKekenyangan(4);
                break;
            case "Ayam":
                setHarga(10);
                setKekenyangan(8);
                break;
            case "Sapi":
                setHarga(12);
                setKekenyangan(15);
                break;
            case "Wortel":
                setHarga(3);
                setKekenyangan(2);
                break;
            case "Bayam":
                setHarga(3);
                setKekenyangan(2);
                break;
            case "Kacang":
                setHarga(2);
                setKekenyangan(2);
                break;
            case "Susu":
                setHarga(2);
                setKekenyangan(1);
                break;
        }
    }

    public int getKekenyangan() {
        return this.kekenyangan;
    }

    public int getHarga() {
        return this.harga;
    }

    private void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }

    private void setHarga(int harga) {
        this.harga = harga;
    }
}
