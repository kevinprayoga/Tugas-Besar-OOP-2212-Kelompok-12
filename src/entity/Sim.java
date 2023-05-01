package entity;

import exceptions.*;
import pekerjaan.Pekerjaan;
import pekerjaan.PekerjaanPrinter;

import java.io.NotActiveException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Sim implements AksiAktif, AksiPasif{
    Random rand = new Random();
    
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

    private int bonusInc;

    private Rumah rumah;
    private Ruangan ruangan;
    private NonMakanan inFrontNonMakanan;

    private int gajiBank;               // waktu leftover dari kerja
    private static int jumlahPasif;            // jumlah aksi pasif yang memerlukan waktu yang sedang berjalan

    private static ArrayList<Integer> timerPembelian = new ArrayList<Integer>(null);
    private static ArrayList<Sim> pembelianSim = new ArrayList<Sim>(null);
    private static ArrayList<Produk> pembelianProduk = new ArrayList<Produk>(null);
 
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
    public void kerja(int time) throws NotEnoughKesejahteraan,InterruptedException{
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

    public void olahraga(int time) throws NotEnoughKesejahteraan,InterruptedException {
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

    

    public void tidur(int time) throws ItemError,InterruptedException{
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

    public void makan(Makanan m) throws ItemError,InterruptedException{
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

    public void buangAir() throws ItemError,NotEnoughKesejahteraan,InterruptedException{
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

    public void woodworking(NonMakanan item) throws TidakCukupItem,InterruptedException{
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

    public void bath() throws ItemError,InterruptedException{
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

    public void meditate(int time) throws NotEnoughKesejahteraan,InterruptedException{
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

    public void read() throws ItemError,InterruptedException{
        if(inFrontNonMakanan.getAksi() != "Read"){
            throw new ItemError("Sim sedang tidak di depan rak buku!");
        } else{
            status = "read";
            for(int i = 0;i<20; i++){
                WaktuAlt.addSecond();
            }
            int randomnum = rand.nextInt(5);
            if(randomnum == 4){
                bonusInc += 10;
            }
            mood += 5;
        }
    }

    public void party() throws TidakCukupItem, InterruptedException{
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

    public void beliObjek(Produk o) throws ItemError,TidakCukupItem{
        if(o instanceof Makanan){
            throw new ItemError("Makanan hanya dapat dimasak menggunakan bahan makanan!");
        } else if (o instanceof NonMakanan){
            if(uang - ((NonMakanan) o).getHarga() < 0){
                throw new TidakCukupItem("Tidak cukup uang untuk membeli item tersebut!");
            } else{
                int waktuPengiriman = (rand.nextInt(5)+1)*30;
                pembelianSim.add(this);
                pembelianProduk.add(o);
                timerPembelian.add(waktuPengiriman);
            }
        } else if (o instanceof BahanMakanan){
            if(uang - ((BahanMakanan) o).getHarga() < 0){
                throw new TidakCukupItem("Tidak cukup uang untuk membeli item tersebut!");
            } else{
                int waktuPengiriman = (rand.nextInt(5)+1)*30;
                pembelianSim.add(this);
                pembelianProduk.add(o);
                timerPembelian.add(waktuPengiriman);
            }
        }
    }

    public void displayInventory(){
        inventory.printInventory();
    }

    public void installObject(Produk o, Posisi p);

    public int getTime() throws ItemError{
        if(inFrontNonMakanan.getAksi() != "Read"){
            throw new ItemError("Sim sedang tidak di depan jam!");
        } else{
            return WaktuAlt.getRemainTime();
        }
    }

    public void gamble(int money) throws TidakCukupItem{
        if(this.uang < money){
            throw new TidakCukupItem("Tidak cukup uang untuk berjudi sebanyak itu!");
        } else{
            if(rand.nextInt(10) >= 5){
                uang += money;
            } else{
                uang -= money;
            }
        }
    }

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
            status = "dead";
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

    public static void updatePembelian(){
        
    }

    public void berpindahRuangan(String namaRuangan) {
        this.ruangan = this.rumah.getRuangan(namaRuangan);
    }
}