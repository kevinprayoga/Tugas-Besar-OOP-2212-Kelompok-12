package graphics;
import main.GamePanel;
import util.UtilityTool;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Matrix;
import entity.NonMakanan;
import entity.Ruangan;
import entity.Sim;

public class RoomPainter extends Painter {
    private GamePanel gamePanel;
    private Ruangan ruangan;
    
    private int row, col;
    private int x, y;

    private Matrix<NonMakanan> matObjek;
    private Matrix<Boolean> collisionMap;
    private Matrix<Boolean> roomWall;
    private ArrayList<Sim> sims;

    private boolean isBuildMode;

    private final BufferedImage roomTiles = UtilityTool.loadImage("res/image/house/Room Tiles.png");
    private final BufferedImage availTiles = UtilityTool.loadImage("res/image/house/Hover Tiles.png");
    private final BufferedImage wall = UtilityTool.loadImage("res/image/house/Wall.png"); 

    public RoomPainter(Ruangan ruangan, int row, int col, GamePanel gamePanel, boolean isBuildMode, int x, int y) {
        this.gamePanel = gamePanel;
        this.ruangan = ruangan;
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        
        matObjek = ruangan.getPetaBarang();
        collisionMap = ruangan.getCollisionMap();
        sims = ruangan.getSimList();
        this.isBuildMode = isBuildMode;
    }

    public void draw(Graphics2D graphics2d) {
        graphics2d.drawImage(roomTiles, x, y, gamePanel);

        if (gamePanel.getHoveredObject() != null && sims.contains(gamePanel.getPlayedSims().getSims())) {
            HoveredObjectPainter hoveredObjectPainter = new HoveredObjectPainter(gamePanel.getHoveredObject(), gamePanel, x, y);
            hoveredObjectPainter.draw(graphics2d);
        }

        for (int i = 0; i < matObjek.getRow(); i++) {
            for (int j = 0; j < matObjek.getColumn(); j++) {
                if (matObjek.get(i, j) != null) {
                    ObjekPainter objekPainter = new ObjekPainter(matObjek.get(i, j), gamePanel, x + j * 16 + 4, y + i * 16 + 24);
                    objekPainter.draw(graphics2d);
                } else if (isBuildMode && !collisionMap.get(i, j) && ruangan.equals(gamePanel.getPlayedSims().getSims().getRuangan())) {
                    graphics2d.drawImage(availTiles, x + j * 16 + 4, y + i * 16 + 24, gamePanel);
                }
            }
        }

        // Drawing upper wall
        if (row == 0) {
            graphics2d.drawImage(wall, x + 4, y, gamePanel);
        } else if (gamePanel.getHouse().getMatRoom().get(row - 1, col) == null) {
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
        if (!sims.contains(gamePanel.getPlayedSims().getSims())) {
            graphics2d.drawImage(wall, x + 4, y + 16 * 6 + 4, gamePanel);
        }

        // Debugger
        // System.out.println("x: " + playedSims.getSims().getPosisi().getX() + " y: " + playedSims.getSims().getPosisi().getY());
    }
    
}
