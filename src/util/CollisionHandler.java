package util;

import entity.Sim;
import entity.World;
import entity.Matrix;
import main.GamePanel;

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
        this.worldTiles = world.getTiles();
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
        if (x < 0 || y < 0 || x / gamePanel.getTileSize() >= world.getDimension().getWidth() - 1 || y / gamePanel.getTileSize() >= world.getDimension().getHeight() - 1) {
            return true;
        }
        if (worldTiles.get((x + 8) / gamePanel.getTileSize(), (y + 16) / gamePanel.getTileSize()) == 2) {
            return true;
        }
        if (houseMap.get((x + 8) / gamePanel.getTileSize(), (y + 8) / gamePanel.getTileSize())) {
            if (!gamePanel.getEnteredHouse()) {
                gamePanel.setHouse(world.getHouseMatrix().get((x + 8) / gamePanel.getTileSize(), (y + 8) / gamePanel.getTileSize()));
                gamePanel.setGameState(GamePanel.GameState.HOUSE_GAME_SCREEN);
                return true;
            } else {
                gamePanel.setEnteredHouse(false);
                return false;
            }
        }
        return false;
    }

    public boolean isCollideHouse(int x, int y) {
        return false;
    }
}
