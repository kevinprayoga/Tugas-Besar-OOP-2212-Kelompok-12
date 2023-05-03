package util;

import entity.Sim;
import entity.World;

import java.util.Arrays;

import entity.Matrix;
import main.GamePanel;
import main.GamePanel.GameState;

public class CollisionHandler {
    private final GamePanel gamePanel;
    private Sim sims;
    private World world;
    private Matrix<Integer> worldTiles;
    private Matrix<Boolean> houseMap;
    private Matrix<Boolean> roomMap;

    public CollisionHandler(GamePanel gamePanel, Sim sims) {
        this.gamePanel = gamePanel;
        this.sims = sims;
        this.world = gamePanel.getWorld();
        this.worldTiles = world.getMapWorld();
        this.houseMap = world.getHouseMap();
    }

    public boolean isCollide(int x, int y) {
        switch (gamePanel.getGameState()) {
            case WORLD_GAME_SCREEN:
                return isCollideWorld(x, y);
            case HOUSE_GAME_SCREEN:
                return isCollideHouse(x, y);
            default:
                return false;
        }
    }
    
    public boolean isCollideWorld(int x, int y) {
        if (gamePanel.getGameState() == GameState.WORLD_GAME_SCREEN) {
            if (x < 0 || y < 0 || x / gamePanel.getTileSize() >= world.getWidth() - 1 || y / gamePanel.getTileSize() >= world.getLength() - 1) {
                System.out.println("Collide with border");
                return true;
            }
            if (worldTiles.get((x + 8) / gamePanel.getTileSize(), (y + 16) / gamePanel.getTileSize()) == 2) {
                System.out.println("Collide with tree");
                return true;
            }
            if (houseMap.get((x + 8) / gamePanel.getTileSize(), (y + 8) / gamePanel.getTileSize())) {
                System.out.println("Collide with house");
    
                // Removing the sim from world
                world.getSimList().remove(sims);
    
                // Setting game to the house screen
                gamePanel.setHouse(world.getPerumahan().get((x + 8) / gamePanel.getTileSize(), (y + 8) / gamePanel.getTileSize()));
                gamePanel.setGameState(GamePanel.GameState.HOUSE_GAME_SCREEN);
                gamePanel.leastRecentlyUsed.push(GamePanel.GameState.HOUSE_GAME_SCREEN);
                System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
                gamePanel.isHouseSelected = true;
                
                // Moving sim
                sims.setCurrentPosition("Rumah");
                gamePanel.getHouse().addSim(sims);
                System.out.println(sims.getPosisi().getX() + ", " + sims.getPosisi().getY());
                gamePanel.getPlayedSims().reset();
                System.out.println(gamePanel.getPlayedSims().x + ", " + gamePanel.getPlayedSims().y);
                
                return true;
            }
        } else {
            
        }
        return false;
    }

    public boolean isCollideHouse(int x, int y) {
        return false;
    }
}
