package entity;

public class World {
    private int panjang;
    private int lebar;
    private Rumah[][] rumah;

    
    public World(int panjang,int lebar){
        this.panjang = panjang;
        this.lebar = lebar;

    }

    public int getPanjang(){
        return panjang;
    }

    public int getLebar(){
        return lebar;
    }


}
