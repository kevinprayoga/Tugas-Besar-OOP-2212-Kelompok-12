package graphics;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import entity.Sim;
import main.GamePanel;
import main.GamePanel.GameState;
import util.UtilityTool;

public class PopUpAction {
    private GamePanel gamePanel;
    private String text;
    private static int actionTime = 0;
    private static int timeRead = 0;
    private static int increment = 10;
    private Sim sim;

    private boolean isClosed = false;
    private boolean isSubmitted = false;

    public PopUpAction(String text, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.text = text;
        this.sim = gamePanel.getPlayedSims().getSims();
    }
    
    public void draw(Graphics2D graphics2d) {
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();
        if (keyHandler.code == KeyEvent.VK_ESCAPE || isClosed) {
            UI.setActionText("");
            timeRead = 0;
        }
        if (keyHandler.code == KeyEvent.VK_ENTER || isSubmitted) {
            try {
                executeAction(text);
                UI.setActionText("");
                gamePanel.getGameUI().setLoadingMessage("Sedang " + text + "... ");
                gamePanel.setGameState(GameState.LOADING_SCREEN);
                gamePanel.leastRecentlyUsed.push(GameState.LOADING_SCREEN);
                System.out.println("Sedang " + text + "... ");
                gamePanel.removeAll();
                timeRead = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        
        if (keyHandler.arrowCode == KeyEvent.VK_UP) {
            timeRead++;
        }
        if (keyHandler.arrowCode == KeyEvent.VK_DOWN) {
            if (timeRead > 0) {
                timeRead--;
            }
        }
        actionTime = timeRead / 10 * increment;
        
        BufferedImage popUpPanel = UtilityTool.loadImage("res/image/ui/action pop up.png");
        graphics2d.drawImage(popUpPanel, 324, 426, gamePanel);

        String timeText = actionTime + " sec";
        graphics2d.setColor(ColorPalette.dark_grey);
        graphics2d.setFont(UI.getGeneralFont().deriveFont(30f));
        graphics2d.drawString(timeText, 592 - UtilityTool.getTextWidth(timeText, graphics2d), 529);

        JLabel set = new JLabel("Set Action");
        set.setBounds(633, 428, 53, 67);
        gamePanel.add(set);

        JLabel cancel = new JLabel("Cancel");
        cancel.setBounds(633, 497, 53, 69);
        gamePanel.add(cancel);

        set.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isSubmitted = true;
            }
        });

        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isClosed = true;
            }
        });
    }

    private void executeAction(String text) throws Exception{
        System.out.println(text);
        for (Sim s : gamePanel.getPlayableSims()) {
            s.update(actionTime);
        }
        switch (text) {
            case "kerja":
                System.out.println("kerja");
                sim.kerja(actionTime);
                break;
            case "olahraga":
                System.out.println("olahraga");
                sim.olahraga(actionTime);
                break;
            case "tidur":
                System.out.println("tidur");
                sim.tidur(actionTime);
                break;
            case "meditasi":
                System.out.println("meditasi");
                sim.meditate(actionTime);
                break;
        }
    }
}
