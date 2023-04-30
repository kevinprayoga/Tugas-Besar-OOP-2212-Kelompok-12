package entity;

public class Makanan {
    
    private String namaMakanan;
    private String[] bahan;
    private int kekenyangan;

    public Makanan(String namaMakanan) {
        setNamaMakanan(namaMakanan);
        
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

    public String getNamaMakanan() {
        return this.namaMakanan;
    }

    public String[] getBahan() {
        return this.bahan;
    }

    public int getKekenyangan() {
        return this.kekenyangan;
    }

    private void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    private void setBahan(String[] bahan) {
        this.bahan = bahan;
    }

    private void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }
}
