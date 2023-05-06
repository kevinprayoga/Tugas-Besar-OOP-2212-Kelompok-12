package graphics;

import entity.World;
import main.GamePanel;
import util.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldPainter extends Painter {
    private World world;
    private final GamePanel gamePanel;
    private final BufferedImage dirtTiles = UtilityTool.loadImage("res/image/world/Tiles.png");
    private final BufferedImage sandTiles = UtilityTool.loadImage("res/image/world/Sand Tiles.png");
    private final BufferedImage waterTiles = UtilityTool.loadImage("res/image/world/Water Tiles.png");

    public WorldPainter(World world, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.world = world;
    }

    public void draw(Graphics2D graphics2d) {
        // Draw tiles
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                if (world.getMapWorld().get(i, j) == 0) {
                    graphics2d.drawImage(dirtTiles, i * gamePanel.getTileSize(), j * gamePanel.getTileSize(), gamePanel);
                } else if (world.getMapWorld().get(i, j) == 1) {
                    graphics2d.drawImage(sandTiles, i * gamePanel.getTileSize(), j * gamePanel.getTileSize(), gamePanel);
                } else {
                    graphics2d.drawImage(waterTiles, i * gamePanel.getTileSize(), j * gamePanel.getTileSize(), gamePanel);
                }
            }
        }

        int tempX = 0;
        int tempY = 0;

        // Drawing houses and sims from up to down
        for (int i = 0; i < world.getPerumahan().getRow(); i++) {
            for (int j = 0; j < world.getPerumahan().getColumn(); j++) {
                if (world.getPerumahan().get(i, j) != null) {
                    if (world.getPerumahan().get(i, j).getOwner().equals(gamePanel.getPlayedSims().getSims())) {
                        tempX = i * gamePanel.getTileSize();
                        tempY = j * gamePanel.getTileSize() - 20;
                        BufferedImage mark = UtilityTool.loadImage("res/image/world/House Mark.png");
                        graphics2d.drawImage(mark, i * gamePanel.getTileSize() - 10, j * gamePanel.getTileSize() - 10, gamePanel);
                    }
                    graphics2d.drawImage(world.getPerumahan().get(i, j).getImage(), i * gamePanel.getTileSize(), j * gamePanel.getTileSize() - 4, gamePanel);
                }
            }
        }
        
        // Drawing non-playable sims
        for (int i = 0; i < world.getSimList().size(); i++) {
            if (!world.getSimList().get(i).equals(gamePanel.getPlayedSims().getSims()) && world.getSimList().get(i).getCurrentPosition() == "World") {
                graphics2d.drawImage(world.getSimList().get(i).getCharacter(), world.getSimList().get(i).getPosisi().getX() * gamePanel.getTileSize(), world.getSimList().get(i).getPosisi().getY() * gamePanel.getTileSize() - 4, gamePanel);
            }
        }
        
        // PlayedSims playedSims = gamePanel.getPlayedSims();
        PlayedSims playedSims = gamePanel.getPlayedSims();
        playedSims.draw(graphics2d);

        // Drawing house mark
        BufferedImage myHouse = UtilityTool.loadImage("res/image/world/My House.png");
        graphics2d.drawImage(myHouse, tempX, tempY, gamePanel);
    }
}
