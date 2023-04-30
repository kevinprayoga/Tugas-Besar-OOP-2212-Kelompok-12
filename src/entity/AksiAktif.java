package entity;

import exceptions.ItemError;
import exceptions.TidakCukupItem;
import exceptions.PekerjaanError;
import exceptions.NotEnoughKesejahteraan;
import exceptions.TidakCukupItem;

public interface AksiAktif {
    public void kerja(int time) throws NotEnoughKesejahteraan;

    public void olahraga(int time) throws NotEnoughKesejahteraan;

    public void tidur(int time) throws NotEnoughKesejahteraan,ItemError;

    public void makan(Makanan m) throws ItemError;

    public void berkunjung(int time, Rumah r);

    public void buangAir() throws ItemError, NotEnoughKesejahteraan;

    public int getTimeSTidur();

    public int getTimeSBAir();

    public int getSimCD();

    public int getBonusInc();

    public void vacation() throws TidakCukupItem;

    public void woodworking(NonMakanan item);

    public void bath() throws ItemError;

    public void meditate(int time) throws NotEnoughKesejahteraan;

    public void read();

    public void party() throws TidakCukupItem;
}
