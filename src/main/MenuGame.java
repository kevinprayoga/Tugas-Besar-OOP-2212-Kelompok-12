package main;

import entity.BadutSulap;
import entity.Sim;
import entity.Waktu;
import entity.World;
import exceptions.*;
import graphics.PlayedSims;

import java.util.Scanner;
import java.awt.Menu;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class MenuGame{
    private final GamePanel gamePanel;

    boolean gameStarted;
    private int addSimDay = -1;
    private World mainWorld;
    private ArrayList<Sim> playableSims;
    private PlayedSims playedSims;

    public MenuGame(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        playableSims = gamePanel.getPlayableSims();
        playedSims = gamePanel.getPlayedSims();
    }

    public void exitGame(){
        System.out.println("Keluar dari game. Sampai jumpa!");
        System.exit(0);
    }

    public void help(){
        System.out.println("Help:");
    }

    public void startGame(){
        gameStarted = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Apa yang ingin dilakukan?");
        String command = sc.nextLine();
        sc.close();
    }
    /* 
    public void setPekerjaan(Sim s, String pekerjaanWish) throws PekerjaanError,TidakCukupItem{
        int randomnum;
        if(s.getPekerjaan() == null){
            Random rand = new Random();
            randomnum = rand.nextInt(7);
            if(randomnum == 0){
                s.pekerjaan = ;
            }
        } else{
            if(s.kerjatime > 720){
                if(s.getMoney() - s.getPekerjaan().getGaji()/2 <= 0){
                    throw new TidakCukupItem("Uang tidak cukup untuk mengganti pekerjaan!");
                } else{
                    s.setMoney(s.getMoney() - s.getPekerjaan().getGaji()/2);
                    s.pekerjaan = pekerjaanWish;
                    s.pekerjaanStart = LocalDateTime.now();
                }
            } else{
                throw new PekerjaanError("Belum bisa mengganti pekerjaan.");
            }
        }
    }
    */

    public void setPekerjaan(Sim playedSim, String newJob) throws PekerjaanError, TidakCukupItem{
        if (playedSim.getTotalWaktuKerja() <= 720) {
            throw new  PekerjaanError("Belum bisa mengganti pekerjaan menjadi " + newJob);
        } else {
            int costJob = playedSim.getPekerjaanPrinter().costGajiNewJob(newJob, playedSim.getUang(),playedSim.getTotalWaktuKerja());
            playedSim.setMinusUang(costJob);
            playedSim.setTotalWaktuKerjaZero();
        }
    }

    public void viewSimInfo(Sim s){
        System.out.println("Nama Sim: "+s.getNamaLengkap());
        System.out.println("Pekerjaan: " +s.getPekerjaan());
        System.out.println("Kesehatan: "+s.getKesehatan());
        System.out.println("Kekenyangan: "+s.getKekenyangan());
        System.out.println("Mood: "+s.getMood());
        System.out.println("Uang: "+s.getUang());
    }

    public void viewInventory(Sim s){
        s.getInventory().printInventory();
    }

    public void upgradeHouse(){
        
    }

    public void editRoom(){}

    public void changeSim(){}

    public void displayObjects(){}

    public void goToObject(){}

    public void action(String aksi){}

    public void save(){}

    public void load(){}

    public int getSimCD(){
        return addSimDay;
    }

    public void setSimCD(int day){
        addSimDay = day;
    }

    public boolean canAddSim(){
        return addSimDay != Waktu.getDay();
    }
}
