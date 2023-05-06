package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;

import entity.BahanMakanan;
import entity.Inventory;
import entity.Makanan;
import entity.Produk;
import entity.Sim;
import main.GamePanel;
import main.GamePanel.GameState;
import util.UtilityTool;

public class EatPanelPainter extends Painter {
    
    private GamePanel gamePanel;
    private Sim sim;
    private Inventory inventory;

    public EatPanelPainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sim = gamePanel.getPlayedSims().getSims();
        this.inventory = sim.getInventory();
    }

    public void draw(Graphics2D graphics2d) {
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (keyHandler.code == KeyEvent.VK_ESCAPE) {
            gamePanel.setEatPanelOpened(false);
        }

        class Placeholder {
            private Produk produk;
            private BufferedImage image;
            private BufferedImage placeholder = util.UtilityTool.loadImage("res/image/ui/store placeholder.png");

            public <P extends Produk> Placeholder(P produk) {
                this.produk = produk;
                this.image = util.UtilityTool.loadImage("res/image/object/" + produk.getNamaProduk() + ".png");
                if (this.image == null) {
                    System.out.println("Image not found: " + produk.getNamaProduk());
                }
            }

            public void draw(Graphics2D graphics2d, int x, int y) {
                graphics2d.drawImage(placeholder, x, y, gamePanel);
                int max = Math.max(image.getWidth(), image.getHeight());
                
                double scale = 40.0 / max;
                image = UtilityTool.scaleImage(image, scale);

                graphics2d.drawImage(image, x + 24 - (int) (image.getWidth() / 2), y + 24 - (int) (image.getHeight() / 2) , gamePanel);

                // Nama
                graphics2d.setFont(UIPainter.getGeneralFont().deriveFont(13f));
                graphics2d.setColor(ColorPalette.dark_grey);
                graphics2d.drawString(produk.getNamaProduk(), x + 56, y + 18);
                graphics2d.setColor(Color.decode("#FFE68B"));
                
                String kekenyangan;
                if (produk instanceof Makanan) {
                    kekenyangan = Integer.toString(((Makanan) produk).getKekenyangan());
                } else if (produk instanceof BahanMakanan) {
                    kekenyangan = Integer.toString(((BahanMakanan) produk).getKekenyangan());
                } else {
                    kekenyangan = "0";
                }
                
                graphics2d.setFont(UIPainter.getGeneralFont().deriveFont(10f));
                graphics2d.fillRect(x + 56, y + 25, UtilityTool.getTextWidth(kekenyangan, graphics2d) + 10, 17);
                graphics2d.setColor(Color.decode("#4E4219"));
                graphics2d.drawString(kekenyangan, x + 62, y + 38);

                // Jlabel
                JLabel label = new JLabel("Makanan");
                label.setBounds(x, y, placeholder.getWidth(), placeholder.getHeight());
                gamePanel.add(label);

                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        System.out.println("Makan");
                        try {
                            sim.makan(inventory.getItem(produk.getNamaProduk()));
                            System.out.println("Berhasil makan coyy: ");
                            gamePanel.getGameUI().setLoadingMessage("Sedang makan... ");
                            gamePanel.setGameState(GameState.LOADING_SCREEN);
                            gamePanel.leastRecentlyUsed.push(GameState.LOADING_SCREEN);
                            gamePanel.removeAll();
                        } catch (Exception e) {
                            System.out.println("Gagal makan coyy: " + produk.getNamaProduk());
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }
        }
        BufferedImage cookPanel = UtilityTool.loadImage("res/image/ui/eat panel.png");
        graphics2d.drawImage(cookPanel, 266, 271, gamePanel);

        ArrayList<String> listInventory = inventory.getInventoryList();
        
        int i = 0;
        for (String string : listInventory) {
            if (inventory.listbahanmakanan.contains(string)) {
                BahanMakanan bahanMakanan = new BahanMakanan(string);
                Placeholder placeholder = new Placeholder(bahanMakanan);
                placeholder.draw(graphics2d, 285 + (i % 2) * 230, 357 + (i / 2) * 55);
                i++;
            } else if (inventory.listmakanan.contains(string)) {
                Makanan makanan = new Makanan(string);
                Placeholder placeholder = new Placeholder(makanan);
                placeholder.draw(graphics2d, 285 + (i % 2) * 230, 357 + (i / 2) * 55);
                i++;
            }
        }
    }
}
