package entity;

import exceptions.*;
import pekerjaan.Pekerjaan;
import pekerjaan.PekerjaanPrinter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

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

    private int timeTidur;
    private int dayTidur;
    private boolean kesejahAltTidur;
    private int timeMakan =-1;              // time -1 berarti tidak makan atau sudah buang air untuk makan sebelumnya
    private int dayMakan;
    private boolean kesejahAltBAir;

    private int startTimeVacation;
    private int startDayVacation;

    private int addSimCD;
    private int bonusInc;

    private Rumah rumah;
    private Ruangan ruangan;
    private NonMakanan inFrontNonMakanan;

    private int gajiBank;               // waktu leftover dari kerja
    private static int jumlahPasif;            // jumlah aksi pasif yang memerlukan waktu yang sedang berjalan

    private static ArrayList<Thread> threads = new ArrayList<Thread>(null);

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

    public static int getJumlahPasif(){
        return jumlahPasif;
    }
    
    // Implementasi aksi aktif
    public void kerja(int time) throws NotEnoughKesejahteraan{
        if(mood - (time/30*10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup mood untuk bekerja selama itu!");
        } else if(kekenyangan - (time/30*10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bekerja selama itu!");
        } else {
            status = "kerja";
            for(int i = 0;i<time; i++){
                WaktuAlt.addSecond();
            }
            status = "";
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
            for(int i = 0;i<time; i++){
                WaktuAlt.addSecond();
            }
            status = "";
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
            for(int i = 0;i<time; i++){
                WaktuAlt.addSecond();
            }
            status = "";
            mood += (time/240*30);
            kesehatan += (time/240*20);
            timeTidur = WaktuAlt.getTime();
            dayTidur = WaktuAlt.getDay();
            
        }
    }

    public void makan(Makanan m) throws ItemError{
        if (inFrontNonMakanan.getAksi() != "Makan"){
            throw new ItemError("Sim tidak sedang berada di depan meja kursi!");
        } else{
            status = "makan";
            for(int i = 0;i<30; i++){
                WaktuAlt.addSecond();
            }
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
                throw new NotEnoughKesejahteraan("Sim tidak cukup makan untuk buang air!");
            } else {
                status = "buang air";
                for(int i = 0;i<30; i++){
                    WaktuAlt.addSecond();
                }
                kekenyangan -= 20;
                mood += 10;
            }
        }
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
            startTimeVacation = WaktuAlt.getTime();
            startDayVacation = WaktuAlt.getDay();
            kekenyangan = 100;
            mood = 100;
            kesehatan = 100;
            timeMakan = -1;
            timeTidur = WaktuAlt.getTime();
            dayTidur = WaktuAlt.getDay();
        }
    }

    public void woodworking(NonMakanan item){
        if(wood < item.getHarga()){
            throw new TidakCukupItem("Tidak cukup wood untuk woodworking!");
        } else{
            wood -= item.getHarga();
            status = "woodworking";
            for(int i = 0;i<item.getHarga()/2; i++){
                WaktuAlt.addSecond();
            }
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
            for(int i = 0;i<10; i++){
                WaktuAlt.addSecond();
            }
            mood += 5;
            kesehatan += 5;
        }
    }

    public void meditate(int time){
        if(kekenyangan - (time/30*5) <= 0){
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bermeditasi selama itu!");
        } else{
            status = "meditasi";
            for(int i = 0;i<time; i++){
                WaktuAlt.addSecond();
            }
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
            for(int i = 0;i<20; i++){
                WaktuAlt.addSecond();
            }
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
            for(int i = 0;i<120; i++){
                WaktuAlt.addSecond();
            }
            kekenyangan += 80;
            mood += 80;
        }
    }

    // Implementasi aksi pasif
    public void upgradeRumah(Ruangan r, String arah, String nama);

    public void beliObjek(Objek o);

    public void displayInventory(){
        
    }

    public void installObject(Objek o, Posisi p);

    public int getTime();

    public void gamble(int money);

    public void update(){

        // Alter berdasarkan waktu sejak buang air dan tidur
        if(((dayTidur*720 + timeTidur) - (WaktuAlt.getDay()*720 + WaktuAlt.getTime())) >= 600 && !kesejahAltTidur){
            mood -= 5;
            kesehatan -= 5;
            kesejahAltTidur = true;
        } else if(((dayTidur*720 + timeTidur) - (WaktuAlt.getDay()*720 + WaktuAlt.getTime())) <= 600 && kesejahAltTidur){
            mood += 5;
            kesehatan += 5;
            kesejahAltTidur = false;
        }
        if(timeMakan != -1 && ((dayMakan*720 + timeMakan) - (WaktuAlt.getDay()*720 + WaktuAlt.getTime())) >= 240 && !kesejahAltBAir){
            kesehatan -= 5;
            mood -= 5;
            kesejahAltBAir = true;
        } else if(timeMakan != -1 && ((dayMakan*720 + timeMakan) - (WaktuAlt.getDay()*720 + WaktuAlt.getTime())) <= 240 && kesejahAltBAir){
            kesehatan += 5;
            mood += 5;
            kesejahAltBAir = false;
        }

        // cekHidup
        //
        //
        if(kesehatan <= 0 || mood <= 0 || kekenyangan <= 0){

        }

        //kesejahteraan < 100
        if(kesehatan > 100 ){
            kesehatan = 100;
        }
        if(mood >= 100){
            mood = 100;
        }
        if(kekenyangan >= 100){
            kekenyangan = 100;
        }
        if(status == "vacation" && ((WaktuAlt.getDay()*720+WaktuAlt.getTime()) - (startDayVacation*720 +startTimeVacation)) >= 2160){
            status = "";
            timeTidur = WaktuAlt.getTime();
            dayTidur = WaktuAlt.getDay();
        }
        
    }
}