public interface AksiPasif {
    public void upgradeRumah(Ruangan r, String arah, String nama);

    public void beliObjek(Objek o);

    public void moveRoom(Ruangan r);

    public void moveTo(Objek o);

    public void displayInventory();

    public void installObject(Objek o, Posisi p);

    public int getTime();

    public void gamble(int money);
}
