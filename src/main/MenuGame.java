package main;

import entity.Sims;
import exceptions.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.Random;

public class MenuGame extends Sims{
    public void exitGame(){
        System.out.println("Keluar dari game. Sampai jumpa!");
        System.exit(0);
    }

    public void help(){
        System.out.println("Help:");
    }

    public void startGame(){
        Scanner sc = new Scanner(System.in);
    
    }

    public void setPekerjaan(String pekerjaanWish) throws PekerjaanError{
        int randomnum;
        if(pekerjaan == null){
            Random rand = new Random()
            randomnum = rand.nextInt(7);
            if(randomnum == 0){
                pekerjaan = "a dst"
            }
        } else{
            if(kerjatime > 720){
                money -= pekerjaan.getGaji()/2;
                pekerjaan = pekerjaanWish;
                pekerjaanStart = LocalDateTime.now();
            } else{
                throw new PekerjaanError("Belum bisa mengganti pekerjaan.")
            }
        }
    }
    
}
