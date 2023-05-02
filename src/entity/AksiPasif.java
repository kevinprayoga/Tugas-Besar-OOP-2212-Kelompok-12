package entity;

import exceptions.ItemError;
import exceptions.TidakCukupItem;

public interface AksiPasif {
    public void upgradeRumah(int x, int y, String nama) throws TidakCukupItem, InterruptedException;

    public void beliObjek(Produk o) throws ItemError,TidakCukupItem;

    public void displayInventory();

    public void installObject(NonMakanan o, Posisi p);

    public int getTime() throws ItemError;

    public void gamble(int money) throws TidakCukupItem;
}