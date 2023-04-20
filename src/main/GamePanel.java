package main;

import java.awt.*;
import javax.swing.*;

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
    enum GameState {TITLE_SCREEN, LOAD_GAME_SCREEN, WORLD_GAME_SCREEN, HOUSE_GAME_SCREEN, CHARACTER_SELECTION_SCREEN};
    private GameState gameState = GameState.TITLE_SCREEN; 

    // Player default position
    private int playerX = screenWidth / 2;
    private int playerY = screenHeight / 2;
    private int playerSpeed = 2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
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
                update();

                // 2: Draw the screen with updated information
                repaint(); // Calls the paintComponent() method

                delta--;
            }

        }
    }

    public void update() {
        if (keyHandler.upPressed) {
            playerY -= playerSpeed;
        }

        if (keyHandler.downPressed) {
            playerY += playerSpeed;
        }

        if (keyHandler.leftPressed) {
            playerX -= playerSpeed;
        }

        if (keyHandler.rightPressed) {
            playerX += playerSpeed;
        }
        playerX = Math.max(0, Math.min(playerX, screenWidth - tileSize));
        playerY = Math.max(0, Math.min(playerY, screenHeight - tileSize));

        // Main program loop update <<<<< taro di sini yak untuk program utama
    }

    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        if (gameState == GameState.TITLE_SCREEN) {
            ui.draw(graphics2D);
        } else {
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(playerX, playerY, tileSize, tileSize);
        }
        graphics2D.dispose();
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
}
