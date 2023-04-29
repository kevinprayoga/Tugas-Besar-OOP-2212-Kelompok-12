package entity;

import java.util.Scanner;
import java.util.HashMap;

public class Rumah {
    private Dimensi dimensi;
    private String[][] mapRumah;
    private HashMap<Integer, Ruangan> rooms;
    
    public Rumah(){
        // asumsi dimensi sama kayak 1 ruangan
        this.dimensi = new Dimensi(6,6);
        this.mapRumah = new String[dimensi.getLength()][dimensi.getWidth()];
        this.rooms = new HashMap<>(dimensi.getWidth() * dimensi.getLength());
    
        for (String[] i : this.mapRumah) {
            for (int j = 0; j < dimensi.getLength(); j++) {
                i[j] = "EMPTY";
            }
        }
    }
    
    public String[][] getMapRumah(){
        return this.mapRumah;
    }
    
    public Ruangan getRuangan(int x, int y){
        // Due to the difference between Cartesian Diagram mapping & Matrix mapping,
        // the Matrix is turned into Cartesian Diagram;

        return this.rooms.get(x + (dimensi.getLength() * (y - 1)));
    }
    
    public void addRuangan(Posisi loc, Object o){
        this.rooms.put(loc.getX() + (dimensi.getLength() * (loc.getY() - 1)), o);
        this.mapRumah[dimensi.getLength() - loc.getX()][loc.getY() - 1] = o.toString();
    }
    
    public void removeRuangan(Posisi loc){
        this.rooms.remove(loc.getX() + (dimensi.getLength() * (loc.getY() - 1)));
        this.mapRumah[dimensi.getLength() - loc.getX()][loc.getY() - 1] = "EMPTY";
    }
    
    public void moveRuangan(Ruangan ruang, Posisi loc){
        removeRuangan(Ruangan.getPosisi());
        addObjek(loc, objek);
        rooms.setPosisi(loc);
    }

}
