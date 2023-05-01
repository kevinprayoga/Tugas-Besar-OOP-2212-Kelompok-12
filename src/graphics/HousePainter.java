package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Rumah;
import main.GamePanel;
import util.UtilityTool;

public class HousePainter {
    private final GamePanel gamePanel;
    private Rumah house;
    private final BufferedImage addRoom = UtilityTool.loadImage("res/image/house/Add Room.png");
    private final BufferedImage hoverTiles = UtilityTool.loadImage("res/image/house/Hover Tiles.png");
    private final BufferedImage roomTiles = UtilityTool.loadImage("res/image/house/Room Tiles.png");
    private final BufferedImage sideUpperWall = UtilityTool.loadImage("res/image/house/Side Upper Wall.png");
    private final BufferedImage wall = UtilityTool.loadImage("res/image/house/Wall.png");

    public HousePainter(Rumah house, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.house = house;
    }

    public void draw(Graphics2D graphics2d) {
        gamePanel.setBackground(Color.decode("#211F1B"));
        // BufferedImage house.getImage();
        // Draw The Room
        for (int i = 0; i < house.getDimensi().getLength(); i++) {
            for (int j = 0; j < house.getDimensi().getWidth(); j++) {
                
            }
        }
    }
    
}
