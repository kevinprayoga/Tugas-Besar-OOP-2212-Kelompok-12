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
            status = "kerja";
            Thread.sleep(time*1000);
            mood -= (time/30*10);
            kekenyangan -= (time/30*10);
            gajiBank += time;
            uang += ((gajiBank / 240)*(jobPrinter.getTestPekerjaan().getGaji()))*((100+getBonusInc())/100);
            gajiBank = gajiBank % 240;
        }
    }

    public void olahraga(int time) throws NotEnoughKesejahteraan {
        if(kekenyangan - (time/20*10) <= 0){
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk berolahraga selama itu!");
        } else{
            status = "olahraga";
            Thread.sleep(time*1000);
            mood += (time/20*10);
            kekenyangan -= (time/20*5);
            kesehatan += (time/20*5);
        }
    }

    

    public void tidur(int time) throws ItemError{
        if (inFrontNonMakanan.getAksi() != "Tidur"){
            throw new ItemError("Sim tidak sedang berada di depan tempat tidur!");
        } else{
            status = "tidur";
            Thread.sleep(time*1000);
            mood += (time/240*30);
            kesehatan += (time/240*20);
            timeTidur = LocalDateTime.now();
            
        }
    }

    public void makan(Makanan m) throws ItemError{
        if (inFrontNonMakanan.getAksi() != "Makan"){
            throw new ItemError("Sim tidak sedang berada di depan meja kursi!");
        } else{
            status = "makan";
            Thread.sleep(30*1000);
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
                status = "buang air";
                Thread.sleep(30*1000);
                kekenyangan -= 20;
                mood += 10;
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
    // aksi pasif khusus
    public void vacation() throws TidakCukupItem{
        if(uang < 1800){
            throw new TidakCukupItem("Tidak cukup uang untuk berlibur!");
        } else{
            uang -= 1800;
            status = "vacation";
            kekenyangan = 100;
            mood = 100;
            kesehatan = 100;
            timeMakan = LocalDateTime.now();
            timeTidur = LocalDateTime.now();
        }
    }

    public void woodworking(NonMakanan item){
        if(wood < item.getHarga()){
            throw new TidakCukupItem("Tidak cukup wood untuk woodworking!");
        } else{
            wood -= item.getHarga();
            status = "woodworking";
            Thread.sleep(item.getHarga()/2*1000);
            mood += 10;
            // add inventory
            //inventory.
        }
    }

    public void bath(){
        if(inFrontNonMakanan.getAksi() != "Bath"){
            throw new ItemError("Sim sedang tidak di shower!");
        } else{
            status = "bath";
            Thread.sleep(10*1000);
            mood += 5;
            kesehatan += 5;
        }
    }

    public void meditate(int time){
        if(kekenyangan - (time/30*5) <= 0){
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bermeditasi selama itu!");
        } else{
            status = "meditasi";
            Thread.sleep(time*1000);
            mood += (time/30*10);
            kesehatan += (time/30*5);
            kekenyangan -= (time/30*5);
        }
    }

    public void read(){
        if(inFrontNonMakanan.getAksi() != "Read"){
            throw new ItemError("Sim sedang tidak di depan rak buku!");
        } else{
            status = "read";
            Thread.sleep(20*1000);
            int randomnum = new Random(10);
            if(randomnum == 4){
                bonusInc += 10;
            }
            mood += 5;
        }
    }

    public void party(){
        if(uang < 100){
            throw new TidakCukupItem("Tidak cukup uang untuk party!");
        } else{
            uang -= 500;
            status = "party";
            Thread.sleep(120*1000);
            kekenyangan += 80;
            mood += 80;
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
        if(tim)
        
    }
}