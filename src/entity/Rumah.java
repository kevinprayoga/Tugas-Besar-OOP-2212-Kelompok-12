package entity;

import util.UtilityTool;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import java.util.ArrayList;

public class Rumah {
    private Sim owner;
    private BufferedImage image;

    private Dimensi dimensi;
    private Posisi posisi;

    private HashMap<String, Ruangan> ruangan;
    private Matrix<Ruangan> matRoom;
    private Matrix<Integer> roomBuild; // 0 = EMPTY, 1 = BUILDABLE, 2 = BUILT
    private ArrayList<Sim> simList;

    private int timerUpgrade = -1;
    private Integer[] upgradeLokasi = new Integer[2];
    private String nameUpgrade;

    // House state
    private boolean isBuildMode = false;
    
    public Rumah(Sim owner){
        this.owner = owner;
        owner.setMyRumah(this);
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
        roomBuild = new Matrix<>(9, 9);
        ruangan = new HashMap<>(9 * 9);
        simList = new ArrayList<>();

        // Set Default Value to EMPTY, which means EMPTY SPACE
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                roomBuild.set(i, j, 0);
            }
        }

        createRuangan(4, 4, "center");
    }

    public Rumah(Sim owner, int x, int y) {
        this(owner);
    }

    public void setImage(String path) {
        image = UtilityTool.loadImage(path);
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

    public Matrix<Integer> getRoomBuild() {
        return roomBuild;
    }
    
    public Posisi getPosisi() {
        return posisi;
    }

    public void setPosisi(int x, int y) {
        posisi = new Posisi(x, y);
    }

    public Ruangan getRuangan(String roomName) {
        return ruangan.get(roomName);
    }

    public int getUpgradeTimer(){
        return timerUpgrade;
    }

    public void setUpgradeTimer(int time){
        timerUpgrade = time;
    }

    public Integer[] getUpgradeLokasi(){
        return upgradeLokasi;
    }

    public void setUpgradeLokasi(int x, int y){
        upgradeLokasi[0] = x;
        upgradeLokasi[1] = y;
    }

    public String getUpgradeNama(){
        return nameUpgrade;
    } 

    public void setUpgradeNama(String nama){
        nameUpgrade = nama;
    }

    public Ruangan createRuangan(int x, int y, String roomName) {
        Ruangan room = new Ruangan();
        setNewRuangan(x, y, room);
        roomBuild.set(y, x, 2); // jika terisi ruangan
        ruangan.put(roomName, room);
        setBuildAvailable(x, y);
        System.out.println(x + " " + y);
        return room;
    }

    public void setBuildMode(boolean isBuildMode){
        this.isBuildMode = isBuildMode;
    }

    public void setNewRuangan(int x, int y, Ruangan room) {
        matRoom.set(y, x, room);
    }

    private void setBuildAvailable(int x, int y) {
        System.out.println(x + " " + y);
        if (x > 0) {
            if (roomBuild.get(y, x - 1) == 0) {
                roomBuild.set(y, x - 1, 1);
            }
        }
        if (x < 8) {
            if (roomBuild.get(y, x + 1) == 0) {
                roomBuild.set(y, x + 1, 1);
            }
        }
        if (y > 0) {
            if (roomBuild.get(y - 1, x) == 0) {
                roomBuild.set(y - 1, x, 1);
            }
        }
        if (y < 8) {
            if (roomBuild.get(y + 1, x) == 0) {
                roomBuild.set(y + 1, x, 1);
            }
        }
    }

    public void addSim(Sim sim) {
        simList.add(sim);
        Ruangan utama = matRoom.get(4, 4);
        for (int i = 0; i < utama.getCollisionMap().getRow(); i++) {
            for (int j = 0; j < utama.getCollisionMap().getColumn(); j++) {
                if (!utama.getCollisionMap().get(i, j)) {
                    System.out.println("Posisi Sim: " + i + " " + j);
                    sim.getPosisi().changeLoc(i + 29, j + 29);
                    return;  
                }
            }
        }
    }

    public void removeSim(Sim sim) {
        simList.remove(sim);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Ruangan room = matRoom.get(i, j);
                if (room != null) {
                    room.getSimList().remove(sim);
                }
            }
        }
    }

}