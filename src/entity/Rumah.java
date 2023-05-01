package entity;

import java.util.Scanner;

import util.UtilityTool;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.ArrayList;

public class Rumah {
    private Sim owner;
    private BufferedImage image;

    private Dimensi dimensi;
    private Matrix<Ruangan> petaRuangan;
    private HashMap<String, Ruangan> ruangan;
    private ArrayList<Sim> simList;

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

        this.dimensi = new Dimensi(9, 9);
        this.petaRuangan = new Matrix(9, 9);
        this.ruangan = new HashMap<>(9 * 9);

        Ruangan center = new Ruangan();
        this.petaRuangan.set(4, 4, center);
        this.ruangan.put("Center", center);
    }

    public Matrix<Ruangan> getPetaRuangan() {
        return this.petaRuangan;
    }

    public Ruangan getRuangan(String namaRuangan) {
        return this.ruangan.get(namaRuangan);
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

    public void setBuildMode(boolean isBuildMode) {
        this.isBuildMode = isBuildMode;
    }

    public void addRuangan(Posisi loc, String namaRuangan) {
        Ruangan r = new Ruangan();
        this.petaRuangan.set(loc.getX(), loc.getY(), r);
        this.ruangan.put(namaRuangan, r);
    }

}