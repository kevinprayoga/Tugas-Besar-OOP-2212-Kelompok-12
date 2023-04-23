import java.time.LocalDateTime;
import java.time.*;
import java.time.temporal.ChronoUnit;
public class ImplementAksiAktif implements AksiAktif{
    /*  Alternatif 1
    public void kerja(int time) {
        Thread.sleep(time * 1000);
        money += pekerjaan.getGaji();
        kekenyangan -= (time / 30 * 10);
        mood -= (time / 30 * 10);
    } */

    

    /*/ Alternatif 2
    public void kerja(int time) {
        for (int i = 0; i < time; i++) {
            Thread.sleep(1000);
            if (time % 30 == 0) {
                kekenyangan -= (10);
                mood -= (10);
            }
            if (time % 240 == 0) {
                money += pekerjaan.getGaji();
            }
        }
    }
    */
    int mood, money,kesehatan,kekenyangan, gajiBank;
    LocalDateTime endTime,startTime;
    boolean isBusy;
    String activity;

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
        // money += pekerjaan.getGaji()*((100+bonusInc)/100)
        }
    }

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

}
