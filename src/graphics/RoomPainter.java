package graphics;
import main.GamePanel;
import util.UtilityTool;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Matrix;
import entity.NonMakanan;
import entity.Ruangan;

public class RoomPainter {
    private GamePanel gamePanel;
    private Ruangan ruangan;
    private Matrix<NonMakanan> matObjek;
    private Matrix<Boolean> collisionMap;

    private final BufferedImage roomTiles = UtilityTool.loadImage("res/image/house/Room Tiles.png");

    public RoomPainter(Ruangan ruangan, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.ruangan = ruangan;
        matObjek = ruangan.getPetaBarang();
        collisionMap = ruangan.getCollisionMap();
    }

    public void draw(Graphics2D graphics2d, int x, int y) {
        graphics2d.drawImage(roomTiles, x, y, gamePanel);
        for (int i = 0; i < matObjek.getRow(); i++) {
            for (int j = 0; j < matObjek.getColumn(); j++) {
                if (matObjek.get(i, j) != null) {
                    ObjekPainter objekPainter = new ObjekPainter(matObjek.get(i, j));
                    objekPainter.draw(graphics2d, x + i * 16 + 4, y + j * 16 + 24);
                }
            }
        }

        // PlayedSims playedSims = gamePanel.getPlayedSims();
        PlayedSims playedSims = gamePanel.getPlayedSims();
        playedSims.draw(graphics2d);

        System.out.println("x: " + playedSims.getSims().getPosisi().getX() + " y: " + playedSims.getSims().getPosisi().getY());
    }
    
}
