package util;

import java.awt.*;
import main.GamePanel;

public class UtilityTool {
    public static int getXForCenterOfText(String text, GamePanel gamePanel, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.getScreenWidth() / 2 - length / 2;
    }
}
