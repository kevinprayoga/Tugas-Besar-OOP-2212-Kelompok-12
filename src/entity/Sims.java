
package entity;

import exceptions.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Sims implements AksiAktif{
    private int mood, money,kesehatan,kekenyangan, gajiBank,bonusInc;
    private LocalDateTime endTime,startTime;
    private boolean isBusy;
    private String activity;


    
    
    
    
    // Implementasi aksi aktif
    public void kerja(int time) throws NotEnoughKesejahteraan{
        if(mood-(time/30*10) <= 0){
            throw new NotEnoughKesejahteraan("Sim tidak cukup mood untuk bekerja selama itu!");
        } else if(kekenyangan - (time/30*10) <= 0){
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bekerja selama itu!");
        } else{
        startTime = LocalDateTime.now();
        endTime = startTime.plusSeconds(time);
        isBusy = true;
        activity = "kerja";
        }
    }

    public void olahraga(int time) throws NotEnoughKesejahteraan {
        kesehatan += (time / 20 * 5);
        kekenyangan -= (time / 20 * 5);
        mood += (time / 20 * 10);
        if(kekenyangan - (time/20*10) <= 0){
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk berolahraga selama itu!");
        } else{
            startTime = LocalDateTime.now();
            endTime = startTime.plusSeconds(time);
            isBusy = true;
            activity = "olahraga";
        }
    }

    public void 
    public void update(){
        if (activity.equals("kerja")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                activity = "";
            } else{
                int seconds = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), startTime); 
                mood -= (seconds/30*10);
                kekenyangan -= (seconds/30*10);
                gajiBank += seconds - (seconds%30);
                money += ((gajiBank / 240)*(pekerjaan.getGaji()))*((100+getBonusInc())/100);
                gajiBank = gajiBank % 240;
                startTime.plusSeconds(seconds - (seconds%30));
            }
        } else if (activity.equals("olahraga")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                activity = "";
            } else{
                int seconds = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), startTime); 
                mood += (seconds/20*10);
                kekenyangan -= (seconds/20*5);
                kesehatan += (seconds/20*5);
                startTime.plusSeconds(seconds - (seconds%20));
            }
        }
    }

}


