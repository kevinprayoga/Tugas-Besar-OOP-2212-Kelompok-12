package main;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;

import javax.swing.JLabel;

import entity.Sim;
import graphics.ColorPalette;
import graphics.PlayedSims;
import main.GamePanel.GameState;
import util.UtilityTool;

public class CharacterSelector {
    private final GamePanel gamePanel;
    private static int iterator;

    class Placeholder {
        enum Types {Sim, Addsim};
        private Types type;
        private Sim sim = null;

        GamePanel gamePanel;

        public Placeholder(GamePanel gamePanel, Sim sim) {
            this.gamePanel = gamePanel;
            this.sim = sim;
        }

        public Placeholder(GamePanel gamePanel) {
            this.gamePanel = gamePanel;
        }

        public void draw(Graphics2D graphics2d, int x, int y) {
            if (type == Types.Sim) {
                BufferedImage image = UtilityTool.loadImage("res/image/ui/placeholder.png");
                graphics2d.drawImage(image, x, y, gamePanel);

                graphics2d.setFont(gamePanel.getGameUI().getGeneralFont().deriveFont(22f));
                graphics2d.setColor(ColorPalette.dark_grey);
                graphics2d.drawString(sim.getNamaLengkap(), x + 100, y + 34);
                
                graphics2d.setColor(Color.decode("#A2CA93"));
                graphics2d.setFont(gamePanel.getGameUI().getGeneralFont().deriveFont(15f));
                graphics2d.fillRoundRect(x + 100, y + 44, UtilityTool.getTextWidth(Integer.toString(sim.getUang()) + 32, graphics2d), 24, 5, 5);

                graphics2d.setColor(Color.decode("#3E814D"));
                graphics2d.drawString(Integer.toString(sim.getUang()), x + 108, y + 62);

                JLabel label = new JLabel("Sim");
                label.setBounds(x, y, 388, 84);
                gamePanel.add(label);

                BufferedImage character = sim.getCharacter();
                character = UtilityTool.scaleImage(character, 4.4f);
                graphics2d.drawImage(character, x + 10, y + 4, gamePanel);

                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gamePanel.setPlayedSims(new PlayedSims(gamePanel, sim));
                        if (sim.getCurrentPosition().equals("World")) {
                            gamePanel.setGameState(GameState.WORLD_GAME_SCREEN);
                        } else {
                            gamePanel.setGameState(GameState.HOUSE_GAME_SCREEN);
                        }
                        gamePanel.removeAll();
                    }
                });

            } else {
                if (gamePanel.getAddSimsAvailable()) {
                    BufferedImage image = UtilityTool.loadImage("res/image/ui/add char.png");
                    graphics2d.drawImage(image, x, y, gamePanel);
                    
                    JLabel label = new JLabel("Add Sim");
                    label.setBounds(x, y, 388, 84);
                    gamePanel.add(label);
    
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            gamePanel.setGameState(GameState.NEW_CHAR_SCREEN);
                            gamePanel.removeAll();
                        }
                    });
                } else {
                    BufferedImage image = UtilityTool.loadImage("res/image/ui/add char cooldown.png");
                    graphics2d.drawImage(image, x, y, gamePanel);
                }
            }
        }
    }

    public CharacterSelector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D graphics2d) {
        Placeholder[] placeholders = new Placeholder[9];
        for (int i = 0; i < Math.min(9, gamePanel.getPlayableSims().size() + 1 - iterator * 9) ; i++) {
            if (i + iterator * 9 != gamePanel.getPlayableSims().size()) {
                placeholders[i] = new Placeholder(gamePanel, gamePanel.getPlayableSims().get(i + iterator * 9));
                placeholders[i].type = Placeholder.Types.Sim;
                placeholders[i].draw(graphics2d, 63, 84 + i * 97);
            } else {
                placeholders[i] = new Placeholder(gamePanel);
                placeholders[i].type = Placeholder.Types.Addsim;
                placeholders[i].draw(graphics2d, 63, 84 + i * 97);
            }
        }

        if (iterator > 0) {
            BufferedImage image = UtilityTool.loadImage("res/image/ui/arrow up.png");
            graphics2d.drawImage(image, 169, 30, gamePanel);

            JLabel label = new JLabel("Up");
            label.setBounds(169, 30, 175, 26);
            gamePanel.add(label);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Up");
                    iterator--;
                    gamePanel.removeAll();
                }
            });
        }

        if (9 < gamePanel.getPlayableSims().size() + 1 - iterator * 9) {
            BufferedImage image = UtilityTool.loadImage("res/image/ui/arrow down.png");
            graphics2d.drawImage(image, 169, 968, gamePanel);

            JLabel label = new JLabel("Down");
            label.setBounds(169, 968, 175, 26);
            gamePanel.add(label);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Down");
                    iterator++;
                    System.out.println(iterator);
                    gamePanel.removeAll();
                }
            });
        }
    }
    
}
