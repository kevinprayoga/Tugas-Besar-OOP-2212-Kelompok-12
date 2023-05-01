package entity;

// import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

// masih draft
// updated: 01 May 2023 - Hans

public class Ruangan {
    private Dimensi dimensi;
    private Matrix<NonMakanan> petaBarang;
    private Matrix<Boolean> anchor;
    private ArrayList<Sim> simList;

    public Ruangan() {
        this.dimensi = new Dimensi(6, 6);
        this.petaBarang = new Matrix<NonMakanan> (6, 6);
        this.anchor = new Matrix<Boolean> (6, 6);
        this.simList = new ArrayList<> (10);
    }

    public Matrix<NonMakanan> getPetaBarang() {
        return this.petaBarang;
    }

    public Matrix<Boolean> getAnchor() {
        return this.anchor;
    }

    public NonMakanan getObjek(Posisi loc) {
        return this.petaBarang.get(loc.getX(), loc.getY());
    }

    public boolean nonMakananExist(Posisi loc) {
        return (this.getObjek(loc) != null);
    }

    public void addObjek(Posisi loc, NonMakanan objek) {
        int a = objek.getDimensi().getLength();
        int b = objek.getDimensi().getWidth();
        
        int x = loc.getX();
        int y = loc.getY();

        switch (objek.getOrientasi()) {
            case "Down":
                for (int i = x; i < x + a; i++) {
                    for (int j = y; j < y + b; j++) {
                        this.petaBarang.set(i, j, objek);
                    }
                }
                break;
            case "Up":
                for (int i = x; i > x - a; i++) {
                    for (int j = y; j < y + b; j++) {
                        this.petaBarang.set(i, j, objek);
                    }
                }
                break;
            case "Left":
                for (int i = 0; i < a; i++) {
                    for (int j = 0; j < b; j++) {
                        this.petaBarang.set(loc.getX() + a, loc.getY() + b, objek);
                    }
                }
                break;
            case "Right":
                for (int i = 0; i < a; i++) {
                    for (int j = 0; j < b; j++) {
                        this.petaBarang.set(loc.getX() - a, loc.getY() + b, objek);
                    }
                }
                break;
                
                this.petaBarang.set(loc.getX(), loc.getY(), objek);
                this.petaBarang.set(loc.getX(), loc.getY(), objek);
        }
        
    }

    public void removeObjek(Posisi loc) {
        this.objects.remove(loc.getX() + (dimensi.getLength() * (loc.getY() - 1)));
        this.mapRuangan[dimensi.getLength() - loc.getX()][loc.getY() - 1] = "EMPTY";
    }

    public void moveObjek(NonMakanan objek, Posisi loc) {
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