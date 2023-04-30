package main;

import entity.Sim;
import entity.World;
import main.GamePanel.GameState;

public class GameLoader {
    private final GamePanel gamePanel;

    public GameLoader(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void loadGame(int opt) {
        gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
    }

    public void saveGame(int opt) {
    }


    public void newGame(int opt) {
        System.out.println("New game");
        
        gamePanel.reset();
        
        gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
    }
} 
