package graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.House;
import main.GamePanel;

public class HousePainter {
    private final GamePanel gamePanel;
    private House house;

    public HousePainter(House house, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.house = house;
    }

    public void draw(Graphics2D graphics2d) {
        gamePanel.setBackground(Color.decode("#211F1B"));
    }
    
}
