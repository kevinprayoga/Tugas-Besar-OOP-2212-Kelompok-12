package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import entity.Waktu;
import main.GamePanel;
import util.UtilityTool;

public class ClockPainter extends Painter {
    
    private GamePanel gamePanel;

    public ClockPainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D graphics2d) {
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (keyHandler.code == KeyEvent.VK_ESCAPE || keyHandler.code == KeyEvent.VK_W || keyHandler.code == KeyEvent.VK_S || keyHandler.code == KeyEvent.VK_A || keyHandler.code == KeyEvent.VK_D) {
            gamePanel.setClockOpened(false);
        }
        
        BufferedImage clock = UtilityTool.loadImage("res/image/ui/jam.png");
        graphics2d.drawImage(clock, 329, 398, gamePanel);

        graphics2d.setFont(UIPainter.getTitleFont().deriveFont(97f));
        graphics2d.setColor(ColorPalette.dark_grey);

        String time = String.format("%02d : %02d", (Waktu.getTime() % 720) / 30 , (Waktu.getTime() % 60));
        graphics2d.drawString(time, UtilityTool.getXForCenterOfText(time, gamePanel, graphics2d), 553);

        String remainTime = "Sisa waktu " + Integer.toString(Waktu.getRemainTime());
        graphics2d.setFont(UIPainter.getTitleFont().deriveFont(30f));
        graphics2d.setColor(Color.decode("#A4454B"));
        graphics2d.drawString(remainTime, UtilityTool.getXForCenterOfText(remainTime, gamePanel, graphics2d), 589);
    }
}
