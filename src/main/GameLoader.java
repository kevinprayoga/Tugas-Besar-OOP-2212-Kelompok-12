package main;

import java.util.Arrays;

import entity.Sim;
import entity.World;
import main.GamePanel.GameState;

public class GameLoader {
    private static GamePanel gamePanel;
    private static int fileOpt;

    public static void loadGame(int opt) {
        fileOpt = opt;
        gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
        gamePanel.leastRecentlyUsed.push(GameState.CHARACTER_SELECTION_SCREEN);
        System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
    }

    public static void saveGame() {
        
    }


    public static void newGame(int opt) {
        fileOpt = opt;
        System.out.println("New game");
        
        gamePanel.reset();
        
        gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
        gamePanel.leastRecentlyUsed.push(GameState.CHARACTER_SELECTION_SCREEN);
        System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
    }

    public static void setGamePanel(GamePanel gamePanel) {
        GameLoader.gamePanel = gamePanel;
    }
} 
