package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import entity.Rumah;
import entity.Ruangan;
import entity.Matrix;
import main.GamePanel;
import util.UtilityTool;

public class HousePainter extends Painter {
    private final GamePanel gamePanel;
    private Rumah house;
    private Matrix<Ruangan> matRoom;
    private Matrix<Integer> roomBuild;

    private final BufferedImage addRoom = UtilityTool.loadImage("res/image/house/Add Room.png");

    private int initX = 60, initY = 68;

    public HousePainter(Rumah house, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.house = house;
        this.matRoom = house.getMatRoom();
        this.roomBuild = house.getRoomBuild();
    }

    public void draw(Graphics2D graphics2d) {

        // Creating build button
        class BuildButton {
            public void draw(Graphics2D graphics2d, int x, int y) {
                graphics2d.drawImage(addRoom, initX + x * 100 + 30, initY + y * 96 + 50, gamePanel);

                JLabel buildButton = new JLabel("Build");
                buildButton.setBounds(initX + x * 100 + 30, initY + y * 96 + 50, addRoom.getWidth(), addRoom.getHeight());
                gamePanel.add(buildButton);

                buildButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if (house.isBuildMode()) {
                            System.out.println("Building room");
                            try{
                                gamePanel.getPlayedSims().getSims().upgradeRumah(x,y,"AAAA");
                            } catch(Exception e){
                                e.getMessage();
                            }
                        }
                        gamePanel.removeAll();
                    }
                });
            }
        }


        gamePanel.setBackground(Color.decode("#211F1B"));
        // Draw The Room
        for (int i = 0; i < house.getDimensi().getLength(); i++) {
            for (int j = 0; j < house.getDimensi().getWidth(); j++) {
                if (house.getRoomBuild().get(i, j) == 2) {
                    RoomPainter roomPainter = new RoomPainter(matRoom.get(i, j), i, j, gamePanel, house.isBuildMode(), initX + j * 100, initY + i * 96);
                    roomPainter.draw(graphics2d);
                } else {
                    if (house.isBuildMode() && house.getOwner().equals(gamePanel.getPlayedSims().getSims())) {
                        if (roomBuild.get(i, j) == 1) {
                            BuildButton buildButton = new BuildButton();
                            buildButton.draw(graphics2d, j, i);
                        }
                    }
                }
            }
        }

        // for (int i = 0; i < house.getDimensi().getLength(); i++) {
        //     for (int j = 0; j < house.getDimensi().getWidth(); j++) {
        //         if (house.getRoomBuild().get(i, j) == 2) {
        //             graphics2d.drawImage(sideUpperWall, initX + j * 100, initY + i * 96, gamePanel);
        //             graphics2d.drawImage(sideUpperWall, initX + (j + 1) * 100, initY + i * 96, gamePanel);
        //         }
        //     }
        // }
    }
}
