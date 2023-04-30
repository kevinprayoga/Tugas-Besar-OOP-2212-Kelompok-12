package main;

import entity.Sim;
import entity.waktu;
import exceptions.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.Random;

public class MenuGame{
    boolean gameStarted;
    private int addSimDay;


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
                throw new PekerjaanError("Belum bisa mengganti pekerjaan.")
            }
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
    
    public void viewLocation(){
        System.out.println("Rumah pada sumbu  x: "+Rumah.getLokasi().getAbsis());
        System.out.println("Rumah pada sumbu y: "+Rumah.getLokasi().getOrdinat());
        System.out.println("Ruangan" + ruangan.getName());
    }

    public void viewInventory(Sims s){
        s.getInventory().printInventory;
    }

    public void upgradeHouse(){
        
    }

    public void editRoom(){}
    public void addSim(){}
    public void changeSim(){}

    public void displayObjects(){}

    public void goToObject(){}

    public void action(String aksi){}

    public void save(){}

    public void load(){}

    public int getSimCD(){
        return addSimDay;
    }

    public boolean canAddSim(){
        return addSimDay != waktu.getDay();
    }
}
