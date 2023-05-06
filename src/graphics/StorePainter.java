package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;

import entity.Produk;
import entity.Sim;
import entity.NonMakanan;
import entity.BahanMakanan;
import main.GamePanel;
import util.UtilityTool;

public class StorePainter extends Painter {
    private GamePanel gamePanel;
    private Sim sim;
    
    // List of BahanMakanan and NonMakanan
    // BahanMakanan
    String[] listBahanMakanan = {
        "Nasi",
        "Kentang",
        "Ayam",
        "Sapi",
        "Wortel",
        "Bayam",
        "Kacang",
        "Susu"
    };
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

    public StorePainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sim = gamePanel.getPlayedSims().getSims();
    }

    public void draw(Graphics2D graphics2d) {
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (keyHandler.code == KeyEvent.VK_ESCAPE) {
            gamePanel.setStoreOpened(false);
        }

        class Placeholder {
            private Produk produk;
            private BufferedImage image;
            private BufferedImage placeholder = util.UtilityTool.loadImage("res/image/ui/store placeholder.png");

            public Placeholder (Produk produk) {
                this.produk = produk;
                this.image = util.UtilityTool.loadImage("res/image/object/" + produk.getNamaProduk() + ".png");
                if (image == null) {
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
                graphics2d.setColor(Color.decode("#A2CA93"));
                graphics2d.setFont(UIPainter.getGeneralFont().deriveFont(11f));
                
                
                if (produk instanceof BahanMakanan) {
                    // Harga
                    graphics2d.fillRect(x + 56, y + 25, UtilityTool.getTextWidth(Integer.toString(((BahanMakanan) produk).getHarga()), graphics2d) + 10, 17);
                    graphics2d.setColor(Color.decode("#3E814D"));
                    graphics2d.drawString(Integer.toString(((BahanMakanan) produk).getHarga()), x + 60, y + 37);
                } else if (produk instanceof NonMakanan) {
                    // Harga
                    graphics2d.fillRect(x + 56, y + 25, UtilityTool.getTextWidth(Integer.toString(((NonMakanan) produk).getHarga()), graphics2d) + 10, 17);
                    graphics2d.setColor(Color.decode("#3E814D"));
                    graphics2d.drawString(Integer.toString(((NonMakanan) produk).getHarga()), x + 60, y + 37);
                }

                // Jlabel
                JLabel label = new JLabel("Buy");
                label.setBounds(x, y, placeholder.getWidth(), placeholder.getHeight());
                gamePanel.add(label);

                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        System.out.println("Buy");
                        try {
                            sim.beliObjek(produk);
                            System.out.println("Berhasil beli coyy: " + produk.getNamaProduk());
                            System.out.println(sim.getTimerPembelian());
                        } catch (Exception e) {
                            System.out.println("Gagal beli coyy: " + produk.getNamaProduk());
                            System.out.println(e.getMessage());
                        }
                    }
                });
            }
        }

        BufferedImage storePage = util.UtilityTool.loadImage("res/image/ui/store page.png");
        graphics2d.drawImage(storePage, 266, 109, gamePanel);

        // combined list of BahanMakanan and NonMakanan
        ArrayList<String> listProduk = new ArrayList<String>(Arrays.asList(listBahanMakanan));
        listProduk.addAll(Arrays.asList(listNonMakanan));

        // Display Produk
        for (int i = 0; i < listProduk.size(); i++) {
            Produk produk = createProduk(listProduk.get(i));
            Placeholder placeholder = new Placeholder(produk);
            placeholder.draw(graphics2d, 285 + (i % 2) * 230, 195 + (i / 2) * 55);
        }
    }

    public Produk createProduk(String nama) {

        ArrayList<String> listBahanMakananArray = new ArrayList<String>(Arrays.asList(listBahanMakanan));
        ArrayList<String> listNonMakananArray = new ArrayList<String>(Arrays.asList(listNonMakanan));

        if (listBahanMakananArray.contains(nama)) {
            return new BahanMakanan(nama);
        } else if (listNonMakananArray.contains(nama)) {
            return new NonMakanan(nama);
        } else {
            return null;
        }
    }
}
