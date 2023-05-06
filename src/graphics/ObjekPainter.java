package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import entity.NonMakanan;
import main.GamePanel;
import main.GamePanel.GameState;
import util.UtilityTool;

public class ObjekPainter extends Painter {
    private int yOffset = 0;
    private GamePanel gamePanel;
    private BufferedImage image;
    private NonMakanan objek;

    private int x;
    private int y;

    private static NonMakanan clicked = null;

    class CustomButton {
        private final int sideMargin = 10;
        private final int topMargin = 4;
        private final int stroke = 3;
        private String text;

        public CustomButton(String text) {
            this.text = text;
        }

        public void draw(Graphics2D graphics2d, int x, int y) {
            graphics2d.setColor(new Color(0, 0, 0, 0.25f));
            graphics2d.fillRect(x, y, UtilityTool.getTextWidth(text, graphics2d) + 2 * (sideMargin + stroke), UtilityTool.getTextHeight(text, graphics2d) + 2 * (topMargin + stroke));
            graphics2d.setColor(Color.WHITE);
            graphics2d.fillRect(x + stroke, y + stroke, UtilityTool.getTextWidth(text, graphics2d) + 2 * sideMargin, UtilityTool.getTextHeight(text, graphics2d) + 2 * topMargin);
            graphics2d.setFont(UIPainter.getGeneralFont().deriveFont(10f));
            graphics2d.setColor(ColorPalette.dark_grey);
            graphics2d.drawString(text, x + sideMargin + stroke, y + topMargin + stroke + UtilityTool.getTextHeight(text, graphics2d));
        }

        public int getWidth(Graphics2D graphics2d) {
            return UtilityTool.getTextWidth(text, graphics2d) + 2 * (sideMargin + stroke);
        }

        public int getHeight(Graphics2D graphics2d) {
            return UtilityTool.getTextHeight(text, graphics2d) + 2 * (topMargin + stroke);
        }
    }

    public ObjekPainter(NonMakanan objek, GamePanel gamePanel, int x, int y) {
        this.objek = objek;
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        switch (objek.getNamaProduk()) {     
            case "Kasur King Size":
                if (objek.getOrientasi() == "Up" || objek.getOrientasi() == "Down") {
                    yOffset = 4;
                } else {
                    yOffset = 8;
                }
            case "Kasur Queen Size":
                if (objek.getOrientasi() == "Up" || objek.getOrientasi() == "Down") {
                    yOffset = 4;
                } else {
                    yOffset = 8;
                }
            case "Kasur Single Size":
                if (objek.getOrientasi() == "Up" || objek.getOrientasi() == "Down") {
                    yOffset = 4;
                } else {
                    yOffset = 8;
                }
            case "Shower":
                yOffset = 16;
            case "Toilet":
                if (objek.getOrientasi() == "Up" || objek.getOrientasi() == "Down") {
                    yOffset = 8;
                } else {
                    yOffset = 4;
                }
            case "Kompor Gas":
                yOffset = 4;
            case "Kompor Listrik":
                yOffset = 4;
            case "Rak Buku":
                yOffset = 8;
            case "Meja dan Kursi":
                yOffset = 4;
            case "Jam":
                yOffset = 4;
        }
        image = UtilityTool.loadImage("res/image/object/" + objek.getNamaProduk() + " " + objek.getOrientasi() + ".png");
    }

    public void draw(Graphics2D graphics2d) {
        graphics2d.drawImage(image, x, y - yOffset, null);

        JLabel label = new JLabel();
        label.setBounds(x, y, objek.getDimensi().getLength() * gamePanel.getTileSize(), objek.getDimensi().getWidth() * gamePanel.getTileSize());
        gamePanel.add(label);
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(objek.getNamaProduk() + " Clicked");
                if (gamePanel.getPlayedSims().getSims().getRuangan().getPetaBarang().contains(objek)) {
                    if (clicked == null) {
                        clicked = objek;
                    } else {
                        clicked = null;
                    }
                }
                gamePanel.remove(label);
            }
        });

        if (clicked != null) {
            if (clicked.equals(this.objek)) {

                CustomButton use = new CustomButton("Gunakan");
                use.draw(graphics2d, x - 16 - use.getWidth(graphics2d), y);
    
                JLabel useLabel = new JLabel();
                useLabel.setBounds(x - 16 - use.getWidth(graphics2d), y, use.getWidth(graphics2d), use.getHeight(graphics2d));
                gamePanel.add(useLabel);
    
                useLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        try{
                            System.out.println(objek.getNamaProduk() + " Used");
                            clicked = null;
                            // Program methods here
                            if(objek.getAksi().equals("Makan")){
                                gamePanel.setEatPanelOpened(true);
                                
                                gamePanel.setStoreOpened(false);
                                gamePanel.setWoodworkingOpened(false);
                                gamePanel.setCookingOpened(false);
                                gamePanel.setChangeJobOpened(false);
                            } else if(objek.getAksi().equals("Tidur")){
                                UIPainter.setActionText("tidur");
                            } else if(objek.getAksi().equals("Read")){
                                gamePanel.getPlayedSims().getSims().read();
                                UIPainter.setActionText("read");
                                gamePanel.getGameUI().setLoadingMessage("Sedang Membaca ... ");
                                gamePanel.setGameState(GameState.LOADING_SCREEN);
                                gamePanel.leastRecentlyUsed.push(GameState.LOADING_SCREEN);
                                System.out.println("Sedang ... ");
                                gamePanel.removeAll();
                            } else if (objek.getAksi().equals("Memasak")) {
                                gamePanel.setCookingOpened(true);
                                
                                gamePanel.setStoreOpened(false);
                                gamePanel.setWoodworkingOpened(false);
                                gamePanel.setChangeJobOpened(false);
                                gamePanel.setEatPanelOpened(false);
                            }
                        } catch (Exception ex){
                            ex.getMessage();
                        }
                        gamePanel.removeAll();
                    }
                });
    
                CustomButton pickUp = new CustomButton("Ambil");
                pickUp.draw(graphics2d, x - use.getWidth(graphics2d), y + use.getHeight(graphics2d) + 4);
    
                JLabel pickUpLabel = new JLabel();
                pickUpLabel.setBounds(x - use.getWidth(graphics2d), y + use.getHeight(graphics2d) + 4, pickUp.getWidth(graphics2d), pickUp.getHeight(graphics2d));
                gamePanel.add(pickUpLabel);
    
                pickUpLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        System.out.println(objek.getNamaProduk() + " Picked Up");
                        gamePanel.getPlayedSims().getSims().getRuangan().removeObjek(objek);
                        gamePanel.getPlayedSims().getSims().getInventory().addItem(objek);
                        clicked = null;
                        gamePanel.removeAll();
                    }
                });
            }
        }
    }

}
