package graphics;

import main.GamePanel;
import util.UtilityTool;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Sim;
import entity.Posisi;
import entity.Waktu;

public class PlayedSims {
    private final GamePanel gamePanel;
    private Sim sims;

    // Player settings
    private int speed;
    private int x, y;

    // Image for animation
    private BufferedImage image;
    private boolean faceDown = true, faceRight = true;
    private util.KeyHandler keyHandler;
    private int frameRate = 32;
    private int frame = 0;
    
    String file = "res/image/sims/";
    
    public PlayedSims(GamePanel gamePanel, Sim sims) {
        this.gamePanel = gamePanel;
        this.sims = sims;
        this.keyHandler = gamePanel.getKeyHandler();
        this.speed = 1;
        this.x = sims.getCurrentPosition().getX() * gamePanel.getTileSize();
        this.y = sims.getCurrentPosition().getY() * gamePanel.getTileSize();

        // Load image
        switch(sims.getC()) {
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

    public void kerja() {
    }

    public void olahraga() {

    }

    public void tidur() {

    }

    public void makan() {

    }

    public void masak() {

    }

    public void berkunjung() {

    }
    
    public void buangAir() {
    
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
        } else {
            frame++;
            if (frame >= frameRate) frame = 0;
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
        }

        // Update position of played sims
        sims.setCurrentPosition(new Point((int) x / gamePanel.getTileSize(),(int) y / gamePanel.getTileSize()));
    }

    // Getter

    public Sim getSims() {
        return sims;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}