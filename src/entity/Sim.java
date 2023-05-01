package entity;

import exceptions.*;
import util.UtilityTool;

import java.awt.image.BufferedImage;
import java.io.NotActiveException;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Sim implements AksiAktif, AksiPasif {
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

    private int charType;
    private int totalWaktuKerja;

    private int timeTidur;
    private int dayTidur;
    private boolean kesejahAltTidur;

    private int timeMakan = -1;              // time -1 berarti tidak makan atau sudah buang air untuk makan sebelumnya
    private int dayMakan;
    private boolean kesejahAltBAir;

    private int startTimeVacation;
    private int startDayVacation;

    private int bonusInc;

    private Rumah rumah;
    private Ruangan ruangan;
    private Posisi posisi;
    private String currentPosition; // "World" atau "Rumah"
    private String currentActivity;
    private NonMakanan inFrontNonMakanan;

    private int gajiBank;               // waktu leftover dari kerja

    private static ArrayList<Integer> timerPembelian = new ArrayList<Integer>();
    private static ArrayList<Sim> pembelianSim = new ArrayList<Sim>();
    private static ArrayList<Produk> pembelianProduk = new ArrayList<Produk>();

    // Konstruktor
    public Sim(String nama, int charType) {
        namaLengkap = nama;
        kekenyangan = 80;
        mood = 80;
        kesehatan = 80;
        uang = 100;

        this.charType = charType;
        currentPosition = "World";
        currentActivity = "Gabut";
        posisi = new Posisi(0, 0);
    }

    // Setter
    public void setPosisi(Posisi posisi) {
        this.posisi = posisi;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
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

    public int getCharType() {
        return charType;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public Posisi getPosisi() {
        return posisi;
    }

    public String getCurrentActivity() {
        return currentActivity;
    }

    public BufferedImage getCharacter() {
        BufferedImage character;
        switch (charType) {
            case 0:
                character = UtilityTool.loadImage("res/image/sims/bnmo/BNMO_Down_Right (1).png");
                break;
            case 1:
                character = UtilityTool.loadImage("res/image/sims/hans/Hans_Down_Right (1).png");
                break;
            case 2:
                character = UtilityTool.loadImage("res/image/sims/ivan/Ivan_Down_Right (1).png");
                break;
            case 3:
                character = UtilityTool.loadImage("res/image/sims/kevin/Kevin_Down_Right (1).png");
                break;
            case 4:
                character = UtilityTool.loadImage("res/image/sims/nicholas/Nic_Down_Right (1).png");
                break;
            case 5:
                character = UtilityTool.loadImage("res/image/sims/ojan/Ojan_Down_Right (1).png");
                break;
            case 6:
                character = UtilityTool.loadImage("res/image/sims/rana/Rana_Down_Right (1).png");
                break;
            default:
                character = UtilityTool.loadImage("res/image/sims/bnmo/BNMO_Down_Right (1).png");
                break;
        }
        return character;
    }

    // Implementasi aksi aktif
    public void kerja(int time) throws NotEnoughKesejahteraan, InterruptedException {
        if (mood - (time / 30 * 10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup mood untuk bekerja selama itu!");
        } else if (kekenyangan - (time / 30 * 10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bekerja selama itu!");
        } else {
            status = "kerja";
            for (int i = 0; i < time; i++) {
                Waktu.addSecond();
            }
            status = "";
            mood -= (time / 30 * 10);
            kekenyangan -= (time / 30 * 10);
            gajiBank += time;
            uang += ((gajiBank / 240) * (jobPrinter.getTestPekerjaan().getGaji())) * ((100 + getBonusInc()) / 100);
            gajiBank = gajiBank % 240;
            totalWaktuKerja += time;
        }
    }

    public void olahraga(int time) throws NotEnoughKesejahteraan, InterruptedException {
        if (kekenyangan - (time / 20 * 10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk berolahraga selama itu!");
        } else {
            status = "olahraga";
            for (int i = 0; i < time; i++) {
                Waktu.addSecond();
            }
            status = "";
            mood += (time / 20 * 10);
            kekenyangan -= (time / 20 * 5);
            kesehatan += (time / 20 * 5);
        }
    }

    public void tidur(int time) throws ItemError, InterruptedException {
        if (inFrontNonMakanan.getAksi() != "Tidur") {
            throw new ItemError("Sim tidak sedang berada di depan tempat tidur!");
        } else {
            status = "tidur";
            for (int i = 0; i < time; i++) {
                Waktu.addSecond();
            }
            status = "";
            mood += (time / 240 * 30);
            kesehatan += (time / 240 * 20);
            timeTidur = Waktu.getTime();
            dayTidur = Waktu.getDay();

        }
    }

    public void makan(Makanan m) throws ItemError, InterruptedException {
        if (inFrontNonMakanan.getAksi() != "Makan") {
            throw new ItemError("Sim tidak sedang berada di depan meja kursi!");
        } else {
            status = "makan";
            for (int i = 0; i < 30; i++) {
                Waktu.addSecond();
            }
            kekenyangan += m.getKekenyangan();
        }
    }

    public void berkunjung(Rumah r) {

    }

    public void buangAir() throws ItemError, NotEnoughKesejahteraan, InterruptedException {
        if (inFrontNonMakanan.getAksi() != "Buang Air") {
            throw new ItemError("Sim sedang tidak di toilet!");
        } else {
            if (kekenyangan - 20 <= 0) {
                throw new NotEnoughKesejahteraan("Sim tidak cukup makan untuk buang air!");
            } else {
                status = "buang air";
                for (int i = 0; i < 30; i++) {
                    Waktu.addSecond();
                }
                kekenyangan -= 20;
                mood += 10;
            }
        }
    }

    public int getBonusInc() {
        return bonusInc;
    }

    // aksi pasif khusus
    public void vacation() throws TidakCukupItem {
        if (uang < 1800) {
            throw new TidakCukupItem("Tidak cukup uang untuk berlibur!");
        } else {
            uang -= 1800;
            status = "vacation";
            startTimeVacation = Waktu.getTime();
            startDayVacation = Waktu.getDay();
            kekenyangan = 100;
            mood = 100;
            kesehatan = 100;
            timeMakan = -1;
            timeTidur = Waktu.getTime();
            dayTidur = Waktu.getDay();
        }
    }

    public void woodworking(NonMakanan item) throws TidakCukupItem, InterruptedException {
        if (wood < item.getHarga()) {
            throw new TidakCukupItem("Tidak cukup wood untuk woodworking!");
        } else {
            wood -= item.getHarga();
            status = "woodworking";
            for (int i = 0; i < item.getHarga() / 2; i++) {
                Waktu.addSecond();
            }
            mood += 10;
            // add inventory
            // inventory.
        }
    }

    public void bath() throws ItemError, InterruptedException {
        if (inFrontNonMakanan.getAksi() != "Bath") {
            throw new ItemError("Sim sedang tidak di shower!");
        } else {
            status = "bath";
            for (int i = 0; i < 10; i++) {
                Waktu.addSecond();
            }
            mood += 5;
            kesehatan += 5;
        }
    }

    public void meditate(int time) throws NotEnoughKesejahteraan, InterruptedException {
        if (kekenyangan - (time / 30 * 5) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bermeditasi selama itu!");
        } else {
            status = "meditasi";
            for (int i = 0; i < time; i++) {
                Waktu.addSecond();
            }
            mood += (time / 30 * 10);
            kesehatan += (time / 30 * 5);
            kekenyangan -= (time / 30 * 5);
        }
    }

    public void read() throws ItemError, InterruptedException {
        if (inFrontNonMakanan.getAksi() != "Read") {
            throw new ItemError("Sim sedang tidak di depan rak buku!");
        } else {
            status = "read";
            for (int i = 0; i < 20; i++) {
                Waktu.addSecond();
            }
            int randomnum = rand.nextInt(5);
            if (randomnum == 4) {
                bonusInc += 10;
            }
            mood += 5;
        }
    }

    public void party() throws TidakCukupItem, InterruptedException {
        if (uang < 100) {
            throw new TidakCukupItem("Tidak cukup uang untuk party!");
        } else {
            uang -= 500;
            status = "party";
            for (int i = 0; i < 120; i++) {
                Waktu.addSecond();
            }
            kekenyangan += 80;
            mood += 80;
        }
    }

    // Implementasi aksi pasif
    public void upgradeRumah(Ruangan r, String arah, String nama);

    public void beliObjek(Produk o) throws ItemError, TidakCukupItem {
        if (o instanceof Makanan) {
            throw new ItemError("Makanan hanya dapat dimasak menggunakan bahan makanan!");
        } else if (o instanceof NonMakanan) {
            if (uang - ((NonMakanan) o).getHarga() < 0) {
                throw new TidakCukupItem("Tidak cukup uang untuk membeli item tersebut!");
            } else {
                int waktuPengiriman = (rand.nextInt(5) + 1) * 30;
                uang -= ((NonMakanan) o).getHarga();
                pembelianSim.add(this);
                pembelianProduk.add(o);
                timerPembelian.add(waktuPengiriman);
            }
        } else if (o instanceof BahanMakanan) {
            if (uang - ((BahanMakanan) o).getHarga() < 0) {
                throw new TidakCukupItem("Tidak cukup uang untuk membeli item tersebut!");
            } else {
                int waktuPengiriman = (rand.nextInt(5) + 1) * 30;
                uang -= ((BahanMakanan) o).getHarga();
                pembelianSim.add(this);
                pembelianProduk.add(o);
                timerPembelian.add(waktuPengiriman);
            }
        }
    }

    public void displayInventory() {
        inventory.printInventory();
    }

    public void installObject(NonMakanan o, Ruangan r){
        NonMakanan z = (NonMakanan)inventory.getItem(o.getNamaProduk());

    }

    public int getTime() throws ItemError {
        if (inFrontNonMakanan.getAksi() != "Read") {
            throw new ItemError("Sim sedang tidak di depan jam!");
        } else {
            return Waktu.getRemainTime();
        }
    }

    public void gamble(int money) throws TidakCukupItem {
        if (this.uang < money) {
            throw new TidakCukupItem("Tidak cukup uang untuk berjudi sebanyak itu!");
        } else {
            if (rand.nextInt(10) >= 5) {
                uang += money;
            } else {
                uang -= money;
            }
        }
    }

    public void update(){
        // Alter berdasarkan waktu sejak buang air dan tidur
        if (((dayTidur * 720 + timeTidur) - (Waktu.getDay() * 720 + Waktu.getTime())) >= 600 && !kesejahAltTidur) {
            mood -= 5;
            kesehatan -= 5;
            kesejahAltTidur = true;
        } else if (((dayTidur * 720 + timeTidur) - (Waktu.getDay() * 720 + Waktu.getTime())) <= 600
                && kesejahAltTidur) {
            mood += 5;
            kesehatan += 5;
            kesejahAltTidur = false;
        }
        if (timeMakan != -1 && ((dayMakan * 720 + timeMakan) - (Waktu.getDay() * 720 + Waktu.getTime())) >= 240
                && !kesejahAltBAir) {
            kesehatan -= 5;
            mood -= 5;
            kesejahAltBAir = true;
        } else if (timeMakan != -1 && ((dayMakan * 720 + timeMakan) - (Waktu.getDay() * 720 + Waktu.getTime())) <= 240
                && kesejahAltBAir) {
            kesehatan += 5;
            mood += 5;
            kesejahAltBAir = false;
        }

        // cekHidup
        //
        //
        if (kesehatan <= 0 || mood <= 0 || kekenyangan <= 0) {
            status = "dead";
        }

        // kesejahteraan < 100
        if (kesehatan > 100) {
            kesehatan = 100;
        }
        if (mood >= 100) {
            mood = 100;
        }
        if (kekenyangan >= 100) {
            kekenyangan = 100;
        }
        if (status == "vacation"
                && ((Waktu.getDay() * 720 + Waktu.getTime()) - (startDayVacation * 720 + startTimeVacation)) >= 2160) {
            status = "";
            timeTidur = Waktu.getTime();
            dayTidur = Waktu.getDay();
        }

    }


    public void updatePembelian(){
        for(int i = 0;i<timerPembelian.size();i++){
            timerPembelian.set(i, timerPembelian.get(i)-1);
            if(timerPembelian.get(i) == 0){
                timerPembelian.remove(i);
                // add ke inventory
                (pembelianSim.get(i)).inventory.addItem(pembelianProduk.get(i));
                pembelianProduk.remove(i);
                pembelianSim.remove(i);
            }
        }

    }

    public void moveRuangan(String namaRuangan) {
        Ruangan r = this.rumah.getRuangan(namaRuangan);
        this.ruangan.removeSim(this);
        this.ruangan = r;
        this.ruangan.addSim(this);
    }
}