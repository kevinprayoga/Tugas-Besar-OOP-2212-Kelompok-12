package entity;
import java.util.ArrayList;

import entity.Matrix;
import util.Noise;

public class World {

    // Attributes
    private Dimensi dimensi;
    private Matrix<Integer> mapWorld;
    private Matrix<Boolean> houseMap;
    private Matrix<Rumah> perumahan;
    private ArrayList<Sim> simList;

    // Constructor
    public World() {
        dimensi = new Dimensi(64, 64);
        this.mapWorld = new Matrix<>(dimensi.getWidth(), dimensi.getLength());
        this.houseMap = new Matrix<>(dimensi.getWidth(), dimensi.getLength());
        this.perumahan = new Matrix<>(dimensi.getWidth(), dimensi.getLength());
        simList = new ArrayList<Sim>();

        // Set Default Value to EMPTY, which means EMPTY SPACE
        for (int i = 0; i < dimensi.getLength(); i++) {
            for (int j = 0; j < dimensi.getWidth(); j++) {
                houseMap.set(i, j, false);
            }
        }

        // World map generation
        Noise noise = new Noise(dimensi.getWidth(), dimensi.getLength());
        
        // Randomizing frequency
        int frequency = (int) (Math.random() * 5) + 2;
        System.out.println(frequency);
        noise.generateNoise(frequency);
        
        // Mapping noise map into 3 values
        for (int row = 0; row < dimensi.getWidth(); row++) {
            for (int column = 0; column < dimensi.getLength(); column++) {
                if (noise.getNoise()[row][column] < 0) {
                    mapWorld.set(row, column, 0);
                } else if (noise.getNoise()[row][column] < 0.5) {
                    mapWorld.set(row, column, 1);
                } else {
                    mapWorld.set(row, column, 2);
                }
            }
        }
    }

    // Methods
    // Getter
    public int getLength() {
        return dimensi.getLength();
    }

    public int getWidth() {
        return dimensi.getWidth();
    }

    public Matrix<Integer> getMapWorld() {
        return this.mapWorld;
    }

    public Matrix<Boolean> getHouseMap() {
        return this.houseMap;
    }

    public Matrix<Rumah> getPerumahan() {
        return this.perumahan;
    }

    public Rumah getHouse(int x, int y) {
        // Due to the difference between Cartesian Diagram mapping & Matrix mapping,
        // the Matrix is turned into Cartesian Diagram;
        return this.perumahan.get(x, y);
    }

    public ArrayList<Sim> getSimList() {
        return this.simList;
    }

    // Setter
    public void setNewHouse(int x, int y, Rumah house) {
        this.perumahan.set(x, y, house);
    }

    // Adder
    public void addSim(Sim sim) {
        simList.add(sim);
        Rumah myHouse = addHouse(sim);

        int houseX = 0;
        int houseY = 0;

        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                try {
                    if (perumahan.get(i, j).equals(myHouse)) {
                        houseX = i; houseY = j;
                    }
                } catch (Exception e) {}
            }
        }

        // Randomizing sims position on radius 2 of the house
        int x = (int) (Math.random() * 5) - 2 + houseX;
        int y = (int) (Math.random() * 5) - 2 + houseY;
        
        // Checking if its not grass or sand or beyond map, if it is, randomize again
        while (houseMap.get(x, y) || x < 0 || x > 63 || y < 0 || y > 63 && mapWorld.get(x, y) == 2 ) {
            x = (int) (Math.random() * 5) - 2 + houseX;
            y = (int) (Math.random() * 5) - 2 + houseY;    
        }

        sim.setPosisi(new Posisi(x, y));
    }

    public Rumah addHouse(Sim owner) {
        // Randomizing house position
        int x = (int) (Math.random() * 63);
        int y = (int) (Math.random() * 63);

        // While the position is not grass, randomize again, if there is arleady a house, randomize again
        while (mapWorld.get(x, y) != 0 || houseMap.get(x, y)) {
            x = (int) (Math.random() * 63);
            y = (int) (Math.random() * 63);
        }

        // Create new house
        Rumah house = new Rumah(owner);
        setNewHouse(x, y, house);
        return house;
    }

    // Debugger

    public void printWorld() {
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                System.out.print(houseMap.get(i, j));
                if (j < getWidth() - 1) {
                    System.out.print(" | ");
                }
                else {
                    System.out.println();
                }
            }
        }
    }
}
