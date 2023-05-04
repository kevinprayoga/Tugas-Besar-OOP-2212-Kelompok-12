package entity;

import exceptions.*;
import util.UtilityTool;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.naming.NameNotFoundException;

public class Sim implements AksiAktif, AksiPasif {
    Random rand = new Random();

    private String namaLengkap;
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
    private int timeEmployed;

    private int timeTidur;
    private int dayTidur;
    private boolean kesejahAltTidur;

    private int timeMakan = -1; // time -1 berarti tidak makan atau sudah buang air untuk makan sebelumnya
    private int dayMakan;
    private boolean kesejahAltBAir;

    private int startTimeVacation;
    private int startDayVacation;

    private int bonusInc;

    private Rumah rumah;
    private Ruangan ruangan;
    private Posisi posisi;
    private String currentPosition; // "World" atau "Rumah"
    private NonMakanan inFrontNonMakanan;

    private int gajiBank; // waktu leftover dari kerja

    private int timerPembelian = -1;
    private Produk pembelianProduk;

    // Konstruktor
    public Sim(String nama, int charType) throws NameNotFoundException {

        if (nama.length() < 3) {
            throw new NameNotFoundException("Name can not be null!");
        }

        namaLengkap = nama;
        kekenyangan = 80;
        mood = 80;
        kesehatan = 80;
        uang = 100;

        this.charType = charType;
        currentPosition = "World";
        status = "Gabut";
        posisi = new Posisi(0, 0);

        inventory = new Inventory();
        this.jobPrinter = new PekerjaanPrinter();
    }

    // Setter
    public void setPosisi(Posisi posisi) {
        this.posisi = posisi;
    }

    public void setRumah(Rumah rumah){
        this.rumah = rumah;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentActivity(String currentActivity) {
        this.status = currentActivity;
    }

    // Getter
    public String getNamaLengkap() {
        return namaLengkap;
    }

    public Pekerjaan getPekerjaan() {
        return jobPrinter.getTestPekerjaan();
    }

    public PekerjaanPrinter getPekerjaanPrinter() {
        return jobPrinter;
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
        return status;
    }

    public int getTotalWaktuKerja(){
        return totalWaktuKerja;
    }

    public int getBonusInc() {
        return bonusInc;
    }

    public void setMinusUang(int mines) {
        uang -= mines;
    }

    public void setTotalWaktuKerjaZero() {
        totalWaktuKerja = 0;
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
    public void kerja(int time) throws NotEnoughKesejahteraan, InterruptedException, PekerjaanError {
        if (mood - (time / 30 * 10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup mood untuk bekerja selama itu!");
        } else if (kekenyangan - (time / 30 * 10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bekerja selama itu!");
        } else if(timeEmployed < 720 && Waktu.getDay() != 0){
            throw new PekerjaanError("Sim belum cukup lama berada dalam pekerjaan ini untuk mulai bekerja!");
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
            status = "";
            kekenyangan += m.getKekenyangan();
        }
    }

    public void berkunjung(Rumah homeVisit) throws NotEnoughKesejahteraan, InterruptedException {
        double timeCost = Math.sqrt(((homeVisit.getPosisi().getX())-(rumah.getPosisi().getX()))^2 + ((homeVisit.getPosisi().getY())-(rumah.getPosisi().getY()))^2);
        int time = (int) timeCost/30 * 10;
        if (kekenyangan - (time) <= 0) {
            throw new NotEnoughKesejahteraan("Tidak cukup kekenyangan untuk berkunjung!");
        } else {
            status = "berkunjung";
            for (int i = 0; i < 30; i++) {
                Waktu.addSecond();
            }
            status = "";
            kekenyangan -= time;
            mood += time;
        }
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
                status = "";
                kekenyangan -= 20;
                mood += 10;
            }
        }
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
            inventory.addItem(item);
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
    public void upgradeRumah(int x, int y, String nama) throws TidakCukupItem, InterruptedException,ExistingOrder{
        if (uang < 1500) {
            throw new TidakCukupItem("Tidak cukup uang untuk Upgrade Rumah!");
        } else if(rumah.getUpgradeTimer() != -1){
            throw new ExistingOrder("Sudah ada proses upgrade rumah pada rumah ini!");
        } else {
            try {
                if (rumah.getRoomBuild().get(x, y) == 2) { // meriksa kalau 2 artinya ruangan available untuk diisi
                    uang -= 1500;
                    rumah.setUpgradeTimer(1080);
                    rumah.setUpgradeLokasi(x, y);
                    rumah.setUpgradeNama(nama);
                }
            } catch (Exception e) {
                System.out.println("Ruangan tidak dapat ditempati!");
            }
        }
    }

    public void beliObjek(Produk o) throws ItemError, TidakCukupItem, ExistingOrder{
        if (o instanceof Makanan) {
            throw new ItemError("Makanan hanya dapat dimasak menggunakan bahan makanan!");
        } else if (o instanceof NonMakanan) {
            if (uang - ((NonMakanan) o).getHarga() < 0) {
                throw new TidakCukupItem("Tidak cukup uang untuk membeli item tersebut!");
            } else if(timerPembelian != -1){
                throw new ExistingOrder("Sudah ada order pemesanan barang!");
            } else {
                timerPembelian= (rand.nextInt(5) + 1) * 30;
                uang -= ((NonMakanan) o).getHarga();
                pembelianProduk = o;
            }
        } else if (o instanceof BahanMakanan) {
            if (uang - ((BahanMakanan) o).getHarga() < 0) {
                throw new TidakCukupItem("Tidak cukup uang untuk membeli item tersebut!");
            } else if(timerPembelian != -1){
                throw new ExistingOrder("Sudah ada order pemesanan barang!");
            } else {
                timerPembelian= (rand.nextInt(5) + 1) * 30;
                uang -= ((BahanMakanan) o).getHarga();
                pembelianProduk = o;
            }
        }
    }

    public void displayInventory() {
        inventory.printInventory();
    }

    public void installObject(NonMakanan o, Posisi loc) {
        NonMakanan barang = (NonMakanan) inventory.getItem(o.getNamaProduk());
        this.ruangan.addObjek(loc, barang);
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

    public void update(int time) {
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

        if(timerPembelian != -1){
            timerPembelian -= time;
            if(timerPembelian <= 0){
                timerPembelian = 0;
            }
        }

        if(timerPembelian == 0){
            timerPembelian -= 1;
            inventory.addItem((pembelianProduk));
        }

        if(rumah.getUpgradeTimer() != -1){
            rumah.setUpgradeTimer(rumah.getUpgradeTimer()-time);
            if(rumah.getUpgradeTimer()<=0){
                rumah.setUpgradeTimer(0);
            }
        }

        if(rumah.getUpgradeTimer() == 0){
            rumah.setUpgradeTimer(-1);
            rumah.createRuangan(rumah.getUpgradeLokasi()[0], rumah.getUpgradeLokasi()[1], rumah.getUpgradeNama());
        }

        timeEmployed += time;
    }

    public void moveRuangan(String namaRuangan) {
        Ruangan r = this.rumah.getRuangan(namaRuangan);
        this.ruangan.removeSim(this);
        this.ruangan = r;
        this.ruangan.addSim(this);
    }
}