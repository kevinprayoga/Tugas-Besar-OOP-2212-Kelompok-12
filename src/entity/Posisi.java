package entity;

public class Posisi{
    private int absis;
    private int ordinat;

    public Posisi(int x, int y){
        x = absis;
        y = ordinat;
    }
    
    public void setAbsis(int absis){
        this.absis = absis;
    }

    public void setOrdinat(int ordinat){
        this.ordinat = ordinat;
    }

    public void changeLoc(int x, int y){
        this.setAbsis(x);
        this.setOrdinat(y);
    }

    public int getAbsis(){
        return absis;
    }

    public int getOrdinat(){
        return ordinat;
    }

}