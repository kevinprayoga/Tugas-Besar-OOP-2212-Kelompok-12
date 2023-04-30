package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Inventory;
import main.GamePanel;
import util.UtilityTool;

public class InventoryPainter {
    private GamePanel gamePanel;
    private Inventory inventory;

    private BufferedImage placeholder = UtilityTool.loadImage("res/image/ui/object placeholder.png");
    private BufferedImage ammountPlaceholder = UtilityTool.loadImage("res/image/ui/object ammount.png");
    private BufferedImage upArrow = UtilityTool.loadImage("res/image/ui/inventory up.png");
    private BufferedImage downArrow = UtilityTool.loadImage("res/image/ui/inventory down.png");

    public InventoryPainter (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.inventory = gamePanel.getPlayedSims().getSims().getInventory();
    }

    public void draw(Graphics2D graphics2d) {
        class Placeholder {
            
        }
    }
}