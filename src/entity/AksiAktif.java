package entity;

import exceptions.ItemError;
import exceptions.TidakCukupItem;
import exceptions.TimeError;
import exceptions.NotEnoughKesejahteraan;
import exceptions.PekerjaanError;

public interface AksiAktif {
    public void kerja(int time) throws NotEnoughKesejahteraan,InterruptedException,PekerjaanError, TimeError;

    public void olahraga(int time) throws NotEnoughKesejahteraan,InterruptedException,TimeError;

    public void tidur(int time) throws NotEnoughKesejahteraan,ItemError,InterruptedException,TimeError;

    public <P extends Produk> void makan(P m) throws ItemError,InterruptedException;

    public void berkunjung(Rumah r) throws NotEnoughKesejahteraan, InterruptedException;

    public void buangAir() throws ItemError, NotEnoughKesejahteraan,InterruptedException;

    public int getBonusInc();

    public void nubes() throws InterruptedException;

    public void woodworking(NonMakanan item) throws InterruptedException,TidakCukupItem;

    public void bath() throws ItemError,InterruptedException;

    public void meditate(int time) throws NotEnoughKesejahteraan,InterruptedException, TimeError;

    public void read() throws InterruptedException,ItemError;

    public void party() throws TidakCukupItem,InterruptedException;
    
    public void masak(Makanan m) throws TidakCukupItem,InterruptedException;
}
