package entity;

import exceptions.ItemError;
import exceptions.TidakCukupItem;
import exceptions.PekerjaanError;
import exceptions.NotEnoughKesejahteraan;

public interface AksiAktif {
    public void kerja(int time) throws NotEnoughKesejahteraan,InterruptedException;

    public void olahraga(int time) throws NotEnoughKesejahteraan,InterruptedException;

    public void tidur(int time) throws NotEnoughKesejahteraan,ItemError,InterruptedException;

    public void makan(Makanan m) throws ItemError,InterruptedException;

    public void berkunjung(Rumah r);

    public void buangAir() throws ItemError, NotEnoughKesejahteraan,InterruptedException;

    public int getBonusInc();

    public void vacation() throws TidakCukupItem;

    public void woodworking(NonMakanan item) throws InterruptedException,TidakCukupItem;

    public void bath() throws ItemError,InterruptedException;

    public void meditate(int time) throws NotEnoughKesejahteraan,InterruptedException;

    public void read() throws InterruptedException,ItemError;

    public void party() throws TidakCukupItem,InterruptedException;
}
