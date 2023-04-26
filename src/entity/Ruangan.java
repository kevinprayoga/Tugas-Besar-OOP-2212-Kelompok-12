package entity;

import java.util.Scanner;
// masih draft

public class Ruangan {
    private Dimensi dimensi;
    private String[][] mapRoom;

    public Ruangan(){
        dimensi = new Dimensi(6,6);
        String[][] mapRoom = new String[dimensi.getLength()][dimensi.getWidth()];
    }

    public boolean findObjek(Posisi loc){
        boolean found = false;
        for (int i = 0; i < loc.getAbsis(); i++){
            for (int j = 0; j < loc.getOrdinat(); j ++){
                if (mapRoom[i][j] != null){
                    found = true;
                }
            }
        }
        return found;       
    }

    public void getObjek(Posisi loc){
        if (findObjek(loc)){
            System.out.println(mapRoom[loc.getAbsis()][loc.getOrdinat()]);
        }
        else{
            System.out.println("No Object Found!");
        }
    }
    public void addObjek(Posisi loc, Objek objek){
        if(!findObjek(loc)){
            for (int i = 0; i < loc.getAbsis(); i++){
                for (int j = 0; j < loc.getOrdinat(); j ++){
                    mapRoom[i][j] = "*";
                }
            }
        }
        else{
            System.out.println("Spot is filled. Enter other positions to put object.");
            Scanner scanner = new Scanner(System.in);
            while(findObjek(loc)){
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                loc = new Posisi(x, y);
            }
            scanner.close();
            addObjek(loc, objek);
        }
    }

    public void moveObjek(Posisi loc, Objek objek){
        

    }

    public void removeObjek(){

    }

}

