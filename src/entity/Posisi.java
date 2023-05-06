package entity;

public class Posisi{
    private int absis;
    private int ordinat;

    public Posisi(int x, int y){
        absis = x;
        ordinat = y;
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

    public int getX(){
        return absis;
    }

    public int getY(){
        return ordinat;
    }

}