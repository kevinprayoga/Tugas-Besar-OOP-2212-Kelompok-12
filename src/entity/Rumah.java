package entity;

// import java.util.Scanner;
import java.util.HashMap;
import java.util.TreeSet;

public class Rumah {
    private HashMap<String, Ruangan> rooms;
    private TreeSet<String> roomNames;
    
    public Rumah(){
        // IntialCap Ruangan dalam Rumah = 6 * 6
        this.rooms = new HashMap<>(36);
        this.roomNames = new TreeSet<>();

        this.rooms.put("FIRST", new Ruangan());
        this.roomNames.add("FIRST");
    }
    
    public Ruangan getRuangan(String namaRuangan){
        return this.rooms.get(namaRuangan);
    }
    
    public void addRuangan(Ruangan r, String namaRuangan, String wasd){
        this.rooms.put(namaRuangan, new Ruangan());
        r.addRuangSekitar(wasd, namaRuangan);
    }
    
    /*
    public void removeRuangan(Posisi loc){
        this.rooms.remove(loc.getX() + (dimensi.getLength() * (loc.getY() - 1)));
        this.mapRumah[dimensi.getLength() - loc.getX()][loc.getY() - 1] = "EMPTY";
    }
    */
    
    public boolean ruanganExist(String namaRuangan) {
        return this.roomNames.contains(namaRuangan);
    }

}
