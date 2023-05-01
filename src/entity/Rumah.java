package entity;

import java.util.Scanner;

import util.UtilityTool;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.TreeSet;

public class Rumah {
    private Sim owner;
    private BufferedImage image;

    private Dimensi dimensi;
    private String[][] mapRumah;
    private HashMap<Integer, Ruangan> rooms;

    // House state
    private boolean isBuildMode = false;
    
    public Rumah(Sim owner){
        this.owner = owner;
        int type = (int) (Math.random() * 11) + 1;
        switch (type) {
            case 1:
                image = UtilityTool.loadImage("res/image/world/House (1).png");
                break;
            case 2:
                image = UtilityTool.loadImage("res/image/world/House (2).png");
                break;
            case 3:
                image = UtilityTool.loadImage("res/image/world/House (3).png");
                break;
            case 4:
                image = UtilityTool.loadImage("res/image/world/House (4).png");
                break;
            case 5:
                image = UtilityTool.loadImage("res/image/world/House (5).png");
                break;
            case 6:
                image = UtilityTool.loadImage("res/image/world/House (6).png");
                break;
            case 7:
                image = UtilityTool.loadImage("res/image/world/House (7).png");
                break;
            case 8:
                image = UtilityTool.loadImage("res/image/world/House (8).png");
                break;
            case 9:
                image = UtilityTool.loadImage("res/image/world/House (9).png");
                break;
            case 10:
                image = UtilityTool.loadImage("res/image/world/House (10).png");
                break;
            case 11:
                image = UtilityTool.loadImage("res/image/world/House (11).png");
                break;
        }

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

        this.rooms.put("FIRST", new Ruangan());
        this.roomNames.add("FIRST");
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public Sim getOwner(){
        return this.owner;
    }

    public Dimensi getDimensi(){
        return this.dimensi;
    }

    public boolean isBuildMode(){
        return this.isBuildMode;
    }

    public void setBuildMode(boolean isBuildMode){
        this.isBuildMode = isBuildMode;
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
