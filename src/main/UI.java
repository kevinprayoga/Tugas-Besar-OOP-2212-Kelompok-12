package main;

import util.UtilityTool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import main.GamePanel.GameState;

public class UI {
    private final GamePanel gamePanel;
    private Graphics2D graphics2d;

    class RectangleButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("UI.RectangleButtonListener.actionPerformed()");
        }
    }

    public UI (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D graphics2d) {
        this.graphics2d = graphics2d;
        if (gamePanel.getGameState() == GameState.TITLE_SCREEN) {
            drawTitleScreen();
        } else if (gamePanel.getGameState() == GameState.LOAD_GAME_SCREEN) {
            drawLoadGameScreen();
        }
    }

    private void drawTitleScreen() {
        // Title
        graphics2d.setColor(Color.white);
        graphics2d.setFont(new Font("Arial", Font.BOLD, 30));
        graphics2d.drawString("Sim-Plicity", UtilityTool.getXForCenterOfText("Sim-Plicity", gamePanel, graphics2d), 50);

        // Play game button
        JButton playGameButton = new JButton("Play Game");
        gamePanel.add(playGameButton);
    }
    
    private void drawLoadGameScreen() {
        // Three load game slots

    }
}

class RectangleComponent extends JComponent {
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle box = new Rectangle(5, 10, 20, 30);
        g2.draw(box);
        g2.drawString("This is a rectangle", 5, 50);
    }
}