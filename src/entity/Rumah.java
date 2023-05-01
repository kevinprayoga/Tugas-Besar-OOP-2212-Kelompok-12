package entity;

import util.UtilityTool;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.ArrayList;

public class Rumah {
    private Sim owner;
    private BufferedImage image;

    private Dimensi dimensi;

    private HashMap<String, Ruangan> ruangan;
    private Matrix<Ruangan> matRoom;
    private Posisi posisi;
    private ArrayList<Sim> simList;

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

        dimensi = new Dimensi(9, 9);
        matRoom = new Matrix<>(9, 9);
        ruangan = new HashMap<>(9 * 9);

        createRuangan(4, 4, "center");
        owner.getInventory().addItem(new NonMakanan("KasurSingleSize"));
        owner.getInventory().addItem(new NonMakanan("Toilet"));
        owner.getInventory().addItem(new NonMakanan("KomporGas"));
        owner.getInventory().addItem(new NonMakanan("MejaKursi"));
        owner.getInventory().addItem(new NonMakanan("Jam"));
    }

    public BufferedImage getImage(){
        return image;
    }

    public Sim getOwner(){
        return owner;
    }

    public Dimensi getDimensi(){
        return dimensi;
    }

    public boolean isBuildMode(){
        return isBuildMode;
    }

    public Matrix<Ruangan> getMatRoom() {
        return matRoom;
    }

    public ArrayList<Sim> getSimList() {
        return simList;
    }

    public Posisi getPosisi(World world) {
        posisi.setAbsis(world.getPerumahan().getRow());
        posisi.setOrdinat(world.getPerumahan().getColumn());
        return posisi;
    }

    public Ruangan getRuangan(String roomName) {
        return ruangan.get(roomName);
    }

    public Ruangan createRuangan(int x, int y, String roomName) {
        Ruangan room = new Ruangan();
        setNewRuangan(x, y, room);
        ruangan.put(roomName, room);
        return room;
    }

    public void setBuildMode(boolean isBuildMode){
        this.isBuildMode = isBuildMode;
    }

    public void setNewRuangan(int x, int y, Ruangan room) {
        matRoom.set(x, y, room);
    }

    public void addSim(Sim sim) {
        simList.add(sim);
    }

    public void removeSim(Sim sim) {
        simList.remove(sim);
    }

}