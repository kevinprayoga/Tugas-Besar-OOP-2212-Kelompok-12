package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;

import entity.NonMakanan;
import entity.Sim;
import main.GamePanel;
import main.GamePanel.GameState;
import util.UtilityTool;

public class WoodworkingPainter extends Painter {
    private GamePanel gamePanel;
    private Sim sim;

    String[] listNonMakanan = {
        "Kasur King Size",
        "Kasur Queen Size",
        "Kasur Single Size",
        "Shower",
        "Toilet",
        "Kompor Gas",
        "Kompor Listrik",
        "Rak Buku",
        "Meja dan Kursi",
        "Jam"
    };

    public WoodworkingPainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sim = gamePanel.getPlayedSims().getSims();
    }

    public void draw(Graphics2D graphics2d) {
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (keyHandler.code == KeyEvent.VK_ESCAPE || keyHandler.code == KeyEvent.VK_W || keyHandler.code == KeyEvent.VK_S || keyHandler.code == KeyEvent.VK_A || keyHandler.code == KeyEvent.VK_D) {
            gamePanel.setWoodworkingOpened(false);
        }

        class Placeholder {
            private NonMakanan produk;
            private BufferedImage image;
            private BufferedImage placeholder = util.UtilityTool.loadImage("res/image/ui/store placeholder.png");

            public Placeholder (NonMakanan produk) {
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
                graphics2d.setColor(Color.decode("#B4977D"));
                
                graphics2d.fillRect(x + 56, y + 25, UtilityTool.getTextWidth(Integer.toString(((NonMakanan) produk).getHarga()), graphics2d) + 10, 17);
                graphics2d.setColor(Color.decode("#4E4219"));
                graphics2d.setFont(UIPainter.getGeneralFont().deriveFont(11f));
                graphics2d.drawString(Integer.toString(produk.getHarga()), x + 62, y + 38);

                // Jlabel
                JLabel label = new JLabel("Bikin");
                label.setBounds(x, y, placeholder.getWidth(), placeholder.getHeight());
                gamePanel.add(label);

                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        System.out.println("Bikin");
                        try {
                            sim.woodworking(produk);
                            System.out.println("Berhasil bikin coyy: ");
                            gamePanel.getGameUI().setLoadingMessage("Sedang woodworking... ");
                            gamePanel.setGameState(GameState.LOADING_SCREEN);
                            gamePanel.leastRecentlyUsed.push(GameState.LOADING_SCREEN);
                            gamePanel.removeAll();

                            for(Sim s: gamePanel.getPlayableSims()){
                                s.update(produk.getHarga()/2);
                            }
                        } catch (Exception e) {
                            System.out.println("Gagal bikin coyy: " + produk.getNamaProduk());
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }
        }
        BufferedImage woodworkingPanel = UtilityTool.loadImage("res/image/ui/woodworking panel.png");
        graphics2d.drawImage(woodworkingPanel, 266, 296, gamePanel);

        ArrayList<String> listProduk = new ArrayList<>(Arrays.asList(listNonMakanan));
        for (int i = 0; i < listProduk.size(); i++) {
            Placeholder placeholder = new Placeholder(new NonMakanan(listProduk.get(i)));
            placeholder.draw(graphics2d, 286 + (i % 2) * 230, 382 + (i / 2) * 55);
        }

        // Jumlah
        graphics2d.setFont(UIPainter.getGeneralFont().deriveFont(14f));
        graphics2d.setColor(ColorPalette.dark_grey);
        graphics2d.drawString(Integer.toString(sim.getWood()), 324, 697);

    }
}
