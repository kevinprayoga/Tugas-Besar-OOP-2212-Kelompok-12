package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import entity.Inventory;
import entity.NonMakanan;
import main.GamePanel;
import util.UtilityTool;

public class InventoryPainter {
    private GamePanel gamePanel;
    private Inventory inventory;
    private static int iterator = 0;

    private BufferedImage placeholder = UtilityTool.loadImage("res/image/ui/object placeholder.png");
    private BufferedImage ammountPlaceholder = UtilityTool.loadImage("res/image/ui/object ammount.png");
    private BufferedImage infinite = UtilityTool.loadImage("res/image/ui/infinite.png");
    private BufferedImage upArrow = UtilityTool.loadImage("res/image/ui/inventory up.png");
    private BufferedImage downArrow = UtilityTool.loadImage("res/image/ui/inventory down.png");

    public InventoryPainter (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.inventory = gamePanel.getPlayedSims().getSims().getInventory();

        if (inventory.getInventoryList().size() <= 12) {
            iterator = 0;
        }
    }

    public void draw(Graphics2D graphics2d) {
        class Placeholder {
            private BufferedImage image;
            private String nama;
            private int ammount;

            public Placeholder (String nama, int ammount) {
                this.nama = nama;
                this.ammount = ammount;
                this.image = UtilityTool.loadImage("res/image/object/" + nama + ".png");
                if (image == null) {
                    image = UtilityTool.loadImage("res/image/object/" + nama + ".jpg");
                }
            }

            public void draw(Graphics2D graphics2d, int x, int y) {
                graphics2d.drawImage(placeholder, x, y, gamePanel);
                int max = Math.max(image.getWidth(), image.getHeight());

                double scale = 40.0 / max;
                image = UtilityTool.scaleImage(image, scale);

                graphics2d.drawImage(image, x + placeholder.getWidth() / 2 - image.getWidth() / 2, y + placeholder.getHeight() / 2 - image.getHeight() / 2, gamePanel);
                graphics2d.drawImage(ammountPlaceholder, x + 41, y, gamePanel);

                if (ammount > 64) {
                    graphics2d.drawImage(infinite, x + 48, y + 6, gamePanel);
                } else {
                    graphics2d.setFont(UI.getGeneralFont().deriveFont(11f));
                    graphics2d.setColor(ColorPalette.dark_grey);
                    graphics2d.drawString(Integer.toString(ammount), x + 52 - UtilityTool.getTextWidth(Integer.toString(ammount), graphics2d) / 2, y + 12);

                    // JLabel
                    JLabel label = new JLabel("Consume");
                    label.setBounds(x, y, placeholder.getWidth(), placeholder.getHeight());
                    gamePanel.add(label);

                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (inventory.listbahanmakanan.contains(nama)) {
                                // Consume
                                System.out.println("Consume");
                            } else if (inventory.listmakanan.contains(nama)) {
                                // Consume
                                System.out.println("Consume");
                            } else if (inventory.listnonmakanan.contains(nama)) {
                                System.out.println("Place");
                                gamePanel.setHoveredObject(new NonMakanan(nama));
                                gamePanel.getPlayedSims().getSims().getMyRumah().setBuildMode(true);
                            }
                            gamePanel.removeAll();
                        }
                    });
                }
            }   
        }

        ArrayList<String> inventoryList = inventory.getInventoryList();
        HashMap<String, Integer> inventorHashMap = inventory.getInventory();
        Placeholder[] placeholders = new Placeholder[12];
        for (int i = 0; i < Math.min(12, inventoryList.size() - iterator * 12); i++) {
            placeholders[i] = new Placeholder(inventoryList.get(i + iterator * 12), inventorHashMap.get(inventoryList.get(i + iterator * 12)));
            placeholders[i].draw(graphics2d, 530 + (i % 6) * 70, 885 + (i / 6) * 56);
        }

        graphics2d.drawImage(upArrow, 954, 885, gamePanel);

        JLabel upArrow = new JLabel("Up");
        upArrow.setBounds(954, 885, 20, 51);
        gamePanel.add(upArrow);

        upArrow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Up");
                if (iterator > 0) {
                    iterator--;
                }
                gamePanel.removeAll();
            }
        });

        graphics2d.drawImage(downArrow, 954, 941, gamePanel);

        JLabel downArrow = new JLabel("Down");
        downArrow.setBounds(954, 941, 20, 51);
        gamePanel.add(downArrow);

        downArrow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Down");
                if (12 < inventoryList.size() - iterator * 12) {
                    iterator++;
                }
                gamePanel.removeAll();
            }
        });
        
    }
}