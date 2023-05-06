package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;

import entity.Makanan;
import entity.Sim;
import main.GamePanel;
import main.GamePanel.GameState;
import util.UtilityTool;

public class CookingPainter {
    private GamePanel gamePanel;
    private Sim sim;

    String[] listMakanan = {
        "Nasi Ayam",
        "Nasi Kari",
        "Susu Kacang",
        "Tumis Sayur",
        "Bistik"
    };

    public CookingPainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sim = gamePanel.getPlayedSims().getSims();
    }

    public void draw(Graphics2D graphics2d) {
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (keyHandler.code == KeyEvent.VK_ESCAPE) {
            gamePanel.setCookingOpened(false);
        }

        class Placeholder {
            private Makanan produk;
            private BufferedImage image;
            private BufferedImage placeholder = util.UtilityTool.loadImage("res/image/ui/store placeholder.png");

            public Placeholder (Makanan produk) {
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
                graphics2d.setFont(UI.getGeneralFont().deriveFont(13f));
                graphics2d.setColor(ColorPalette.dark_grey);
                graphics2d.drawString(produk.getNamaProduk(), x + 56, y + 18);
                graphics2d.setColor(Color.decode("#FFE68B"));
                
                String[] bahan = produk.getBahan();
                String bahanString = "";
                for (int i = 0; i < bahan.length; i++) {
                    bahanString += bahan[i];
                    if (i != bahan.length - 1) {
                        bahanString += ", ";
                    }
                }
                
                graphics2d.setFont(UI.getGeneralFont().deriveFont(10f));
                graphics2d.fillRect(x + 56, y + 25, UtilityTool.getTextWidth(bahanString, graphics2d) + 10, 17);
                graphics2d.setColor(Color.decode("#4E4219"));
                graphics2d.drawString(bahanString, x + 62, y + 38);

                // Jlabel
                JLabel label = new JLabel("Makan");
                label.setBounds(x, y, placeholder.getWidth(), placeholder.getHeight());
                gamePanel.add(label);

                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        System.out.println("Makan");
                        try {
                            // sim.masak(produk);
                            System.out.println("Berhasil bikin coyy: ");
                            gamePanel.getGameUI().setLoadingMessage("Sedang memasak... ");
                            gamePanel.setGameState(GameState.LOADING_SCREEN);
                            gamePanel.leastRecentlyUsed.push(GameState.LOADING_SCREEN);
                            gamePanel.removeAll();
                        } catch (Exception e) {
                            System.out.println("Gagal bikin coyy: " + produk.getNamaProduk());
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }
        }
        BufferedImage cookPanel = UtilityTool.loadImage("res/image/ui/cooking panel.png");
        graphics2d.drawImage(cookPanel, 266, 296, gamePanel);

        ArrayList<String> listProduk = new ArrayList<>(Arrays.asList(listMakanan));
        for (int i = 0; i < listProduk.size(); i++) {
            Placeholder placeholder = new Placeholder(new Makanan(listProduk.get(i)));
            placeholder.draw(graphics2d, 286 + (i % 2) * 230, 382 + (i / 2) * 55);
        }
    }
}
