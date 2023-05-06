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

    public ArrayList<Sim> getSimList() {
        return this.simList;
    }

    public boolean nonMakananExist(Posisi loc) {
        return (this.getObjek(loc) != null);
    }

    public void addObjek(Posisi loc, NonMakanan objek) {
        objek.setPosisi(loc);
        this.petaBarang.set(loc.getY(), loc.getX(), objek);

        int width = objek.getDimensi().getWidth(); // y
        int length = objek.getDimensi().getLength(); // x

        for (int i = loc.getY(); i < loc.getY() + width; i++) {
            for (int j = loc.getX(); j < loc.getX() + length; j++) {
                this.collisionMap.set(i, j, true);
            }
        }
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (petaBarang.get(i, j) != null) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (collisionMap.get(i, j) != true) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }

    }

    public void removeObjek(NonMakanan objek) {
        Posisi loc = objek.getPosisi();

        if (loc == null) {
            System.out.println("Posisi objek null");
        }


        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (petaBarang.get(i, j) != null) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }

        System.out.println("Posisi objek: " + loc.getX() + " " + loc.getY());


        this.petaBarang.set(loc.getY(), loc.getX(), null);

        int width = objek.getDimensi().getWidth(); // y
        int length = objek.getDimensi().getLength(); // x

        for (int i = loc.getY(); i < loc.getY() + width; i++) {
            for (int j = loc.getX(); j < loc.getX() + length; j++) {
                this.collisionMap.set(i, j, false);
            }
        }
    }

    public void addSim(Sim sim) {
        this.simList.add(sim);
    }

    public void removeSim(Sim sim) {
        this.simList.remove(sim);
    }
}