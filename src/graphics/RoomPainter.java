package graphics;
import main.GamePanel;
import util.UtilityTool;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Matrix;
import entity.NonMakanan;
import entity.Posisi;
import entity.Ruangan;
import entity.Sim;

public class RoomPainter {
    private GamePanel gamePanel;
    private Ruangan ruangan;
    private Posisi posisi;
    private Matrix<NonMakanan> matObjek;
    private Matrix<Boolean> collisionMap;
    private Matrix<Boolean> roomWall;
    private ArrayList<Sim> sims;

    private boolean isBuildMode;

    private final BufferedImage roomTiles = UtilityTool.loadImage("res/image/house/Room Tiles.png");
    private final BufferedImage availTiles = UtilityTool.loadImage("res/image/house/Hover Tiles.png");
    private final BufferedImage wall = UtilityTool.loadImage("res/image/house/Wall.png"); 

    public RoomPainter(Ruangan ruangan, Posisi posisi, GamePanel gamePanel, boolean isBuildMode) {
        this.gamePanel = gamePanel;
        this.ruangan = ruangan;
        this.posisi = posisi;
        matObjek = ruangan.getPetaBarang();
        collisionMap = ruangan.getCollisionMap();
        sims = ruangan.getSimList();
        this.isBuildMode = isBuildMode;
    }

    public void draw(Graphics2D graphics2d, int x, int y) {
        graphics2d.drawImage(roomTiles, x, y, gamePanel);
        for (int i = 0; i < matObjek.getRow(); i++) {
            for (int j = 0; j < matObjek.getColumn(); j++) {
                if (matObjek.get(i, j) != null) {
                    ObjekPainter objekPainter = new ObjekPainter(matObjek.get(i, j));
                    objekPainter.draw(graphics2d, x + i * 16 + 4, y + j * 16 + 24);
                } else if (isBuildMode) {
                    graphics2d.drawImage(availTiles, x + i * 16 + 4, y + j * 16 + 24, gamePanel);
                }
            }
        }

        // Drawing upper wall
        if (posisi.getY() == 0) {
            graphics2d.drawImage(wall, x + 4, y, gamePanel);
        } else if (gamePanel.getHouse().getMatRoom().get(posisi.getX(), posisi.getY() - 1) == null) {
            graphics2d.drawImage(wall, x + 4, y, gamePanel);
        }

        // Drawing Sims
        for (Sim sim : sims) {
            if (!sim.equals(gamePanel.getPlayedSims().getSims())) {
                graphics2d.drawImage(sim.getCharacter(), x + sim.getPosisi().getX() * 16 + 4, y + sim.getPosisi().getY() * 16 + 24, gamePanel);
            }
        }

        // PlayedSims playedSims = gamePanel.getPlayedSims();
        PlayedSims playedSims = gamePanel.getPlayedSims();
        playedSims.draw(graphics2d);

        // Drawing lower wall
        if (sims.contains(gamePanel.getPlayedSims().getSims())) {
            graphics2d.drawImage(wall, x + 4, y + 16 * 6, gamePanel);
        }

        // Debugger
        // System.out.println("x: " + playedSims.getSims().getPosisi().getX() + " y: " + playedSims.getSims().getPosisi().getY());
    }
    
}
