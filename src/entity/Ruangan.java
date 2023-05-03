package entity;

import java.util.ArrayList;

public class Ruangan {
    private Dimensi dimensi;
    private Matrix<NonMakanan> petaBarang;
    private Matrix<Boolean> collisionMap;
    private ArrayList<Sim> simList;

    public Ruangan() {
        dimensi = new Dimensi(6, 6);
        petaBarang = new Matrix<NonMakanan> (dimensi.getLength(), dimensi.getWidth());
        collisionMap = new Matrix<Boolean> (6, 6);
        simList = new ArrayList<> (10);

        // Setting collision map
        for (int i = 0; i < dimensi.getLength(); i++) {
            for (int j = 0; j < dimensi.getWidth(); j++) {
                collisionMap.set(i, j, false);
            }
        }
    }

    public Matrix<NonMakanan> getPetaBarang() {
        return this.petaBarang;
    }

    public Matrix<Boolean> getCollisionMap() {
        return this.collisionMap;
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

        this.petaBarang.set(x, y, objek);

        switch (objek.getOrientasi()) {
            case "Down":
                for (int i = x; i < x + a; i++) {
                    for (int j = y; j < y + b; j++) {
                        this.collisionMap.set(i, j, true);
                    }
                }
                break;
            case "Up":
                for (int i = x; i > x - a; i++) {
                    for (int j = y; j < y - b; j++) {
                        this.collisionMap.set(i, j, true);
                    }
                }
                break;
            case "Left":
                for (int i = x; i < x + a; i++) {
                    for (int j = y; j < y - b; j++) {
                        this.collisionMap.set(i, j, true);
                    }
                }
            case "Right":
                for (int i = x; i < x - a; i++) {
                    for (int j = y; j < y + b; j++) {
                        this.collisionMap.set(i, j, true);
                    }
                }
                break;
        }

        objek.setPosisi(loc);
    }

    public void removeObjek(Posisi loc) {
        NonMakanan objek = this.getObjek(loc);

        int a = objek.getDimensi().getLength();
        int b = objek.getDimensi().getWidth();
        
        int x = loc.getX();
        int y = loc.getY();
        this.collisionMap.set(x, y, true);

        switch (objek.getOrientasi()) {
            case "Down":
                for (int i = x; i < x + a; i++) {
                    for (int j = y; j < y + b; j++) {
                        this.petaBarang.set(i, j, null);
                    }
                }
                break;
            case "Up":
                for (int i = x; i > x - a; i++) {
                    for (int j = y; j < y - b; j++) {
                        this.petaBarang.set(i, j, null);
                    }
                }
                break;
            case "Left":
                for (int i = x; i < x + a; i++) {
                    for (int j = y; j < y - b; j++) {
                        this.petaBarang.set(i, j, null);
                    }
                }
            case "Right":
                for (int i = x; i < x - a; i++) {
                    for (int j = y; j < y + b; j++) {
                        this.petaBarang.set(i, j, null);
                    }
                }
                break;
        }
        objek.setPosisi(null);
    }

    public void moveObjek(NonMakanan objek, Posisi loc) {
        removeObjek(objek.getPosisi());
        addObjek(loc, objek);
        objek.getPosisi().changeLoc(loc.getX(), loc.getY());
    }

    public void addSim(Sim sim) {
        this.simList.add(sim);
    }

    public void removeSim(Sim sim) {
        this.simList.remove(sim);
    }
}