package main;

import entity.Sims;
import exceptions.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.Random;

public class MenuGame extends Sims{
    boolean gameStarted;
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

    public void setPekerjaan(Sims s, String pekerjaanWish) throws PekerjaanError{
        int randomnum;
        if(pekerjaan == null){
            Random rand = new Random()
            randomnum = rand.nextInt(7);
            if(randomnum == 0){
                s.setPekerjaan("zzz");
            }
        } else{
            if(kerjatime > 720){
                s.setMoney(s.getMoney() - s.getPekerjaan().getGaji()/2);
                s.setPekerjaan(pekerjaanWish);
                s.pekerjaanStart = LocalDateTime.now();
            } else{
                throw new PekerjaanError("Belum bisa mengganti pekerjaan.")
            }
        }
    }

    public void viewSimInfo(Sims s){
        System.out.println("Nama Sim: "+s.getName());
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
        s.getInventory().printInventory
    }

    public void upgradeHouse(){
        
    }

    public void move(){
    
    }
    public void editRoom(){}
    public void addSim(){}
    public void changeSim(){}

    public void displayObjects(){}

    public void goToObject(){}

    public void action(String aksi){}

    public void save(){}

    public void load(){}
}
