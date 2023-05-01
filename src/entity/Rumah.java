package entity;

import java.util.Scanner;

import util.UtilityTool;

import java.awt.image.BufferedImage;
import java.util.HashMap;

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

        return this.rooms.get(x + (dimensi.getLength() * (y - 1)));
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
