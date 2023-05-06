package graphics;

import entity.Inventory;
import entity.Sim;
import main.GamePanel;
import main.GamePanel.GameState;
import util.UtilityTool;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

public class DashboardPainter extends Painter {
    private final GamePanel gamePanel;
    private Sim sims;
    private Inventory inventory;

    // Dashboard State
    private static boolean isDashboardOpen;
    private static boolean isInventoryOpen; 
    
    public DashboardPainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sims = gamePanel.getPlayedSims().getSims();
        this.inventory = sims.getInventory();
    }

    public void draw(Graphics2D graphics2D) {
        
        if (isDashboardOpen) {
            drawDashboard(graphics2D);
            if (isInventoryOpen) {
                drawInventory(graphics2D);
                InventoryPainter inventoryPainter = new InventoryPainter(gamePanel);
                inventoryPainter.draw(graphics2D);
            } else {
                drawOpenInventory(graphics2D);
            }
        } else {
            drawOpenDashboard(graphics2D);
        }

        if (gamePanel.getStoreOpened()) {
            StorePainter store = new StorePainter(gamePanel);
            store.draw(graphics2D);
        }
    }

    private void drawDashboard(Graphics2D graphics2D) {
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (keyHandler.code == java.awt.event.KeyEvent.VK_ESCAPE) {
            isDashboardOpen = false;
            gamePanel.removeAll();
        }

        // Drop Dashboard Button
        BufferedImage button = UtilityTool.loadImage("res/image/ui/drop down button.png");
        button = UtilityTool.flipImageVertically(button);
        graphics2D.drawImage(button, 8, 809, gamePanel);
    
        JLabel openButton = new JLabel("Dashboard");
        openButton.setBounds(8, 809, button.getWidth(), button.getHeight());
        gamePanel.add(openButton);
    
        openButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("Dashboard Closed");
                isDashboardOpen = false;
                gamePanel.removeAll();
            }
        });

        // Dashboard panel
        BufferedImage dashboard = UtilityTool.loadImage("res/image/ui/main dashboard panel.png");
        graphics2D.drawImage(dashboard, 8, 841, gamePanel);

        // Dashboard content
        // Build button
        BufferedImage buildButton;
        if (gamePanel.getGameState() == GameState.WORLD_GAME_SCREEN) {
            buildButton = UtilityTool.loadImage("res/image/ui/Build Button Disabled.png");
            graphics2D.drawImage(buildButton, 8, 845, gamePanel);
        } else {
            buildButton = UtilityTool.loadImage("res/image/ui/Build Button.png");
            graphics2D.drawImage(buildButton, 8, 845, gamePanel);

            JLabel buildLabel = new JLabel("Build");
            buildLabel.setBounds(8, 845, buildButton.getWidth(), buildButton.getHeight());
            gamePanel.add(buildLabel);

            buildLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    System.out.println("Build Button Clicked");
                    gamePanel.getHouse().setBuildMode(!gamePanel.getHouse().isBuildMode());
                    if (!gamePanel.getHouse().isBuildMode()) {
                        gamePanel.setHoveredObject(null);
                    }
                    gamePanel.removeAll();
                }
            });
        }

        // Store button
        BufferedImage storeButton = UtilityTool.loadImage("res/image/ui/Store Button.png");
        graphics2D.drawImage(storeButton, 8, 916, gamePanel);

        JLabel storeLabel = new JLabel("Store");
        storeLabel.setBounds(8, 916, storeButton.getWidth(), storeButton.getHeight());
        gamePanel.add(storeLabel);

        storeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("Store Button Clicked");
                gamePanel.setStoreOpened(!gamePanel.getStoreOpened());
                gamePanel.setWoodworkingOpened(false);
                gamePanel.setCookingOpened(false);
                gamePanel.setChangeJobOpened(false);
                gamePanel.setEatPanelOpened(false);
                gamePanel.removeAll();
            }
        });

        // Drawing sims
        // Background
        double simsStatus = (sims.getKekenyangan() + sims.getKesehatan() + sims.getMood()) / 3.0;
        BufferedImage simsBackground;
        if (simsStatus > 75) {
            simsBackground = UtilityTool.loadImage("res/image/ui/player backdrop green.png");
        } else if (simsStatus > 50) {
            simsBackground = UtilityTool.loadImage("res/image/ui/player backdrop yellow.png");
        } else if (simsStatus > 25) {
            simsBackground = UtilityTool.loadImage("res/image/ui/player backdrop orange.png");
        } else {
            simsBackground = UtilityTool.loadImage("res/image/ui/player backdrop red.png");
        }
        graphics2D.drawImage(simsBackground, 52, 843, gamePanel);

        // Sims
        BufferedImage simsImage = sims.getCharacter();
        simsImage = UtilityTool.scaleImage(simsImage, 10);
        graphics2D.drawImage(simsImage, 55, 849, gamePanel);

        // Sims status
        BufferedImage simsStatusImage = UtilityTool.loadImage("res/image/ui/sims status.png");
        graphics2D.drawImage(simsStatusImage, 52, 969, gamePanel);

        graphics2D.setFont(UIPainter.getTitleFont().deriveFont(12f));
        graphics2D.setColor(ColorPalette.white);
        graphics2D.drawString(sims.getCurrentActivity(), 59, 980);

        // Dashboard money panel
        BufferedImage moneyPanel = UtilityTool.loadImage("res/image/ui/dashborad money frame.png");
        graphics2D.drawImage(moneyPanel, 8, 987, gamePanel);

        graphics2D.setFont(UIPainter.getGeneralFont().deriveFont(13f));
        graphics2D.setColor(ColorPalette.dark_grey);
        graphics2D.drawString(Integer.toString(sims.getUang()), 55, 1004);

        // Kesejahteraan
        graphics2D.drawString("Mood | " + Integer.toString(sims.getMood()) + "%", 283, 870);
        graphics2D.drawString("Kekenyangan | " + Integer.toString(sims.getKekenyangan()) + "%", 283, 921);
        graphics2D.drawString("Kesehatan | " + Integer.toString(sims.getKesehatan()) + "%", 283, 972);

        class KesejahteraanBar {
            private int percentage;

            public KesejahteraanBar(int percentage) {
                this.percentage = percentage;
            }

            public void draw(Graphics2D graphics2d, int x, int y) {
                graphics2D.setColor(ColorPalette.light_grey);
                graphics2D.fillRect(x, y, 202, 15);
                if (percentage > 75) {
                    graphics2D.setColor(Color.decode("#82E75B"));
                } else if (percentage > 50) {
                    graphics2D.setColor(Color.decode("#F4F452"));
                } else if (percentage > 25) {
                    graphics2D.setColor(Color.decode("#F59942"));
                } else {
                    graphics2D.setColor(Color.decode("#F54242"));
                }
                graphics2D.fillRect(x, y,(int) (202 * percentage / 100), 15);
            }
        }

        KesejahteraanBar mood = new KesejahteraanBar(sims.getMood());
        KesejahteraanBar kekenyangan = new KesejahteraanBar(sims.getKekenyangan());
        KesejahteraanBar kesehatan = new KesejahteraanBar(sims.getKesehatan());

        mood.draw(graphics2D, 283, 876);
        kekenyangan.draw(graphics2D, 283, 927);
        kesehatan.draw(graphics2D, 283, 978);
    }
    
    private void drawOpenDashboard(Graphics2D graphics2D) {
        
        BufferedImage button = UtilityTool.loadImage("res/image/ui/drop down button.png");
        graphics2D.drawImage(button, 8, 990, gamePanel);

        JLabel openButton = new JLabel("Dashboard");
        openButton.setBounds(8, 990, button.getWidth(), button.getHeight());
        gamePanel.add(openButton);
        
        openButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("Dashboard Opened");
                isDashboardOpen = true;
                gamePanel.removeAll();
            }
        });
    }

    private void drawInventory(Graphics2D graphics2D) {
        BufferedImage inventoryPanel = UtilityTool.loadImage("res/image/ui/inventory panel.png");
        graphics2D.drawImage(inventoryPanel, 508, 841, gamePanel);

        BufferedImage inventoryButton = UtilityTool.loadImage("res/image/ui/drop right button.png");
        inventoryButton = UtilityTool.flipImageHorizontally(inventoryButton);
        graphics2D.drawImage(inventoryButton, 990, 841, gamePanel);

        JLabel button = new JLabel("Close Button");
        button.setBounds(990, 841, 26, 175);
        gamePanel.add(button);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent awt) {
                System.out.println("Inventory Closed");
                isInventoryOpen = false;
                gamePanel.removeAll();
            }
        });
    }

    private void drawOpenInventory(Graphics2D graphics2D) {
        BufferedImage inventoryButton = UtilityTool.loadImage("res/image/ui/drop right button.png");
        graphics2D.drawImage(inventoryButton, 508, 841, gamePanel);

        JLabel button = new JLabel("Inventory");
        button.setBounds(508, 841, inventoryButton.getWidth(), inventoryButton.getHeight());
        gamePanel.add(button);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("Inventory Opened");
                isInventoryOpen = true;
                gamePanel.removeAll();
            }
        });
    }
}
