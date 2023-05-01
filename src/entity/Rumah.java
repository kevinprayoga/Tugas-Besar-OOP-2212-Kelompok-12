package entity;

import util.UtilityTool;

import java.awt.image.BufferedImage;


public class Rumah {
    private Sim owner;
    private BufferedImage image;

    private Dimensi dimensi;

    private Matrix<Boolean> roomMap;
    private Matrix<Ruangan> matRoom;
    private Posisi posisi;

    // House state
    private boolean isBuildMode = false;

    public Rumah(Sim owner) {
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
        roomMap = new Matrix<>(dimensi.getWidth(), dimensi.getLength());
        matRoom = new Matrix<>(dimensi.getWidth(), dimensi.getLength());

        // Set Default Value to EMPTY, which means EMPTY SPACE
        for (int i = 0; i < dimensi.getLength(); i++) {
            for (int j = 0; j < dimensi.getWidth(); j++) {
                roomMap.set(i, j, false);
            }
        }

        createRuangan(5, 5);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Sim getOwner() {
        return this.owner;
    }

    public Dimensi getDimensi() {
        return this.dimensi;
    }

    public boolean isBuildMode() {
        return this.isBuildMode;
    }

    public Matrix<Boolean> getRoomMap() {
        return roomMap;
    }

    public Matrix<Ruangan> getMatRoom() {
        return matRoom;
    }

    public Posisi getPosisi(World world) {
        posisi.setAbsis(world.getPerumahan().getRow());
        posisi.setOrdinat(world.getPerumahan().getColumn());
        return posisi;
    }

    public Ruangan getRuangan(int x, int y) {
        return this.matRoom.get(x, y);
    }

    public Ruangan createRuangan(int x, int y) {
        Ruangan room = new Ruangan();
        setNewRuangan(x, y, room);
        roomMap.set(x, y, true);
        return room;
    }

    public void setBuildMode(boolean isBuildMode){
        this.isBuildMode = isBuildMode;
    }

    public void setNewRuangan(int x, int y, Ruangan room) {
        this.matRoom.set(x, y, room);

    }

    public void addSim(Sim sim) {
        this.simList.add(sim);
    }

    public void removeSim(Sim sim) {
        this.simList.remove(sim);
    }
}