package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.swing.*;

import entity.Sim;
import entity.Waktu;
import util.UtilityTool;
import main.CharacterSelector;
import main.GameLoader;
import main.GamePanel;
import main.GamePanel.GameState;

public class UIPainter extends Painter {
    // Screen settings
    private GamePanel gamePanel;
    private Graphics2D graphics2d;
    private static Font pixolletta_general;
    private static Font upheavtt_title;

    // Character selection screen
    private String nameField;
    private int optionSelected = 0;
    
    private int helpScreenPage = 1;

    // Action pop-up
    private static String actionText = "";

    // Loading screen text
    private String loadingText = "Loading...";

    class RectangleButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("UI.RectangleButtonListener.actionPerformed()");
        }
    }

    // Action time button
    private static boolean isActionPopUpOpen = false;
    private static int increment = 10;
    private static int actionTime = 0;

    public UIPainter (GamePanel gamePanel) {
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
        switch (gamePanel.getGameState()) {
            case TITLE_SCREEN:
                drawTitleScreen();
                break;
            case HELP_SCREEN:
                drawHelpScreen();
                break;
            case LOAD_GAME_SCREEN:
                drawLoadGameScreen();
                break;
            case CHARACTER_SELECTION_SCREEN:
                gamePanel.getKeyHandler().clearInput();
                drawCharacterSelectionScreen();
                break;
            case NEW_CHAR_SCREEN:
                drawCharacterSelectionScreen();
                drawNewCharScreen();
                break;
            case WORLD_GAME_SCREEN:
                drawWorldGameScreen();
                break;
            case HOUSE_GAME_SCREEN:
                drawHouseGameScreen();
                break;
            case LOADING_SCREEN:
                drawLoadingScreen();
                break;
            default:
                break;
        }
        if (gamePanel.getWoodworkingOpened() && gamePanel.getGameState() != GameState.LOADING_SCREEN) {
            WoodworkingPainter woodworkingPainter = new WoodworkingPainter(gamePanel);
            woodworkingPainter.draw(graphics2d);
        }

        if (gamePanel.isCookingOpened() && gamePanel.getGameState() != GameState.LOADING_SCREEN) {
            CookingPainter cookingPainter = new CookingPainter(gamePanel);
            cookingPainter.draw(graphics2d);
        }

        if (gamePanel.getChangeJobOpened() && gamePanel.getGameState() != GameState.LOADING_SCREEN) {
            ChangeJobPainter changeJobPainter = new ChangeJobPainter(gamePanel);
            changeJobPainter.draw(graphics2d);
        }

        if (gamePanel.getEatPanelOpened() && gamePanel.getGameState() != GameState.LOADING_SCREEN) {
            EatPanelPainter eatPainter = new EatPanelPainter(gamePanel);
            eatPainter.draw(graphics2d);
        }

        if (gamePanel.getClockOpened() && gamePanel.getGameState() != GameState.LOADING_SCREEN) {
            ClockPainter clockPainter = new ClockPainter(gamePanel);
            clockPainter.draw(graphics2d);
        }



        if (actionText == "kerja" || actionText == "olahraga" || actionText == "meditasi" || actionText == "tidur" || actionText == "judi") {
            PopUpActionPainter popUpAction = new PopUpActionPainter(actionText, gamePanel);
            popUpAction.draw(graphics2d);
        } else if (actionText != "") {
            loadingText = "Sedang " + actionText + "... ";
            actionTime = 0;
        }
    }

    private void drawTitleScreen() {
        // Background and title
        gamePanel.setBackground(Color.WHITE);
        BufferedImage titleImage = UtilityTool.loadImage("res/image/ui/title screen.png");
        graphics2d.drawImage(titleImage, 0, 0, gamePanel);

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
        
        // Help button
        JLabel helpButton = new JLabel("Help");
        helpButton.setBounds(302, 708, 420, 80);
        gamePanel.add(helpButton);
        
        // Quit button
        JLabel quitButton = new JLabel("Quit");
        quitButton.setBounds(302, 828, 420, 80);
        gamePanel.add(quitButton);

        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.setGameState(GameState.LOAD_GAME_SCREEN);
                gamePanel.leastRecentlyUsed.push(GameState.LOAD_GAME_SCREEN);
                System.out.println("Load game screen");
                gamePanel.removeAll();
            }
        });
        
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.setGameState(GameState.HELP_SCREEN);
                gamePanel.leastRecentlyUsed.push(GameState.HELP_SCREEN);
                System.out.println("Show help screen");
                gamePanel.removeAll();
            }
        });
        
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Quit game");
                System.exit(0);
            }
        });
        
        
        // Copyright
        graphics2d.setFont(pixolletta_general.deriveFont(18f));
        graphics2d.drawString("The Simplicity © 2023 - Kelompok 12 OOP STI", UtilityTool.getXForCenterOfText("The Simplicity © 2023 - Kelompok 12 OOP STI", gamePanel, graphics2d), 990);
    }
    
    private void drawHelpScreen() {
        // Background and title
        gamePanel.setBackground(Color.WHITE);
        BufferedImage helpScreenImage = UtilityTool.loadImage("res/image/ui/Help " + Integer.toString(helpScreenPage) + ".png");
        graphics2d.drawImage(helpScreenImage, 0, 0, gamePanel);

        // Back button
        BufferedImage backButtonImage = UtilityTool.loadImage("res/image/ui/back button.png");
        graphics2d.drawImage(backButtonImage, 12, 12, gamePanel);
        JLabel backButton = new JLabel("Back");
        backButton.setBounds(12, 12, 44, 44);
        gamePanel.add(backButton);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.leastRecentlyUsed.pop();
                GameState previousState = gamePanel.leastRecentlyUsed.peek();
                gamePanel.setGameState(previousState);
                System.out.println("Back to title screen");
                gamePanel.removeAll();
            }
        });

        // Next button
        JLabel nextButton = new JLabel("Next");
        nextButton.setBounds(48, 48, 976, 976);
        gamePanel.add(nextButton);

        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (helpScreenPage < 4) {
                    helpScreenPage++;
                } else {
                    helpScreenPage = 1;
                }
                gamePanel.removeAll();
            }
        });
    }
    
    private void drawLoadGameScreen() {
        // Background and title
        gamePanel.setBackground(Color.WHITE);
        BufferedImage titleImage = UtilityTool.loadImage("res/image/ui/load screen.png");
        graphics2d.drawImage(titleImage, 0, 0, gamePanel);
        
        // Back button
        BufferedImage backButtonImage = UtilityTool.loadImage("res/image/ui/back button.png");
        graphics2d.drawImage(backButtonImage, 12, 12, gamePanel);
        JLabel backButton = new JLabel("Back");
        backButton.setBounds(12, 12, 44, 44);
        gamePanel.add(backButton);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.leastRecentlyUsed.pop();
                System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
                GameState previousState = gamePanel.leastRecentlyUsed.peek();
                gamePanel.setGameState(previousState);
                System.out.println("Back to title screen");
                gamePanel.removeAll();
            }
        });
        
        // Three load game slots
        // Button backdrop button
        graphics2d.setColor(ColorPalette.light_grey);
        graphics2d.fillRect(196, 550, 448, 108);
        graphics2d.fillRect(196, 670, 448, 108);
        graphics2d.fillRect(196, 790, 448, 108);
        
        // Button rectangle
        graphics2d.setColor(ColorPalette.white);
        graphics2d.fillRect(210, 558, 420, 80);
        graphics2d.fillRect(210, 678, 420, 80);
        graphics2d.fillRect(210, 798, 420, 80);
        
        // Button text
        graphics2d.setColor(ColorPalette.dark_grey);
        graphics2d.setFont(upheavtt_title.deriveFont(61f));

        // Delete Button
        BufferedImage deleteButtonImage = UtilityTool.loadImage("res/image/ui/delete.png");
        BufferedImage deleteButtonImageGreyed = UtilityTool.loadImage("res/image/ui/delete_greyed.png");

        File file1 = new File("data/load1.json");
        if (file1.exists()) {
            graphics2d.drawString("LOAD GAME 1", UtilityTool.getXForCenterOfText("LOAD GAME 1", gamePanel, graphics2d) - 92, 610);
            graphics2d.drawImage(deleteButtonImage, 676, 550, gamePanel);

            // Loader
            JLabel loader = new JLabel("Loader");
            loader.setBounds(210, 558, 420, 80);
            gamePanel.add(loader);

            loader.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Load game 1");
                    GameLoader.loadGame(1);
                    gamePanel.removeAll();
                }
            });

            // Delete button
            JLabel deleteButton = new JLabel("Delete");
            deleteButton.setBounds(676, 550, 152, 108);
            gamePanel.add(deleteButton);

            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Delete game 1");
                    file1.delete();
                    gamePanel.removeAll();
                }
            });
        } else {
            graphics2d.drawString("NEW GAME", UtilityTool.getXForCenterOfText("NEW GAME", gamePanel, graphics2d) - 92, 610);
            graphics2d.drawImage(deleteButtonImageGreyed, 676, 550, gamePanel);

            // New Game
            JLabel newGame = new JLabel("New Game");
            newGame.setBounds(210, 558, 420, 80);
            gamePanel.add(newGame);

            newGame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("New game 1");
                    GameLoader.newGame(1);
                    gamePanel.removeAll();
                }
            });
        }
        
        File file2 = new File("data/load2.json");
        if (file2.exists()) {
            graphics2d.drawString("LOAD GAME 2", UtilityTool.getXForCenterOfText("LOAD GAME 2", gamePanel, graphics2d) - 92, 730);
            graphics2d.drawImage(deleteButtonImage, 676, 670, gamePanel);

            // Loader
            JLabel loader = new JLabel("Loader");
            loader.setBounds(210, 678, 420, 80);
            gamePanel.add(loader);

            loader.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Load game 2");
                    GameLoader.loadGame(2);
                    gamePanel.removeAll();
                }
            });

            // Delete button
            JLabel deleteButton = new JLabel("Delete");
            deleteButton.setBounds(676, 670, 152, 108);
            gamePanel.add(deleteButton);

            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Delete game 2");
                    file2.delete();
                    gamePanel.removeAll();
                }
            });
        } else {
            graphics2d.drawString("NEW GAME", UtilityTool.getXForCenterOfText("NEW GAME", gamePanel, graphics2d) - 92, 730);
            graphics2d.drawImage(deleteButtonImageGreyed, 676, 670, gamePanel);

            // New Game
            JLabel newGame = new JLabel("New Game");
            newGame.setBounds(210, 678, 420, 80);
            gamePanel.add(newGame);

            newGame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("New game 2");
                    GameLoader.newGame(2);
                    gamePanel.removeAll();
                }
            });
        }
        
        File file3 = new File("data/load3.json");
        if (file3.exists()) {
            graphics2d.drawString("LOAD GAME 3", UtilityTool.getXForCenterOfText("LOAD GAME 3", gamePanel, graphics2d) - 92, 850);
            graphics2d.drawImage(deleteButtonImage, 676, 790, gamePanel);

            // Loader
            JLabel loader = new JLabel("Loader");
            loader.setBounds(210, 798, 420, 80);
            gamePanel.add(loader);

            loader.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Load game 3");
                    GameLoader.loadGame(3);
                    gamePanel.removeAll();
                }
            });

            // Delete button
            JLabel deleteButton = new JLabel("Delete");
            deleteButton.setBounds(676, 790, 152, 108);
            gamePanel.add(deleteButton);

            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Delete game 3");
                    file3.delete();
                    gamePanel.removeAll();
                }
            });
        } else {
            graphics2d.drawString("NEW GAME", UtilityTool.getXForCenterOfText("NEW GAME", gamePanel, graphics2d) - 92, 850);
            graphics2d.drawImage(deleteButtonImageGreyed, 676, 790, gamePanel);

            // New Game
            JLabel newGame = new JLabel("New Game");
            newGame.setBounds(210, 798, 420, 80);
            gamePanel.add(newGame);

            newGame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("New game 3");
                    GameLoader.newGame(3);
                    gamePanel.removeAll();
                }
            });
        }
    }
    
    private void drawCharacterSelectionScreen() {
        // Background and backdrop
        gamePanel.setBackground(Color.WHITE);   
        BufferedImage backdrop = UtilityTool.loadImage("res/image/ui/char backdrop.png");
        graphics2d.drawImage(backdrop, 32 * gamePanel.getTileSize(), 0, gamePanel);

        // Back button
        BufferedImage backButtonImage = UtilityTool.loadImage("res/image/ui/back button.png");
        graphics2d.drawImage(backButtonImage, 12, 12, gamePanel);
        JLabel backButton = new JLabel("Back");
        backButton.setBounds(12, 12, 44, 44);
        gamePanel.add(backButton);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.leastRecentlyUsed.pop();
                System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
                GameState previousState = gamePanel.leastRecentlyUsed.peek();
                gamePanel.setGameState(previousState);
                System.out.println("Back to load screen");
                GameLoader.saveGame(gamePanel.getPlayableSims(), gamePanel.getWorld());
                
                gamePanel.removeAll();
            }
        });

        // Character selection
        CharacterSelector characterSelector = new CharacterSelector(gamePanel);
        characterSelector.draw(graphics2d);
    }

    private void drawNewCharScreen() {
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();
        keyHandler.setInputLimit(20);
        nameField = keyHandler.getInput();

        BufferedImage textField = UtilityTool.loadImage("res/image/ui/text field.png");
        graphics2d.drawImage(textField, 574, 100, gamePanel);


        graphics2d.setFont(pixolletta_general.deriveFont(22f));
        graphics2d.setColor(ColorPalette.dark_grey);
        graphics2d.drawString(nameField, 591, 130);

        graphics2d.setFont(upheavtt_title.deriveFont(32f));
        graphics2d.setColor(ColorPalette.dark_grey);
        graphics2d.drawString("Nama", 576, 90);

        // Character selection
        // Shadow
        graphics2d.setColor(new Color(0f, 0f, 0f, 0.27f));
        graphics2d.fillOval(658, 822, 243, 58);

        BufferedImage character;
        switch (optionSelected) {
            case 0:
                character = UtilityTool.loadImage("res/image/sims/bnmo/BNMO_Down_Left (1).png");
                break;
            case 1:
                character = UtilityTool.loadImage("res/image/sims/hans/Hans_Down_Left (1).png");
                break;
            case 2:
                character = UtilityTool.loadImage("res/image/sims/ivan/Ivan_Down_Left (1).png");
                break;
            case 3:
                character = UtilityTool.loadImage("res/image/sims/kevin/Kevin_Down_Left (1).png");
                break;
            case 4:
                character = UtilityTool.loadImage("res/image/sims/nicholas/Nic_Down_Left (1).png");
                break;
            case 5:
                character = UtilityTool.loadImage("res/image/sims/ojan/Ojan_Down_Left (1).png");
                break;
            case 6:
                character = UtilityTool.loadImage("res/image/sims/rana/Rana_Down_Left (1).png");
                break;
            default:
                character = UtilityTool.loadImage("res/image/sims/bnmo/BNMO_Down_Left (1).png");
                break;
        }

        character = UtilityTool.scaleImage(character, 27f);
        if (optionSelected == 0) {
            graphics2d.drawImage(character, 551 + 20, 426, gamePanel);
        } else {
            graphics2d.drawImage(character, 551, 426, gamePanel);
        }

        // Left and right buttons
        BufferedImage leftButton = UtilityTool.loadImage("res/image/ui/select left.png");
        BufferedImage rightButton = UtilityTool.loadImage("res/image/ui/select right.png");
        JLabel leftButtonLabel = new JLabel("Left");
        JLabel rightButtonLabel = new JLabel("Right");

        if (optionSelected == 0) {
            graphics2d.drawImage(leftButton, 619 - 40, 615, gamePanel);
            graphics2d.drawImage(rightButton, 926 + 40, 615, gamePanel);
            leftButtonLabel.setBounds(619 - 40, 615, 26, 175);
            rightButtonLabel.setBounds(926 + 40, 615, 26, 175);

        } else {
            graphics2d.drawImage(leftButton, 619, 615, gamePanel);
            graphics2d.drawImage(rightButton, 926, 615, gamePanel); 
            leftButtonLabel.setBounds(619, 615, 26, 175);
            rightButtonLabel.setBounds(926, 615, 26, 175);
        }

        gamePanel.add(leftButtonLabel);
        gamePanel.add(rightButtonLabel);

        leftButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (optionSelected > 0) {
                    optionSelected--;
                } else {
                    optionSelected = 6;
                }
                System.out.println("Left");
                gamePanel.removeAll();
            }
        });

        rightButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (optionSelected < 6) {
                    optionSelected++;
                } else {
                    optionSelected = 0;
                }
                System.out.println("Right");
                gamePanel.removeAll();
            }
        });

        // Add sims button
        // Button backdrop button
        graphics2d.setColor(ColorPalette.light_grey);
        graphics2d.fillRect(602, 900, 356, 84);
        
        // Button rectangle
        graphics2d.setColor(ColorPalette.white);
        graphics2d.fillRect(616, 908, 328, 56);
        
        // Button text
        if (nameField.length() >= 3) {
            graphics2d.setColor(ColorPalette.dark_grey);
        } else {
            graphics2d.setColor(ColorPalette.light_grey);
        }
        graphics2d.setFont(upheavtt_title.deriveFont(61f));
        graphics2d.drawString("ADD SIMS", UtilityTool.getXForCenterOfText("ADD SIMS", gamePanel, graphics2d) + 266, 950);

        // JLabel for click listener
        // add button
        JLabel addButton = new JLabel("add");
        addButton.setBounds(616, 908, 328, 56);
        gamePanel.add(addButton);

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Sim sims = new Sim(nameField, optionSelected, false);
                    gamePanel.addPlayableSims(sims);
                    gamePanel.menuGame.setSimCD(Waktu.getDay());
                    gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
                    gamePanel.removeAll();
                    System.out.println("Load game screen");
                } catch (Exception e1) {
                    e1.printStackTrace();               
                }
            }
        });

        if (keyHandler.code == KeyEvent.VK_ENTER) {
            try {
                Sim sims = new Sim(nameField, optionSelected, false);
                gamePanel.addPlayableSims(sims);
                gamePanel.menuGame.setSimCD(Waktu.getDay());
                gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
                gamePanel.removeAll();
                System.out.println("Load game screen");
            } catch (Exception e1) {
                e1.printStackTrace();               
            }
        }

        // Back button
        BufferedImage backButtonImage = UtilityTool.loadImage("res/image/ui/back button.png");
        graphics2d.drawImage(backButtonImage, 12, 12, gamePanel);
        JLabel backButton = new JLabel("Back");
        backButton.setBounds(12, 12, 44, 44);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
                gamePanel.leastRecentlyUsed.pop();
                GameState previousState = gamePanel.leastRecentlyUsed.peek();
                gamePanel.setGameState(previousState);
                System.out.println("Main menu");
                gamePanel.removeAll();
            }
        });
    }

    private void drawWorldGameScreen() {
        // Back button
        BufferedImage backButtonImage = UtilityTool.loadImage("res/image/ui/back button.png");
        graphics2d.drawImage(backButtonImage, 12, 12, gamePanel);
        JLabel backButton = new JLabel("Back");
        backButton.setBounds(12, 12, 44, 44);
        gamePanel.add(backButton);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.leastRecentlyUsed.pop();
                System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
                GameState previousState = gamePanel.leastRecentlyUsed.peek();
                gamePanel.setGameState(previousState);
                System.out.println("Title screen ASDASDAS");
                gamePanel.removeAll();
            }
        });

        // Change sim button
        BufferedImage changeSimButtonImage = UtilityTool.loadImage("res/image/ui/change sim.png");
        graphics2d.drawImage(changeSimButtonImage, 12, 66, gamePanel);
        JLabel changeSimButton = new JLabel("Change Sim");
        changeSimButton.setBounds(12, 66, 44, 44);
        gamePanel.add(changeSimButton);

        changeSimButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
                gamePanel.leastRecentlyUsed.push(GameState.CHARACTER_SELECTION_SCREEN);
                System.out.println("Character selection screen");
                gamePanel.removeAll();
            }
        });
  
        // Drawing Dashboard
        DashboardPainter dashboard = new DashboardPainter(gamePanel);
        dashboard.draw(graphics2d);
    }

    private void drawHouseGameScreen() {
        if (gamePanel.isHouseSelected) {
            gamePanel.removeAll();
            gamePanel.isHouseSelected = false;
        }
        // Back button
        BufferedImage backButtonImage = UtilityTool.loadImage("res/image/ui/back button.png");
        graphics2d.drawImage(backButtonImage, 12, 12, gamePanel);
        JLabel backButton = new JLabel("Back");
        backButton.setBounds(12, 12, 44, 44);
        gamePanel.add(backButton);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                while (gamePanel.leastRecentlyUsed.peek() != GameState.WORLD_GAME_SCREEN) {
                    gamePanel.leastRecentlyUsed.pop();
                }
                System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
                GameState previousState = gamePanel.leastRecentlyUsed.peek();
                gamePanel.setGameState(previousState);
                gamePanel.getPlayedSims().getSims().setCurrentPosition("World");
                System.out.println("World screen");

                gamePanel.getHouse().setBuildMode(false);
                gamePanel.setHoveredObject(null);
                gamePanel.getHouse().removeSim(gamePanel.getPlayedSims().getSims());
                gamePanel.getWorld().addSim((gamePanel.getPlayedSims().getSims()));
                gamePanel.getPlayedSims().reset();
                gamePanel.removeAll();
            }
        });

        // Change sim button
        BufferedImage changeSimButtonImage = UtilityTool.loadImage("res/image/ui/change sim.png");
        graphics2d.drawImage(changeSimButtonImage, 12, 66, gamePanel);
        JLabel changeSimButton = new JLabel("Change Sim");
        changeSimButton.setBounds(12, 66, 44, 44);
        gamePanel.add(changeSimButton);

        changeSimButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
                gamePanel.leastRecentlyUsed.push(GameState.CHARACTER_SELECTION_SCREEN);
                System.out.println("Character selection screen");
                gamePanel.removeAll();
            }
        });

        // Drawing Dashboard
        DashboardPainter dashboard = new DashboardPainter(gamePanel);
        dashboard.draw(graphics2d);
    }

    // Laoding screen
    private void drawLoadingScreen() {
        if (Waktu.getActionTimer() == 0) {
            gamePanel.leastRecentlyUsed.pop();
            System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
            GameState previousState = gamePanel.leastRecentlyUsed.peek();
            gamePanel.setGameState(previousState);
            System.out.println("World screen");
            gamePanel.removeAll();
        } else {
            gamePanel.setBackground(ColorPalette.white);
            graphics2d.setColor(ColorPalette.dark_grey);
            graphics2d.setFont(upheavtt_title.deriveFont(61f));
            graphics2d.drawString(loadingText + Integer.toString(Waktu.getActionTimer()), UtilityTool.getXForCenterOfText(loadingText + Integer.toString(Waktu.getActionTimer()), gamePanel, graphics2d), UtilityTool.getYForCenterOfText(loadingText + " " + Integer.toString(Waktu.getActionTimer()), gamePanel, graphics2d));
        }
    }

    public void setLoadingMessage(String message) {
        this.loadingText = message;
    }

    public static void setActionText(String text) {
        actionText = text;
    }

    // Font getter
    public static Font getTitleFont() {
        return upheavtt_title;
    }

    public static Font getGeneralFont() {
        return pixolletta_general;
    }
}