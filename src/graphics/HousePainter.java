package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import entity.Rumah;
import entity.Ruangan;
import entity.Matrix;
import entity.Posisi;
import main.GamePanel;
import util.UtilityTool;

public class HousePainter {
    private final GamePanel gamePanel;
    private Rumah house;
    private Matrix<Ruangan> matRoom;
    private Matrix<Integer> roomBuild;

    private final BufferedImage addRoom = UtilityTool.loadImage("res/image/house/Add Room.png");
    private final BufferedImage sideUpperWall = UtilityTool.loadImage("res/image/house/Side Upper Wall.png");

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
                        System.out.println("Building room");
                        // Code
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
                    RoomPainter roomPainter = new RoomPainter(matRoom.get(i, j), new Posisi(i, j), gamePanel, house.isBuildMode());
                    roomPainter.draw(graphics2d, initX + j * 100, initY + i * 96);
                } else {
                    if (house.isBuildMode() && house.getOwner().equals(gamePanel.getPlayedSims().getSims())) {
                        if (roomBuild.get(i, j) == 1) {
                            BuildButton buildButton = new BuildButton();
                            buildButton.draw(graphics2d, i, j);
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
