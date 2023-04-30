package graphics;

import entity.World;
import main.GamePanel;
import util.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WorldPainter {
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
                if (world.getTiles().get(i, j) == 0) {
                    graphics2d.drawImage(dirtTiles, i * gamePanel.getTileSize(), j * gamePanel.getTileSize(), gamePanel);
                } else if (world.getTiles().get(i, j) == 1) {
                    graphics2d.drawImage(sandTiles, i * gamePanel.getTileSize(), j * gamePanel.getTileSize(), gamePanel);
                } else {
                    graphics2d.drawImage(waterTiles, i * gamePanel.getTileSize(), j * gamePanel.getTileSize(), gamePanel);
                }
            }
        }

        int tempX = 0;
        int tempY = 0;

        // Drawing houses and sims from up to down
        for (int i = 0; i < world.getHouseMatrix().getRow(); i++) {
            for (int j = 0; j < world.getHouseMatrix().getColumn(); j++) {
                if (world.getHouseMatrix().get(i, j) != null) {
                    if (world.getHouseMatrix().get(i, j).getOwner().equals(gamePanel.getPlayedSims().getSims())) {
                        tempX = world.getHouseMatrix().get(i, j).getPoint().getX() * gamePanel.getTileSize();
                        tempY = world.getHouseMatrix().get(i, j).getPoint().getY() * gamePanel.getTileSize() - 20;
                        BufferedImage mark = UtilityTool.loadImage("res/image/world/House Mark.png");
                        graphics2d.drawImage(mark, world.getHouseMatrix().get(i, j).getPoint().getX() * gamePanel.getTileSize() - 10, world.getHouseMatrix().get(i, j).getPoint().getY() * gamePanel.getTileSize() - 10, gamePanel);
                    }
                    graphics2d.drawImage(world.getHouseMatrix().get(i, j).getImage(), world.getHouseMatrix().get(i, j).getPoint().getX() * gamePanel.getTileSize(), world.getHouseMatrix().get(i, j).getPoint().getY() * gamePanel.getTileSize() - 4, gamePanel);
                }
            }
        }
        
        // Drawing non-playable sims
        for (int i = 0; i < world.getSims().size(); i++) {
            if (!world.getSims().get(i).equals(gamePanel.getPlayedSims().getSims()) && world.getSims().get(i).getCurrentLocation() == "World") {
                graphics2d.drawImage(world.getSims().get(i).getCharacter(), world.getSims().get(i).getCurrentPosition().getX() * gamePanel.getTileSize(), world.getSims().get(i).getCurrentPosition().getY() * gamePanel.getTileSize() - 4, gamePanel);
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
