package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import entity.Sim;
import exceptions.NotEnoughKesejahteraan;
import graphics.UI;
import main.GamePanel;
import main.GamePanel.GameState;
import util.UtilityTool;

public class ActionButton {
    private GamePanel gamePanel;
    private Sim sim;

    public ActionButton(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sim = gamePanel.getPlayedSims().getSims();
    }
    
    class CustomButton {
        private final int sideMargin = 10;
        private final int topMargin = 4;
        private final int stroke = 3;
        private String text;

        public CustomButton(String text) {
            this.text = text;
        }

        public void draw(Graphics2D graphics2d, int x, int y) {
            graphics2d.setColor(new Color(0, 0, 0, 0.25f));
            graphics2d.fillRect(x, y, UtilityTool.getTextWidth(text, graphics2d) + 2 * (sideMargin + stroke), UtilityTool.getTextHeight(text, graphics2d) + 2 * (topMargin + stroke));
            graphics2d.setColor(Color.WHITE);
            graphics2d.fillRect(x + stroke, y + stroke, UtilityTool.getTextWidth(text, graphics2d) + 2 * sideMargin, UtilityTool.getTextHeight(text, graphics2d) + 2 * topMargin);
            graphics2d.setFont(UI.getGeneralFont().deriveFont(10f));
            graphics2d.setColor(ColorPalette.dark_grey);
            graphics2d.drawString(text, x + sideMargin + stroke, y + topMargin + stroke + UtilityTool.getTextHeight(text, graphics2d));
        }

        public int getWidth(Graphics2D graphics2d) {
            return UtilityTool.getTextWidth(text, graphics2d) + 2 * (sideMargin + stroke);
        }

        public int getHeight(Graphics2D graphics2d) {
            return UtilityTool.getTextHeight(text, graphics2d) + 2 * (topMargin + stroke);
        }
    }

    public void drawSimsButton(Graphics2D graphics2d, int x, int y) {
        int xOffset = 0, yOffset = 56;
        // Right side
        // Work Button
        String text = "Pergi Kerja";
        CustomButton eatButton = new CustomButton(text);
        eatButton.draw(graphics2d, x - xOffset, y - yOffset);

        JLabel eatLabel = new JLabel(text);
        eatLabel.setBounds(x - xOffset, y - yOffset, eatButton.getWidth(graphics2d),  eatButton.getHeight(graphics2d));
        gamePanel.add(eatLabel);

        eatLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UI.setActionText("kerja");
            }
        });

        // Switch job button
        text = "Ganti Pekerjaan";
        CustomButton switchJobButton = new CustomButton(text);
        xOffset = -8;
        switchJobButton.draw(graphics2d, x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + 10);

        JLabel switchJobLabel = new JLabel(text);
        switchJobLabel.setBounds(x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + 10, switchJobButton.getWidth(graphics2d),  switchJobButton.getHeight(graphics2d));
        gamePanel.add(switchJobLabel);

        switchJobLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Fungsi ganti pekerjaan
            }
        });

        // Workout button
        text = "Olahraga";
        CustomButton workoutButton = new CustomButton(text);
        workoutButton.draw(graphics2d, x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + 20);

        JLabel workoutLabel = new JLabel(text);
        workoutLabel.setBounds(x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + 20, workoutButton.getWidth(graphics2d),  workoutButton.getHeight(graphics2d));
        gamePanel.add(workoutLabel);

        workoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UI.setActionText("olahraga");
            }
        });

        // Woodworking button
        text = "Woodworking";
        CustomButton woodworkingButton = new CustomButton(text);
        xOffset = 0;
        woodworkingButton.draw(graphics2d, x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + 30);

        JLabel woodworkingLabel = new JLabel(text);
        woodworkingLabel.setBounds(x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + 30, woodworkingButton.getWidth(graphics2d),  woodworkingButton.getHeight(graphics2d));
        gamePanel.add(woodworkingLabel);

        woodworkingLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UI.setActionText("woodworking");
            }
        });

        // Holiday button
        text = "Liburan";
        CustomButton holidayButton = new CustomButton(text);
        yOffset = 160;
        xOffset = holidayButton.getWidth(graphics2d) + 48;
        holidayButton.draw(graphics2d, x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + 30);

        JLabel holidayLabel = new JLabel(text);
        holidayLabel.setBounds(x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + 30, holidayButton.getWidth(graphics2d),  holidayButton.getHeight(graphics2d));
        gamePanel.add(holidayLabel);

        holidayLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Fungsi liburan
            }
        });

        // Meditation button
        text = "Meditasi";
        CustomButton meditationButton = new CustomButton(text);
        xOffset = meditationButton.getWidth(graphics2d) + 56;
        meditationButton.draw(graphics2d, x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + holidayButton.getHeight(graphics2d) + 40);

        JLabel meditationLabel = new JLabel(text);
        meditationLabel.setBounds(x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + holidayButton.getHeight(graphics2d) + 40, meditationButton.getWidth(graphics2d),  meditationButton.getHeight(graphics2d));
        gamePanel.add(meditationLabel);

        meditationLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UI.setActionText("meditasi");
            }
        });

        // Party button
        text = "Pesta";
        CustomButton partyButton = new CustomButton(text);
        xOffset = partyButton.getWidth(graphics2d) + 56;
        partyButton.draw(graphics2d, x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + holidayButton.getHeight(graphics2d) + meditationButton.getHeight(graphics2d) + 50);

        JLabel partyLabel = new JLabel(text);
        partyLabel.setBounds(x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + holidayButton.getHeight(graphics2d) + meditationButton.getHeight(graphics2d) + 50, partyButton.getWidth(graphics2d),  partyButton.getHeight(graphics2d));
        gamePanel.add(partyLabel);

        partyLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Fungsi pesta
                try {
                    System.out.println("Pesta");
                    sim.party();
                    UI.setActionText("pesta");
                    gamePanel.getGameUI().setLoadingMessage("Sedang Pesta ... ");
                    gamePanel.setGameState(GameState.LOADING_SCREEN);
                    gamePanel.leastRecentlyUsed.push(GameState.LOADING_SCREEN);
                    System.out.println("Sedang ... ");
                    gamePanel.removeAll();

                    // Updating
                    for (Sim s : gamePanel.getPlayableSims()) {
                        s.update(120);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Gambling button
        text = "Judi";
        CustomButton gamblingButton = new CustomButton(text);
        xOffset = gamblingButton.getWidth(graphics2d) + 48;
        gamblingButton.draw(graphics2d, x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + holidayButton.getHeight(graphics2d) + meditationButton.getHeight(graphics2d) + partyButton.getHeight(graphics2d) + 60);

        JLabel gamblingLabel = new JLabel(text);
        gamblingLabel.setBounds(x - xOffset, y - yOffset + eatButton.getHeight(graphics2d) + switchJobButton.getHeight(graphics2d) + workoutButton.getHeight(graphics2d) + holidayButton.getHeight(graphics2d) + meditationButton.getHeight(graphics2d) + partyButton.getHeight(graphics2d) + 60, gamblingButton.getWidth(graphics2d),  gamblingButton.getHeight(graphics2d));
        gamePanel.add(gamblingLabel);

        gamblingLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UI.setActionText("judi");
            }
        });
    }
}
