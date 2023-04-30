package main;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import entity.Rumah;
import entity.Sim;
import entity.World;
import graphics.HousePainter;
import graphics.PlayedSims;
import graphics.UI;
import graphics.WorldPainter;
import util.CollisionHandler;
import util.KeyHandler;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    final int tileSize = 16;
    final int maxScreenCol = 64;
    final int maxScreenRow = 64;

    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    // FPS
    private int FPS = 60;

    // System variables
    private final KeyHandler keyHandler = new KeyHandler();
    private final UI ui = new UI(this); 

    private Thread gameThread;

    // Game state
    public enum GameState {TITLE_SCREEN, LOAD_GAME_SCREEN, WORLD_GAME_SCREEN, HOUSE_GAME_SCREEN, CHARACTER_SELECTION_SCREEN, NEW_CHAR_SCREEN, HELP_SCREEN};
    private GameState gameState = GameState.TITLE_SCREEN; 
    private boolean isStoreOpened = false;
    private boolean isAddSimsAvailable = true;
    private boolean isEnteredHouse = false;

    // Game variables
    private MenuGame menuGame;
    private World world;
    private Rumah visitedHouse;
    private WorldPainter worldPainter;
    private HousePainter housePainter;
    private ArrayList<Sim> playableSims;
    private PlayedSims playedSims;
    public CollisionHandler collisionHandler;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        this.menuGame = new MenuGame(this);
        this.playableSims = new ArrayList<>();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;

        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // 1: Update information, such as character position

                // 2: Draw the screen with updated information
                repaint(); // Calls the paintComponent() method

                delta--;
            }

        }
    }

    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        
        switch (this.gameState) {
            case TITLE_SCREEN:
                ui.draw(graphics2D);
                break;
            case LOAD_GAME_SCREEN:
                ui.draw(graphics2D);
                break;
            case WORLD_GAME_SCREEN:
                worldPainter.draw(graphics2D);
                ui.draw(graphics2D);
                break;
            case HOUSE_GAME_SCREEN:
                housePainter.draw(graphics2D);
                ui.draw(graphics2D);
                break;
            case CHARACTER_SELECTION_SCREEN:
                ui.draw(graphics2D);
                break;
            case NEW_CHAR_SCREEN:
                ui.draw(graphics2D);
                break;
            case HELP_SCREEN:
                ui.draw(graphics2D);
                break;
        }
        graphics2D.dispose();
    }

    public void reset() {
        this.isStoreOpened = false;
        this.isAddSimsAvailable = true;
        this.isEnteredHouse = false;
        this.menuGame = new MenuGame(this);
        this.playableSims = new ArrayList<>();
        this.world = new World();
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getTileSize() {
        return tileSize;
    }

    public util.KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public UI getGameUI() {
        return ui;
    }

    public World getWorld() {
        return world;
    }

    public PlayedSims getPlayedSims() {
        return playedSims;
    }

    public ArrayList<Sim> getPlayableSims() {
        return playableSims;
    }

    public Rumah getHouse() {
        return visitedHouse;
    }

    public boolean getStoreOpened() {
        return isStoreOpened;
    }

    public boolean getAddSimsAvailable() {
        return isAddSimsAvailable;
    }

    public boolean getEnteredHouse() {
        return isEnteredHouse;
    }

    // Setter

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setWorld(World world) {
        this.world = world;
        worldPainter = new WorldPainter(world, this); 
        }


    public void setPlayedSims(PlayedSims playedSims) {
        this.playedSims = playedSims;
        collisionHandler = new CollisionHandler(this, playedSims.getSims());
    }

    public void setHouse(Rumah house) {
        this.visitedHouse = house;
        housePainter = new HousePainter(house, this);
    }

    public void setStoreOpened(boolean isStoreOpened) {
        this.isStoreOpened = isStoreOpened;
    }

    public void setAddSimsAvailable(boolean isAddSimsAvailable) {
        this.isAddSimsAvailable = isAddSimsAvailable;
    }

    public void setEnteredHouse(boolean isEnteredHouse) {
        this.isEnteredHouse = isEnteredHouse;
    }

    // Adder
    public void addPlayableSims(Sim sim) {
        this.playableSims.add(sim);
        world.addSim(sim);
    }
}
