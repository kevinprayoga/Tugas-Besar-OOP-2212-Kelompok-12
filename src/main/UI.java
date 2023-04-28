package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

import util.UtilityTool;
import main.GamePanel.GameState;
import graphics.ColorPalette;

public class UI {
    private final GamePanel gamePanel;
    private Graphics2D graphics2d;
    private Font pixolletta_general, upheavtt_title;

    class RectangleButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("UI.RectangleButtonListener.actionPerformed()");
        }
    }

    public UI (GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            upheavtt_title = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/upheavtt.ttf"));
            pixolletta_general = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/Pixolletta8px.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        // Background and title
        gamePanel.setBackground(Color.WHITE);
        BufferedImage titleImage = UtilityTool.loadImage("res/image/ui/title screen.png");
        try {
            graphics2d.drawImage(titleImage, 0, 0, gamePanel);
        } catch (Exception e) {
        }

        // Button backdrop button
        graphics2d.setColor(ColorPalette.light_grey);
        graphics2d.fillRect(288, 578, 448, 108);
        graphics2d.fillRect(288, 700, 448, 108);
        graphics2d.fillRect(288, 820, 448, 108);
        
        // Button rectangle
        graphics2d.setColor(ColorPalette.white);
        graphics2d.fillRect(302, 586, 420, 80);
        graphics2d.fillRect(302, 708, 420, 80);
        graphics2d.fillRect(302, 828, 420, 80);
        
        // Button text
        graphics2d.setColor(ColorPalette.dark_grey);
        graphics2d.setFont(upheavtt_title.deriveFont(61f));
        graphics2d.drawString("Play", UtilityTool.getXForCenterOfText("Play", gamePanel, graphics2d), 638);
        graphics2d.drawString("Help", UtilityTool.getXForCenterOfText("Help", gamePanel, graphics2d), 760);
        graphics2d.drawString("Quit", UtilityTool.getXForCenterOfText("Quit", gamePanel, graphics2d), 880);

        // JLabel for click listener
        // Play button
        JLabel playButton = new JLabel("Play");
        playButton.setBounds(302, 586, 420, 80);
        gamePanel.add(playButton);
        
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.setGameState(GameState.LOAD_GAME_SCREEN);
                System.out.println("UI.drawTitleScreen().new MouseAdapter() {...}.mouseClicked()");
                playButton.setVisible(false);
            }
        });

        // Help button
        JLabel helpButton = new JLabel("Help");
        helpButton.setBounds(302, 708, 420, 80);
        gamePanel.add(helpButton);

        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.setGameState(GameState.HELP_SCREEN);
                System.out.println("UI.drawTitleScreen().new MouseAdapter() {...}.mouseClicked()");
                helpButton.setVisible(false);
            }
        });

        // Quit button
        JLabel quitButton = new JLabel("Quit");
        quitButton.setBounds(302, 828, 420, 80);
        gamePanel.add(quitButton);

        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });


        // Copyright
        graphics2d.setFont(pixolletta_general.deriveFont(18f));
        graphics2d.drawString("The Simplicity © 2023 - Kelompok 12 OOP STI", UtilityTool.getXForCenterOfText("The Simplicity © 2023 - Kelompok 15 OOP STI", gamePanel, graphics2d), 990);
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