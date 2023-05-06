package graphics;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entity.Matrix;
import entity.NonMakanan;
import entity.Posisi;
import entity.Ruangan;
import main.GamePanel;

public class HoveredObjectPainter extends Painter{
    private GamePanel gamePanel;
    private Ruangan ruangan;
    private Matrix<Boolean> collisionMap;
    private int x;
    private int y;

    private NonMakanan objek;
    private static Posisi posisi = new Posisi(0, 0);
    private util.KeyHandler keyHandler;

    public HoveredObjectPainter(NonMakanan objek, GamePanel gamePanel, int x, int y) {
        this.gamePanel = gamePanel;
        this.objek = objek;
        this.ruangan = gamePanel.getPlayedSims().getSims().getRuangan();
        this.collisionMap = ruangan.getCollisionMap();
        keyHandler = gamePanel.getKeyHandler();
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D graphics2d) {
        ObjekPainter objekPainter = new ObjekPainter(objek, gamePanel, x + posisi.getX() * 16 + 4, y + posisi.getY() * 16 + 24);
        objekPainter.draw(graphics2d);

        if (keyHandler.code == KeyEvent.VK_ENTER) {
            if (isPlaceable()) {
                gamePanel.getPlayedSims().getSims().installObject(objek, posisi);
                reset();
                gamePanel.setHoveredObject(null);
                gamePanel.getHouse().setBuildMode(false);
                gamePanel.removeAll();
            }
        }
        
        if (keyHandler.code == KeyEvent.VK_ESCAPE) {
            gamePanel.setHoveredObject(null);
            gamePanel.getHouse().setBuildMode(false);
            gamePanel.removeAll();
        }
        
        if (keyHandler.code == KeyEvent.VK_R) {
            objek.setNextOrientasi();
        }

        if (keyHandler.code == KeyEvent.VK_UP) {
            if (posisi.getY() > 0) {
                posisi.changeLoc(posisi.getX(), posisi.getY() - 1);
            }
        }

        if (keyHandler.code == KeyEvent.VK_DOWN) {
            if (posisi.getY() < 6 - objek.getDimensi().getWidth()) {
                posisi.changeLoc(posisi.getX(), posisi.getY() + 1);
            }
        }

        if (keyHandler.code == KeyEvent.VK_LEFT) {
            if (posisi.getX() > 0) {
                posisi.changeLoc(posisi.getX() - 1, posisi.getY());
            }
        }

        if (keyHandler.code == KeyEvent.VK_RIGHT) {
            if (posisi.getX() < 6 - objek.getDimensi().getLength()) {
                posisi.changeLoc(posisi.getX() + 1, posisi.getY());
            }
        }
    }

    private boolean isPlaceable() {
        for (int i = 0; i < objek.getDimensi().getWidth(); i++) {
            for (int j = 0; j < objek.getDimensi().getLength(); j++) {
                if (collisionMap.get(posisi.getY() + i, posisi.getX() + j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void reset() {
        posisi = new Posisi(0, 0);
    }
}
