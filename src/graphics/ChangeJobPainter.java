package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import entity.Pekerjaan;
import entity.PekerjaanPrinter;
import entity.Sim;
import main.GamePanel;

public class ChangeJobPainter {
    private GamePanel gamePanel;
    private Sim sim;
    private BufferedImage background = util.UtilityTool.loadImage("res/image/ui/change job.png");

    String[] jobList = {"BadutSulap", "Barista", "Koki", "Polisi", "Programmer", "Dokter", "Guru"};
    public ChangeJobPainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.sim = gamePanel.getPlayedSims().getSims();
    }

    public void draw(Graphics2D graphics2d) {
        graphics2d.drawImage(background, 267, 266, gamePanel);
        
        util.KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (keyHandler.code == KeyEvent.VK_ESCAPE) {
            gamePanel.setChangeJobOpened(false);
        }
        
        class Button {
            private int width = 143, height = 122;
            private PekerjaanPrinter job;

            public Button(PekerjaanPrinter job) {
                this.job = job;
            }
            
            public void draw(Graphics2D graphics2d, int x, int y) {
                JLabel button = new JLabel(job.getTestPekerjaan().getJob());
                button.setBounds(x, y, width, height);
                gamePanel.add(button);

                button.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        System.out.println(job.getTestPekerjaan().getJob());
                        try {
                            gamePanel.menuGame.setPekerjaan(sim, job.getTestPekerjaan().getJob());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        gamePanel.setChangeJobOpened(false);
                    }
                });
            }
        }

        for (int i = 0; i < jobList.length; i++) {
            PekerjaanPrinter job = new PekerjaanPrinter();
            job.setJob(jobList[i]);
            Button button = new Button(job);
            if (i == jobList.length - 1) {
                button.draw(graphics2d, 441, 626);
            } else {
                button.draw(graphics2d, 285 + (i % 3) * 156, 354 + (i / 3) * 136);
            }
        }
    }

}
