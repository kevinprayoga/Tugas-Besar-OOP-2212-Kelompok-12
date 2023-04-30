package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.*;

import entity.Sims;
import entity.World;
import entity.unit.Time;
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
        World world = new World();

        try {
            world.addSims(new Sims("Mamah Dedeh", 1));
            world.addSims(new Sims("oM dEDY", 3));
            world.addSims(new Sims("DUAAAAAARRRRR", 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        gamePanel.setWorld(world);
        gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
        gamePanel.setMainTime(new Time(0, 0, 0));
    }
} 
