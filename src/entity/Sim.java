package entity;

import exceptions.*;
import pekerjaan.Pekerjaan;
import pekerjaan.PekerjaanPrinter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Sim implements AksiAktif, AksiPasif{
    private String namaLengkap;
    private Pekerjaan pekerjaan;
    private PekerjaanPrinter jobPrinter;
    private int uang;
    private Inventory inventory;
    private int kekenyangan;
    private int mood;
    private int kesehatan;
    private String status;

    private int wood;
    private int timeSinceTidur;
    private int timeSinceBAir;
    private int addSimCD;
    private int bonusInc;

    private Rumah rumah;
    private Ruangan ruangan;

    private int gajiBank;
    private LocalDateTime endTime,startTime, duration;
    private boolean isBusy;

    // Konstruktor
    public Sim(String nama) {
        namaLengkap = nama;
        kekenyangan = 80;
        mood = 80;
        kesehatan = 80;
        uang = 100;
    }

    // Getter
    public String getNamaLengkap() {
        return namaLengkap;
    }

    public Pekerjaan getPekerjaan() {
        return pekerjaan;
    }
    
    public int getUang() {
        return uang;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getKekenyangan() {
        return kekenyangan;
    }

    public int getMood() {
        return mood;
    }

    public int getKesehatan() {
        return kesehatan;
    }

    public String getStatus() {
        return status;
    }

    public Rumah getRumah() {
        return rumah;
    }

    public Ruangan getRuangan() {
        return ruangan;
    }
    
    // Implementasi aksi aktif
    public void kerja(int time) throws NotEnoughKesejahteraan{
        if(mood - (time/30*10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup mood untuk bekerja selama itu!");
        } else if(kekenyangan - (time/30*10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bekerja selama itu!");
        } else {
            startTime = LocalDateTime.now();
            endTime = startTime.plusSeconds(time);
            isBusy = true;
            status = "kerja";
            int lipatan = time / 40;
            uang += (pekerjaan.getGaji()*lipatan);
            mood -=(time/30*10);
            kekenyangan -= (time/30*10);
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
            status = "olahraga";
        }
    }

    public void update(){
        if (status.equals("kerja")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                status = "";
            } else{
                int seconds = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), startTime); 
                mood -= (seconds/30*10);
                kekenyangan -= (seconds/30*10);
                gajiBank += seconds - (seconds%30);
                uang += ((gajiBank / 240)*(pekerjaan.getGaji()))*((100+getBonusInc())/100);
                gajiBank = gajiBank % 240;
                startTime.plusSeconds(seconds - (seconds%30));
            }
        } else if (status.equals("olahraga")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                status = "";
            } else{
                int seconds = (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), startTime); 
                mood += (seconds/20*10);
                kekenyangan -= (seconds/20*5);
                kesehatan += (seconds/20*5);
                startTime.plusSeconds(seconds - (seconds%20));
            }
        }
    }

    public void tidur(int time);

    public void makan(Makanan m);

    public void berkunjung(int time, Rumah r);

    public void buangAir();

    public int getTimeSTidur();

    public int getTimeSBAir();

    public int getSimCD();

    public int getBonusInc();

    public void vacation();

    public void woodworking(NonMakanan item);

    public void bath();

    public void meditate(int time);

    public void read();

    public void party();

    // Implementasi aksi pasif
    public void upgradeRumah(Ruangan r, String arah, String nama);

    public void beliObjek(Objek o);

    public void moveRoom(Ruangan r);

    public void moveTo(Objek o);

    public void displayInventory();

    public void installObject(Objek o, Posisi p);

    public int getTime();

    public void gamble(int money);
}