package main;

import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Sim-Plicity");

        GamePanel gamePanel = GamePanel.getGamePanel();
        window.add(gamePanel);
        window.pack();
    
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);

        gamePanel.startGameThread();
    }
}