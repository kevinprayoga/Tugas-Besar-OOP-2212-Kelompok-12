package graphics;

import main.GameLoader;
import main.GamePanel;
import main.GamePanel.GameState;
import util.UtilityTool;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import entity.Sim;
import entity.Posisi;
import entity.Waktu;

public class PlayedSims {
    private final GamePanel gamePanel;
    private Sim sims;

    // Player settings
    private int speed;
    public int x, y;

    // Image for animation
    private BufferedImage image;
    private boolean faceDown = true, faceRight = true;
    private util.KeyHandler keyHandler;
    private int frameRate = 32;
    private int frame = 0;
    
    // Sims state
    private boolean isStatic = false;
    private boolean isActionPanelOpened = false;

    String file = "res/image/sims/";
    
    public PlayedSims(GamePanel gamePanel, Sim sims) {
        this.gamePanel = gamePanel;
        this.sims = sims;
        this.keyHandler = gamePanel.getKeyHandler();
        this.speed = 1;
        this.x = sims.getPosisi().getX() * gamePanel.getTileSize();
        this.y = sims.getPosisi().getY() * gamePanel.getTileSize();

        // Load image
        switch(sims.getCharType()) {
            case 0:
                file += "bnmo/BNMO_";
                break;
            case 1:
                file += "hans/Hans_";
                break;
            case 2:
                file += "ivan/Ivan_";
                break;
            case 3:
                file += "kevin/Kevin_";
                break;
            case 4:
                file += "nicholas/Nic_";
                break;
            case 5:
                file += "ojan/Ojan_";
                break;
            case 6:
                file += "rana/Rana_";
                break;
        }
    }

    // draw
    public void draw(Graphics2D graphics2d) {
        // draw played sims
        if (keyHandler.upPressed) faceDown = false;
        if (keyHandler.downPressed) faceDown = true;
        if (keyHandler.leftPressed) faceRight = false;
        if (keyHandler.rightPressed) faceRight = true;

        if (!keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed && !keyHandler.rightPressed) {
            frame = 0;
            isStatic = true;
        } else {
            frame++;
            if (frame >= frameRate) frame = 0;
            isStatic = false;
            isActionPanelOpened = false;
        }
        
        if (faceDown) {
            if (faceRight) {
                image = UtilityTool.loadImage(file + "Down_Right (" + Integer.toString(((int) frame / 8) + 1) + ").png");
            } else {
                image = UtilityTool.loadImage(file + "Down_Left (" + Integer.toString(((int) frame / 8) + 1) + ").png");
            }
        } else {
            if (faceRight) {
                image = UtilityTool.loadImage(file + "Up_Right (" + Integer.toString(((int) frame / 8) + 1) + ").png");
            } else {
                image = UtilityTool.loadImage(file + "Up_Left (" + Integer.toString(((int) frame / 8) + 1) + ").png");
            }
        }
        
        update();
        graphics2d.drawImage(image, x, y, gamePanel.getTileSize(), gamePanel.getTileSize(), gamePanel);
        drawSimsStatus(graphics2d);

        JLabel simBox = new JLabel();
        if (isStatic) {
            simBox.setBounds(x, y, 16, 16);
            gamePanel.add(simBox);

            simBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    isActionPanelOpened = !isActionPanelOpened;
                    gamePanel.remove(simBox);
                }
            });
        } else {
            gamePanel.remove(simBox);
        }

        if (isActionPanelOpened) {
            ActionButton actionButton = new ActionButton(gamePanel);
            actionButton.drawSimsButton(graphics2d, x + 32, y);
        }
    }

    private void drawSimsStatus(Graphics2D graphics2d) {
        double simsStatus = (sims.getKekenyangan() + sims.getKesehatan() + sims.getMood()) / 3.0;

        if (simsStatus > 0.75) {
            image = UtilityTool.loadImage("res/image/sims/Sims_Green.png");
        } else if (simsStatus > 0.5) {
            image = UtilityTool.loadImage("res/image/sims/Sims_Yellow.png");
        } else if (simsStatus > 0.25) {
            image = UtilityTool.loadImage("res/image/sims/Sims_Orange.png");
        } else {
            image = UtilityTool.loadImage("res/image/sims/Sims_Red.png");
        }
        graphics2d.drawImage(image, x + 4, y - 12, 8, 12, gamePanel);
    }

    public void update() {
        int tempX = x;
        int tempY = y;

        if (keyHandler.upPressed) {
            y -= speed;
        }

        if (keyHandler.downPressed) {
            y += speed;
        }

        if (keyHandler.leftPressed) {
            x -= speed;
        }

        if (keyHandler.rightPressed) {
            x += speed;
        }

        // Check collision
        if (gamePanel.collisionHandler.isCollide(x, y)) {
            x = tempX;
            y = tempY;
        } if (gamePanel.isHouseSelected) {
            reset();
        }

        // Update position of played sims
        sims.getPosisi().changeLoc((int) x / gamePanel.getTileSize(),(int) y / gamePanel.getTileSize());
    }

    public void reset() {
        this.x = sims.getPosisi().getX() * gamePanel.getTileSize();
        this.y = sims.getPosisi().getY() * gamePanel.getTileSize();
    }

    // Getter

    public Sim getSims() {
        return sims;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosisi(Posisi posisi) {
        this.x = posisi.getX() * gamePanel.getTileSize();
        this.y = posisi.getY() * gamePanel.getTileSize();
    }
}