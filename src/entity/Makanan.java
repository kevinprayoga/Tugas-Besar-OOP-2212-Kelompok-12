package entity;

public class Makanan extends Produk {
    
    private String[] bahan;
    private int kekenyangan;

    public Makanan(String namaMakanan) {
        super(namaMakanan);
        
        switch (namaMakanan) {
            case "Nasi Ayam":
                setBahan(new String[]{"Nasi", "Ayam"});
                setKekenyangan(16);
                break;
            case "Nasi Kari":
                setBahan(new String[]{"Nasi", "Kentang", "Wortel", "Sapi"});
                setKekenyangan(30);
                break;
            case "Susu Kacang":
                setBahan(new String[]{"Susu", "Kacang"});
                setKekenyangan(5);
                break;
            case "Tumis Sayur":
                setBahan(new String[]{"Wortel", "Bayam"});
                setKekenyangan(5);
                break;
            case "Bistik":
                setBahan(new String[]{"Kentang", "Sapi"});
                setKekenyangan(22);
                break;
        }
    }

    public String[] getBahan() {
        return this.bahan;
    }

    public int getKekenyangan() {
        return this.kekenyangan;
    }

    private void setBahan(String[] bahan) {
        this.bahan = bahan;
    }

    private void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }
}
