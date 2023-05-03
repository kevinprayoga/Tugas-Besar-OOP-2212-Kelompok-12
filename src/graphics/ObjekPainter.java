package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.time.OffsetDateTime;

import entity.NonMakanan;
import util.UtilityTool;

public class ObjekPainter {
    private int yOffset = 0;
    private BufferedImage image;
    private NonMakanan objek;

    public ObjekPainter(NonMakanan objek) {
        this.objek = objek;
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

    public void draw(Graphics2D graphics2d, int x, int y) {
        graphics2d.drawImage(image, x, y - yOffset, null);
    }

}
