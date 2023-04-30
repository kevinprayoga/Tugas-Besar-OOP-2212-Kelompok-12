package entity;

import java.util.Scanner;
import java.util.HashMap;

// masih draft

public class Ruangan {
    private Dimensi dimensi;
    private String[][] mapRuangan;
    private HashMap<Integer, Object> objects;

    public Ruangan(){
        this.dimensi = new Dimensi(6,6);
        this.mapRuangan = new String[dimensi.getLength()][dimensi.getWidth()];
        this.objects = new HashMap<>(dimensi.getWidth(), dimensi.getLength());
    
        for (String[] i : this.mapRuangan) {
            for (int j = 0; j < dimensi.getLength(); j++) {
                i[j] = "EMPTY";
            }
        }
    }
    
    public String[][] getMap(){
        return this.mapRuangan;
    }
    
    public Object getObjek(int x, int y){
        // Due to the difference between Cartesian Diagram mapping & Matrix mapping,
        // the Matrix is turned into Cartesian Diagram;
        return this.objects.get(x + (dimensi.getLength() * (y - 1)));
    }
    
    public void addObjek(Posisi loc, Object o){
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
        objek.setPosisi(loc);
    }

}