package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Inventory;
import main.GamePanel;

public class Store {
    private GamePanel gamePanel;
    private Inventory shoppingCart;
    private Inventory inventory;

    public Store(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.shoppingCart = new Inventory();
        this.inventory = gamePanel.getPlayedSims().getSims().getInventory();
    }

    public void draw(Graphics2D graphics2d) {
        class Placeholder {
            private BufferedImage image;
            private BufferedImage placeholder = util.UtilityTool.loadImage("res/image/ui/store placeholder.png");
            private BufferedImage incButton = util.UtilityTool.loadImage("res/image/ui/store add.png");
            private int jumlah = 0;

            public Placeholder (String nama, int jumlah) {
                this.image = util.UtilityTool.loadImage("res/image/object/" + nama + ".png");
            }

            public void draw(Graphics2D graphics2d, int x, int y) {
                graphics2d.drawImage(image, x, y, gamePanel);
                
            }
        }
    }
}