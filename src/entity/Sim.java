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
    private int timeEmployed = 0;

    private int timeTidur;
    private int dayTidur;
    private boolean kesejahAltTidur;

    private int timeMakan = -1; // time -1 berarti tidak makan atau sudah buang air untuk makan sebelumnya
    private int dayMakan;
    private boolean kesejahAltBAir;

    private int startTimeVacation;
    private int startDayVacation;

    private int bonusInc;

    private Rumah myRumah;
    private Rumah currentHouse;
    private Ruangan ruangan;
    private Posisi posisi;
    private String currentPosition; // "World" atau "Rumah"
    private NonMakanan inFrontNonMakanan;

    private int gajiBank; // waktu leftover dari kerja
    private int tidurBank;

    private int timerPembelian = -1;
    private Produk pembelianProduk;

    // Konstruktor
    public Sim(String nama, int charType, boolean isFromLoader) throws NameNotFoundException {

        if (nama.length() < 3) {
            throw new NameNotFoundException("Name can not be null!");
        }

        namaLengkap = nama;
        kekenyangan = 80;
        mood = 80;
        kesehatan = 80;
        uang = 100;

        timeTidur = Waktu.getTime();
        dayTidur = Waktu.getDay();
        kesejahAltBAir = false;
        kesejahAltTidur = false;

        this.charType = charType;
        currentPosition = "World";
        status = "Gabut";
        posisi = new Posisi(0, 0);

        inventory = new Inventory();
        this.jobPrinter = new PekerjaanPrinter();

        if (!isFromLoader) {
            inventory.addItem(new NonMakanan("Kasur Single Size"));
            inventory.addItem(new NonMakanan("Toilet"));
            inventory.addItem(new NonMakanan("Kompor Gas"));
            inventory.addItem(new NonMakanan("Meja dan Kursi"));
            inventory.addItem(new NonMakanan("Meja dan Kursi"));
        }
    }

    // Setter
    public void setPosisi(Posisi posisi) {
        this.posisi = posisi;
    }

    public void setMyRumah(Rumah rumah){
        this.myRumah = rumah;
        this.currentHouse = rumah;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setCurrentActivity(String currentActivity) {
        this.status = currentActivity;
    }

    public void setCurrentHouse(Rumah currentHouse) {
        this.currentHouse = currentHouse;
    }

    public void setRuangan(Ruangan ruangan) {
        this.ruangan = ruangan;
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

    public Rumah getMyRumah() {
        return myRumah;
    }

    public Rumah getCurrentHouse() {
        return currentHouse;
    }

    public Ruangan getRuangan() {
        return ruangan;
    }

    public int getTimeEmployed(){
        return timeEmployed;
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

    public int getWood() {
        return wood;
    }

    public int getTimeTidur() {
        return timeTidur;
    }

    public int getDayTidur() {
        return dayTidur;
    }

    public boolean getKesejahAltTidur() {
        return kesejahAltTidur;
    }

    public int getTimeMakan() {
        return timeMakan;
    }

    public int getDayMakan() {
        return dayMakan;
    }

    public boolean getKesejahAltBAir() {
        return kesejahAltBAir;
    }

    public int getStartTimeVacation() {
        return startDayVacation;
    }

    public int getStartDayVacation() {
        return startDayVacation;
    }

    public int getGajiBank() {
        return gajiBank;
    }

    public void setMinusUang(int mines) {
        uang -= mines;
    }

    public void setTotalWaktuKerjaZero() {
        totalWaktuKerja = 0;
    }

    public int getTimerPembelian(){
        return timerPembelian;
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
    public void kerja(int time) throws NotEnoughKesejahteraan, InterruptedException, PekerjaanError, TimeError {
        if (mood - (time / 30 * 10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup mood untuk bekerja selama itu!");
        } else if (kekenyangan - (time / 30 * 10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bekerja selama itu!");
        } else if(timeEmployed < 720 && Waktu.getDay() != 0){
            throw new PekerjaanError("Sim belum cukup lama berada dalam pekerjaan ini untuk mulai bekerja!");
        } else if(time %120 != 0){
            throw new TimeError("Waktu harus kelipatan 120!");
        } else {
            status = "kerja";
            Waktu.setActionTimer(time);
            Waktu.addTime();
            status = "";
            mood += (time / 30 * 10);
            kekenyangan += (time / 30 * 10);
            gajiBank += time;
            uang += ((gajiBank / 240) * (jobPrinter.getTestPekerjaan().getGaji())) * ((100 + getBonusInc()) / 100);
            gajiBank = gajiBank % 240;
            totalWaktuKerja += time;
        }
    }

    public void olahraga(int time) throws NotEnoughKesejahteraan, InterruptedException,TimeError {
        if (kekenyangan - (time / 20 * 10) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk berolahraga selama itu!");
        } else if(time %20 != 0){
            throw new TimeError("Waktu harus kelipatan 20!");
        } else {
            status = "olahraga";
            Waktu.setActionTimer(time);
            Waktu.addTime();
            status = "";
            mood += (time / 20 * 10);
            kekenyangan -= (time / 20 * 5);
            kesehatan += (time / 20 * 5);
        }
    }

    public void tidur(int time) throws ItemError, InterruptedException,TimeError {
        if(time <180){
            throw new TimeError("Waktu harus lebih dari 240!");
        }else {
            status = "tidur";
            Waktu.setActionTimer(time);
            Waktu.addTime();
            status = "";
            tidurBank += time;
            mood += (tidurBank / 240*30) ;
            kesehatan += (tidurBank/240*20);
            tidurBank = tidurBank % 240;
            timeTidur = Waktu.getTime();
            dayTidur = Waktu.getDay();

        }
    }

    public <P extends Produk> void makan(P m) throws ItemError, InterruptedException {
        status = "makan";
        Waktu.setActionTimer(30);
        Waktu.addTime();
        setTimeMakan(Waktu.getTime());
        setDayMakan(Waktu.getDay());
        status = "";
        if (m instanceof Makanan) {
            kekenyangan += ((Makanan) m).getKekenyangan();
        } else if (m instanceof BahanMakanan) {
            kekenyangan += ((BahanMakanan) m).getKekenyangan();
        } else {
            throw new ItemError("Item yang dimasukkan bukan makanan!");
        }
    }

    public void berkunjung(Rumah homeVisit) throws NotEnoughKesejahteraan, InterruptedException {
        if (homeVisit.equals(myRumah)) return;
        double timeCost = Math.sqrt(((homeVisit.getPosisi().getX())-(currentHouse.getPosisi().getX()))^2 + ((homeVisit.getPosisi().getY())-(currentHouse.getPosisi().getY()))^2);
        int time = (int) timeCost/30 * 10;
        int timeBerkunjung = (int) timeCost;
        if (kekenyangan - (time) <= 0) {
            throw new NotEnoughKesejahteraan("Tidak cukup kekenyangan untuk berkunjung!");
        } else {
            status = "berkunjung";
            System.out.println(currentHouse.getPosisi().getX() + " " + currentHouse.getPosisi().getY() + " " + homeVisit.getPosisi().getX() + " " + homeVisit.getPosisi().getY());
            System.out.println("Sim sedang berkunjung ke rumah " + homeVisit.getOwner().getNamaLengkap() + " dari " + currentHouse.getOwner().getNamaLengkap() + " dengan waktu" + timeCost);
            Waktu.setActionTimer(timeBerkunjung);
            Waktu.addTime();
            status = "";
            kekenyangan -= time;
            mood += time;
        }
    }

    public void buangAir() throws ItemError, NotEnoughKesejahteraan, InterruptedException {
        if (kekenyangan - 20 <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup makan untuk buang air!");
        } else {
            status = "buang air";
            Waktu.setActionTimer(10);
            Waktu.addTime();
            status = "";
            kekenyangan -= 20;
            mood += 10;
        }
    }

    public void masak(Makanan m) throws TidakCukupItem,InterruptedException{
        boolean canCook = true;
        for(String s: m.getBahan()){
            if(inventory.getItemValue(s) == 0){
                canCook = false;
            }
        }
        if(canCook == false){
            throw new TidakCukupItem("Tidak cukup bahan untuk memasak itu!");
        } else{
            status = "memasak";
            Waktu.setActionTimer((int) 1.5*m.getKekenyangan());
            Waktu.addTime();
            status = "";
            for(String s: m.getBahan()){
                Produk b = inventory.getItem(s);
            }
            inventory.addItem(m);
            mood += 10;
        }
    }

    // aksi pasif khusus
    public void nubes() throws InterruptedException {
        status = "nubes";
        Waktu.setActionTimer(720);
        Waktu.addTime();
        status = "";
        mood = 0;
    }

    public void woodworking(NonMakanan item) throws TidakCukupItem, InterruptedException {
        if (wood < item.getHarga()) {
            throw new TidakCukupItem("Tidak cukup wood untuk woodworking!");
        } else {
            wood -= item.getHarga();
            status = "woodworking";
            Waktu.setActionTimer(item.getHarga()/2);
            Waktu.addTime();
            mood += 10;
            inventory.addItem(item);
        }
    }

    public void bath() throws ItemError, InterruptedException {
        status = "bath";
        Waktu.setActionTimer(5);
        Waktu.addTime();
        mood += 5;
        kesehatan += 5;
    }

    public void meditate(int time) throws NotEnoughKesejahteraan, InterruptedException, TimeError {
        if (kekenyangan - (time / 30 * 5) <= 0) {
            throw new NotEnoughKesejahteraan("Sim tidak cukup kenyang untuk bermeditasi selama itu!");
        } else if(time%30 != 0){
            throw new TimeError("Meditasi harus dilakukan dengan waktu kelipatan 30!");
        } else {
            status = "meditasi";
            Waktu.setActionTimer(time);
            Waktu.addTime();
            mood += (time / 30 * 10);
            kesehatan += (time / 30 * 5);
            kekenyangan -= (time / 30 * 5);
        }
    }

    public void read() throws ItemError, InterruptedException {
        status = "read";
        Waktu.setActionTimer(10);
        Waktu.addTime();
        int randomnum = rand.nextInt(5);
        if (randomnum == 4) {
            bonusInc += 10;
        }
        mood += 5;
    }

    public void party() throws TidakCukupItem, InterruptedException {
        if (uang < 100) {
            throw new TidakCukupItem("Tidak cukup uang untuk party!");
        } else {
            uang -= 100;
            status = "party";
            Waktu.setActionTimer(120);
            Waktu.addTime();
            kekenyangan += 80;
            mood += 80;
        }
    }

    // Implementasi aksi pasif
    public void upgradeRumah(int x, int y, String nama) throws TidakCukupItem, InterruptedException,ExistingOrder{
        if (uang < 10) {
            throw new TidakCukupItem("Tidak cukup uang untuk Upgrade Rumah!");
        } else if(myRumah.getUpgradeTimer() != -1){
            throw new ExistingOrder("Sudah ada proses upgrade rumah pada rumah ini!");
        } else {
            try {
                if (myRumah.getRoomBuild().get(y, x) == 1) { // meriksa kalau 2 artinya ruangan available untuk diisi
                    uang -= 10;
                    myRumah.setUpgradeTimer(120);
                    myRumah.setUpgradeLokasi(x, y);
                    myRumah.setUpgradeNama(nama);
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
        this.ruangan.addObjek(loc, o);
        inventory.getItem(o.getNamaProduk());
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
        System.out.println(((dayTidur * 720 + timeTidur) - (Waktu.getDay() * 720 + Waktu.getTime())));
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
        wood += rand.nextInt(5);
        if ((((Waktu.getDay() * 720 + Waktu.getTime()) - (dayTidur * 720 + timeTidur)) >= 600) && kesejahAltTidur == false) {
            mood -= 5;
            kesehatan -= 5;
            kesejahAltTidur = true;
        } else if ((((Waktu.getDay() * 720 + Waktu.getTime()) - (dayTidur * 720 + timeTidur)) <= 600) && kesejahAltTidur == true) {
            mood += 5;
            kesehatan += 5;
            kesejahAltTidur = false;
        }
        if (timeMakan != -1 && (((Waktu.getDay() * 720 + Waktu.getTime()) - (dayMakan * 720 + timeMakan)) >= 240)&& kesejahAltBAir == false) {
            kesehatan -= 5;
            mood -= 5;
            kesejahAltBAir = true;
        } else if (timeMakan != -1 && ((Waktu.getDay() * 720 + Waktu.getTime()) - (dayMakan * 720 + timeMakan)) <= 240
                && kesejahAltBAir == true) {
            kesehatan += 5;
            mood += 5;
            kesejahAltBAir = false;
        } 
        if (mood == 100 && kesejahAltBAir){
            mood = 95;
        } 
        if (kesehatan == 100 && kesejahAltBAir){
            kesehatan = 95;
        }
        if (mood == 100 && kesejahAltTidur){
            mood = 95;
        } 
        if (kesehatan == 100 && kesejahAltTidur){
            kesehatan = 95;
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

        // cekHidup
        //
        //
        if (kesehatan <= 0 || mood <= 0 || kekenyangan <= 0) {
            status = "dead";
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

        if(myRumah.getUpgradeTimer() != -1){
            myRumah.setUpgradeTimer(myRumah.getUpgradeTimer()-time);
            if(myRumah.getUpgradeTimer()<=0){
                myRumah.setUpgradeTimer(0);
            }
        }

        if(myRumah.getUpgradeTimer() == 0){
            myRumah.setUpgradeTimer(-1);
            myRumah.createRuangan(myRumah.getUpgradeLokasi()[0], myRumah.getUpgradeLokasi()[1], myRumah.getUpgradeNama());
        }

        timeEmployed += time;
    }

    public void moveRuangan(String namaRuangan) {
        Ruangan r = this.myRumah.getRuangan(namaRuangan);
        this.ruangan.removeSim(this);
        this.ruangan = r;
        this.ruangan.addSim(this);
    }

    public void setKekenyangan(int kekenyangan) {
        this.kekenyangan = kekenyangan;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setKesehatan(int kesehatan) {
        this.kesehatan = kesehatan;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public void setTotalWaktuKerja(int totalWaktuKerja) {
        this.totalWaktuKerja = totalWaktuKerja;
    }

    public void setTimeEmployed(int timeEmployed) {
        this.timeEmployed = timeEmployed;
    }

    public void setTimeTidur(int timeTidur) {
        this.timeTidur = timeTidur;
    }

    public void setDayTidur(int dayTidur) {
        this.dayTidur = dayTidur;
    }

    public void setKesejahAltTidur(Boolean kesejahAltTidur) {
        this.kesejahAltTidur = kesejahAltTidur;
    }

    public void setTimeMakan(int timeMakan) {
        this.timeMakan = timeMakan;
    }

    public void setDayMakan(int dayMakan) {
        this.dayMakan = dayMakan;
    }

    public void setKesejahAltBAir(Boolean kesejahAltBAir) {
        this.kesejahAltBAir = kesejahAltBAir;
    }

    public void setStartTimeVacation(int startTimeVacation) {
        this.startTimeVacation = startTimeVacation;
    }

    public void setStartDayVacation(int startDayVacation) {
        this.startDayVacation = startDayVacation;
    }

    public void setBonusInc(int bonusInc) {
        this.bonusInc = bonusInc;
    }

    public void setGajiBank(int gajiBank) {
        this.gajiBank = gajiBank;
    }
}