package util;

import entity.Sim;
import entity.World;

import java.util.Arrays;

import javax.swing.plaf.nimbus.State;

import entity.Matrix;
import entity.Posisi;
import entity.Rumah;
import main.GamePanel;
import main.GamePanel.GameState;

public class CollisionHandler {
    private final GamePanel gamePanel;
    private Sim sims;
    private World world;
    private Matrix<Integer> worldTiles;
    private Matrix<Boolean> houseMap;
    private Matrix<Boolean> roomMap;

    private int roomX = 4, roomY = 4;

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
        if (x < 0 || y < 0 || x / gamePanel.getTileSize() >= world.getWidth() - 1 || y / gamePanel.getTileSize() >= world.getLength() - 1) {
            return true;
        }
        if (worldTiles.get((x + 8) / gamePanel.getTileSize(), (y + 16) / gamePanel.getTileSize()) == 2) {
            return true;
        }
        if (houseMap.get((x + 8) / gamePanel.getTileSize(), (y + 8) / gamePanel.getTileSize())) {

            try {
                Rumah visited = world.getPerumahan().get((x + 8) / gamePanel.getTileSize(), (y + 8) / gamePanel.getTileSize());
                sims.berkunjung(visited);
                
                gamePanel.getGameUI().setLoadingMessage("Sedang berkunjung... ");
                gamePanel.setGameState(GameState.LOADING_SCREEN);
                gamePanel.leastRecentlyUsed.push(GameState.LOADING_SCREEN);
                System.out.println("Sedang berkunjung... ");

                // Removing the sim from world
                world.getSimList().remove(sims);
    
                // Setting game to the house screen
                gamePanel.setHouse(visited);
                gamePanel.setGameState(GamePanel.GameState.HOUSE_GAME_SCREEN);
                gamePanel.leastRecentlyUsed.push(GamePanel.GameState.HOUSE_GAME_SCREEN);
                gamePanel.isHouseSelected = true;
                
                // Moving sim
                roomX = 4; roomY = 4;
                sims.setCurrentPosition("Rumah");
                sims.setCurrentHouse(world.getPerumahan().get((x + 8) / gamePanel.getTileSize(), (y + 8) / gamePanel.getTileSize()));
                sims.setRuangan(visited.getMatRoom().get(roomX, roomY));
                gamePanel.getHouse().addSim(sims);
                gamePanel.getHouse().getMatRoom().get(roomX, roomY).addSim(sims);
                gamePanel.getPlayedSims().reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return true;
        }
        
        return false;
    }

    public boolean isCollideHouse(int x, int y) {
        int initX = 64 + roomX * 100;
        int initY = 92 + roomY * 96 - 12;

        roomMap = gamePanel.getHouse().getMatRoom().get(roomY, roomX).getCollisionMap();

        if ((y - initY) / 16 < 6) {
            if (roomMap.get((x - initX) / gamePanel.getTileSize(), (y - initY) / gamePanel.getTileSize())) {
                return true;
            }
        }

        if (roomX < 8) {
            if (gamePanel.getHouse().getMatRoom().get(roomY, roomX + 1) == null) {
                if (x > initX + 80) {
                    return true;
                }
            }
        } else {
            if (x > initX + 80) {
                return true;
            }
        }

        if ((y - initY < 36 || y - initY > 67) && x > initX + 80 ) {
            return true;
        }


        if (x < initX || y < initY || x > initX + 96 || y > initY + 96) {
            if (x - initX > 32 && x - initX < 48) {
                if (y - initY < 0) {
                    roomY--;
                    if (roomY < 0) {
                        roomY = 0;
                        return true;
                    } else if (gamePanel.getHouse().getMatRoom().get(roomY, roomX) == null) {
                        roomY++;
                        return true;
                    }
                    sims.getRuangan().removeSim(sims);
                    sims.setRuangan(gamePanel.getHouse().getMatRoom().get(roomY, roomX));
                    sims.getRuangan().addSim(sims);
                    sims.setCurrentPosition("Rumah");
                    return false;
                } else {
                    roomY++;
                    if (roomY > 8) {
                        roomY = 8;
                        return true;
                    } else if (gamePanel.getHouse().getMatRoom().get(roomY, roomX) == null) {
                        roomY--;
                        return true;
                    }
                    sims.getRuangan().removeSim(sims);
                    sims.setRuangan(gamePanel.getHouse().getMatRoom().get(roomY, roomX));
                    sims.getRuangan().addSim(sims);
                    sims.setCurrentPosition("Rumah");
                    return false;
                }
            }
    
            if (y - initY > 36 && y - initY < 67) {
                if (x - initX < 0) {
                    roomX--;
                    if (roomX < 0) {
                        roomX = 0;
                        return true;
                    } else if (gamePanel.getHouse().getMatRoom().get(roomY, roomX) == null) {
                        roomX++;
                        return true;
                    }
                    sims.getRuangan().removeSim(sims);
                    sims.setRuangan(gamePanel.getHouse().getMatRoom().get(roomY, roomX));
                    sims.getRuangan().addSim(sims);
                    sims.setCurrentPosition("Rumah");
                    return false;
                } else {
                    roomX++;
                    if (roomX > 8) {
                        roomX = 8;
                        return true;
                    } else if (gamePanel.getHouse().getMatRoom().get(roomY, roomX) == null) {
                        roomX--;
                        return true;
                    }
                    sims.getRuangan().removeSim(sims);
                    sims.setRuangan(gamePanel.getHouse().getMatRoom().get(roomY, roomX));
                    sims.getRuangan().addSim(sims);
                    sims.setCurrentPosition("Rumah");
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
