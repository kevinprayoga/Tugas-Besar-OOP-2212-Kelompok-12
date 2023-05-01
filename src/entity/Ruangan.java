package entity;

// import java.util.Scanner;
import java.util.HashMap;

// masih draft
// updated: 01 May 2023 - Hans

public class Ruangan {
    private Dimensi dimensi;
    private String[][] mapRuangan;
    private HashMap<Integer, NonMakanan> objects;
    private String[] ruangSekitar;

    public Ruangan(){
        this.dimensi = new Dimensi(6,6);
        this.mapRuangan = new String[dimensi.getLength()][dimensi.getWidth()];
        this.objects = new HashMap<>(dimensi.getWidth(), dimensi.getLength());
        this.ruangSekitar = new String[] {"Atas", "Bawah", "Kiri", "Kanan"};
    
        for (String[] i : this.mapRuangan) {
            for (int j = 0; j < dimensi.getLength(); j++) {
                i[j] = "EMPTY";
            }
        }
    }
    
    public String[][] getMap(){
        return this.mapRuangan;
    }
    
    public NonMakanan getObjek(int x, int y){
        // Due to the difference between Cartesian Diagram mapping & Matrix mapping,
        // the Matrix is turned into Cartesian Diagram;
        return this.objects.get(x + (dimensi.getLength() * (y - 1)));
    }
    
    public void addObjek(Posisi loc, NonMakanan o){
        this.objects.put(loc.getX() + (dimensi.getLength() * (loc.getY() - 1)), o);
        this.mapRuangan[dimensi.getLength() - loc.getX()][loc.getY() - 1] = o.toString();
    }
    
    public void removeObjek(Posisi loc){
        this.objects.remove(loc.getX() + (dimensi.getLength() * (loc.getY() - 1)));
        this.mapRuangan[dimensi.getLength() - loc.getX()][loc.getY() - 1] = "EMPTY";
    }
    
    public void moveObjek(NonMakanan objek, Posisi loc){
        removeObjek(objek.getPosisi());
        addObjek(loc, objek);
        objek.getPosisi().changeLoc(loc.getX(), loc.getY());
    }

    public void addRuangSekitar(String loc, String namaRuangan) {
        switch (loc) {
            case "Atas", "atas":
                this.ruangSekitar[0] = namaRuangan;
                break;
            case "Bawah", "bawah":
                this.ruangSekitar[1] = namaRuangan;
                break;
            case "Kiri", "kiri":
                this.ruangSekitar[2] = namaRuangan;
                break;
            case "Kanan", "kanan":
                this.ruangSekitar[3] = namaRuangan;
                break;
        }
    }

}