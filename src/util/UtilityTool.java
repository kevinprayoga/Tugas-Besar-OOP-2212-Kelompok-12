package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import main.GamePanel;

public class UtilityTool {
    public static int getTextWidth(String text, Graphics2D graphics2D) {
        return (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
    }

    public static int getTextHeight(String text, Graphics2D graphics2D) {
        return (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getHeight();
    }

    /* Mengembalikan ukuran pixet bagian tengah dari text terhadap layar
     * @param text text yang akan diukur dan ditampilkan ke layar
     * @param gamePanel panel game utama
     * @param graphics2D graphics2D yang akan digunakan untuk menggambar text
     * @return ukuran pixel bagian tengah dari text terhadap layar
     */
    public static int getXForCenterOfText(String text, GamePanel gamePanel, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.getScreenWidth() / 2 - length / 2;
    }

    public static BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage scaleImage(BufferedImage image, double scale) {
        int width = (int) (image.getWidth() * scale);
        int height = (int) (image.getHeight() * scale);
        BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return scaledImage;
    }

    public static BufferedImage scaleDownImage; 

    public static BufferedImage flipImageVertically(BufferedImage image) {
        BufferedImage flippedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D graphics2D = flippedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 0, image.getHeight(), image.getWidth(), 0, null);
        graphics2D.dispose();
        return flippedImage;
    }

    public static BufferedImage flipImageHorizontally(BufferedImage image) {
        BufferedImage flippedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D graphics2D = flippedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), 0, 0, image.getHeight(), null);
        graphics2D.dispose();
        return flippedImage;
    }
}
