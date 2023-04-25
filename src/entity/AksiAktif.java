package entity;

public interface AksiAktif {
    public void kerja(int time) throws Exception;

    public void olahraga(int time) throws Exception;

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
}
