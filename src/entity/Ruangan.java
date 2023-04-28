package entity;

import java.util.Scanner;
// masih draft

public class Ruangan {
    private Dimensi dimensi;
    private String[][] mapRoom;

    public Ruangan(){
        dimensi = new Dimensi(6,6);
        mapRoom = new String[dimensi.getLength()][dimensi.getWidth()];
    }

    public boolean findObjek(Posisi loc){
        boolean found = false;
        if(loc.getAbsis() < dimensi.getLength() && loc.getOrdinat() < dimensi.getWidth()){
            if (mapRoom[loc.getAbsis()][loc.getOrdinat()] != null){
                found = true;
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
            int absisEnd = loc.getAbsis() + objek.getLength();
            int ordinatEnd = loc.getOrdinat() + objek.getWidth();
            if(absisEnd <= dimensi.getLength() && ordinatEnd <= dimensi.getWidth()){
                for (int i = loc.getAbsis(); i < absisEnd; i++){
                    for (int j = loc.getOrdinat(); j < ordinatEnd; j++){
                        mapRoom[i][j] = "*";
                    }
                }
            }
            else{
                System.out.println("Object cannot be placed in the specified location. Try another location.");
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

    public void moveObjek(Objek objek, Posisi loc){
        removeObjek(objek.getPosisi());
        addObjek(loc, objek);
        objek.setPosisi(loc);
    }

    public void removeObjek(Posisi loc){
        if(findObjek(loc)){
            Objek objek = getObjekAt(loc);
            for (int i = loc.getAbsis(); i < loc.getAbsis() + objek.getDimensi().getLength(); i++){
                for (int j = loc.getOrdinat(); j < loc.getOrdinat() + objek.getDimensi().getWidth(); j ++){
                    if (mapRoom[i][j] != null && mapRoom[i][j].equals("*")){
                        mapRoom[i][j] = null;
                    }
                }
            }
        }
        else{
            System.out.println("No Object Found!");
        }
    }
    
    public Objek getObjekAt(Posisi loc){
        if(findObjek(loc)){
            int absisStart = loc.getAbsis();
            int ordinatStart = loc.getOrdinat();
            int absisEnd = absisStart;
            int ordinatEnd = ordinatStart;
            while(absisEnd < dimensi.getLength() && mapRoom[absisEnd][ordinatStart] != null && mapRoom[absisEnd][ordinatStart].equals("*")){
                absisEnd++;
            }
        }
    }

}