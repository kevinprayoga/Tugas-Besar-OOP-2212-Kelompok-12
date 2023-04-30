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
    private LocalDateTime timeTidur;
    private LocalDateTime timeMakan;
    private int addSimCD;
    private int bonusInc;

    private Rumah rumah;
    private Ruangan ruangan;
    private NonMakanan inFrontNonMakanan;

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
            setActivity("kerja", time);
        }
    }

    public void olahraga(int time) throws NotEnoughKesejahteraan {
        if(kekenyangan - (time/20*10) <= 0){
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk berolahraga selama itu!");
        } else{
            setActivity("olahraga",time);
        }
    }

    

    public void tidur(int time) throws ItemError{
        if (inFrontNonMakanan.getAksi() != "Tidur"){
            throw new ItemError("Sim tidak sedang berada di depan tempat tidur!");
        } else{
            setActivity("tidur",time);
            timeTidur = LocalDateTime.now();
            
        }
    }

    public void makan(Makanan m) throws ItemError{
        if (inFrontNonMakanan.getAksi() != "Makan"){
            throw new ItemError("Sim tidak sedang berada di depan meja kursi!");
        } else{
            Thread.sleep(30*1000);
            System.out.println("Sim sudah selesai makan!");
            kekenyangan += m.getKekenyangan();
        }
    }

    public void berkunjung(Rumah r){
        
    }

    public void buangAir(){
        if(inFrontNonMakanan.getAksi() != "Buang Air"){
            throw new ItemError("Sim sedang tidak di toilet!");
        } else{
            if(kekenyangan - 20 <= 0) {
                throw new NotEnoughKesejahteraan("Sim tidak cukup mood untuk makan untuk buang air!");
            } else {
                setActivity("buang air", 10);
            }
        }
    }

    public int getTimeSTidur(){
        return (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), timeTidur);
    }

    public int getTimeSBAir(){
        return (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), timeMakan);
    }

    public int getSimCD(){
        return addSimCD;
    }

    public int getBonusInc(){
        return bonusInc;
    }

    public void vacation() throws TidakCukupItem{
        if(uang < 1800){
            throw new TidakCukupItem("Tidak cukup uang untuk berlibur!");
        } else{
            uang -= 1800;
            setActivity("vacation", 2160);
        }
    }

    public void woodworking(NonMakanan item){
        if(wood < item.getHarga()){
            throw new TidakCukupItem("Tidak cukup wood untuk woodworking!");
        } else{
            wood -= item.getHarga();
            setActivity("woodworking", item.getHarga());
        }
    }

    public void bath(){
        if(inFrontNonMakanan.getAksi() != "Bath"){
            throw new ItemError("Sim sedang tidak di shower!");
        } else{
            setActivity("bath", 10);
        }
    }

    public void meditate(int time){
        if(kekenyangan - (time/30*5) <= 0){
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bermeditasi selama itu!");
        } else{
            setActivity("meditasi",time);
        }
    }

    public void read(){
        if(inFrontNonMakanan.getAksi() != "Read"){
            throw new ItemError("Sim sedang tidak di depan rak buku!");
        }
        setActivity("read", 20);
    }

    public void party(){
        if(uang < 100){
            throw new TidakCukupItem("Tidak cukup uang untuk party!");
        } else{
            uang -= 1800;
            setActivity("party", 180);
        }
    }

    // Implementasi aksi pasif
    public void upgradeRumah(Ruangan r, String arah, String nama);

    public void beliObjek(Objek o);

    public void moveRoom(Ruangan r);

    public void moveTo(Objek o);

    public void displayInventory();

    public void installObject(Objek o, Posisi p);

    public int getTime();

    public void gamble(int money);

    public void update(){
        if (status.equals("kerja")){
            if(LocalDateTime.now().compareTo(endTime) <= 0){
                isBusy = false;
                status = "";
                int seconds = timeBetween(endTime, startTime);
                mood -= (seconds/30*10);
                kekenyangan -= (seconds/30*10);
                gajiBank += seconds - (seconds%30);
                uang += ((gajiBank / 240)*(jobPrinter.getTestPekerjaan().getGaji()))*((100+getBonusInc())/100);
                gajiBank = gajiBank % 240;
            } else{
                int seconds = timeBetween(LocalDateTime.now(), startTime); 
                mood -= (seconds/30*10);
                kekenyangan -= (seconds/30*10);
                gajiBank += seconds - (seconds%30);
                uang += ((gajiBank / 240)*(jobPrinter.getTestPekerjaan().getGaji()))*((100+getBonusInc())/100);
                gajiBank = gajiBank % 240;
                startTime.plusSeconds(seconds - (seconds%30));
            }
        } else if (status.equals("olahraga")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                status = "";
                int seconds = timeBetween(endTime, startTime); 
                mood += (seconds/20*10);
                kekenyangan -= (seconds/20*5);
                kesehatan += (seconds/20*5);
            } else{
                int seconds = timeBetween(LocalDateTime.now(), startTime); // for (Sim s: SimList){update()}
                mood += (seconds/20*10);
                kekenyangan -= (seconds/20*5);
                kesehatan += (seconds/20*5);
                startTime.plusSeconds(seconds - (seconds%20));                  
            }
        } else if(status.equals("tidur")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                status = "";
                int seconds = timeBetween(endTime, startTime); 
                mood += (seconds/240*30);
                kesehatan += (seconds/240*20);
            } else{
                int seconds = timeBetween(LocalDateTime.now(), startTime); 
                mood += (seconds/240*30);
                kesehatan += (seconds/240*20);
                startTime.plusSeconds(seconds - (seconds%20));
            }
        } else if(status.equals("makan")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                status = "";
            } 
        } else if(status.equals("buang air")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                status = "";
                kekenyangan -= 20;
                mood += 10;
            } 
        } else if(status.equals("vacation")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                status = "";
                kekenyangan = 100;
                mood = 100;
                kesehatan = 100;
                timeMakan = LocalDateTime.now();
                timeTidur = LocalDateTime.now();
            }
        } else if(status.equals("woodworking")){
            if(LocalDateTime.now().compareTo(endTime) >= 0){
                isBusy = false;
                status = "";
                mood += 10;
            } 
        } 
        }
    }


    public void setActivity(String status, int time){
        startTime = LocalDateTime.now();
        endTime = startTime.plusSeconds(time);
        isBusy = true;
        this.status = status;
    }
    public int timeBetween(LocalDateTime start, LocalDateTime end){
        return  (int) ChronoUnit.SECONDS.between(end, start); 
    }
}