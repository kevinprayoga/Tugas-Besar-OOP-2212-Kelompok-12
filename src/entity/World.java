package entity;
import java.util.ArrayList;

import util.Noise;

public class World {

    // Attributes
    private Dimensi dimensi;
    private Matrix<Integer> mapWorld;
    private Matrix<Boolean> houseMap;
    private static Matrix<Rumah> perumahan;
    private ArrayList<Sim> simList;

    // For World Map Generation (Noise)
    private int freq;

    // Constructor
    public World(int freq) {
        dimensi = new Dimensi(64, 64);
        this.mapWorld = new Matrix<>(dimensi.getWidth(), dimensi.getLength());
        this.houseMap = new Matrix<>(dimensi.getWidth(), dimensi.getLength());
        perumahan = new Matrix<>(dimensi.getWidth(), dimensi.getLength());
        simList = new ArrayList<Sim>();

        // Set Default Value to EMPTY, which means EMPTY SPACE
        for (int i = 0; i < dimensi.getLength(); i++) {
            for (int j = 0; j < dimensi.getWidth(); j++) {
                houseMap.set(i, j, false);
            }
        }

        // World map generation
        Noise noise = new Noise(dimensi.getWidth(), dimensi.getLength());
        
        this.freq = (int) (Math.random() * 5) + 2;

        // For load game purposes
        if (freq != -1) {
            this.freq = freq;
        }

        System.out.println(this.freq);
        noise.generateNoise(this.freq);
        
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

    public World() {
        this(-1);
    }

    // public World(Posisi[] posisiRumah) {
    //     this(-1);

    // }

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
        return perumahan;
    }

    public Rumah getHouse(int x, int y) {
        // Due to the difference between Cartesian Diagram mapping & Matrix mapping,
        // the Matrix is turned into Cartesian Diagram;
        return perumahan.get(x, y);
    }

    public ArrayList<Sim> getSimList() {
        return this.simList;
    }

    // Setter
    public void setNewHouse(int x, int y, Rumah house) {
        perumahan.set(x, y, house);
    }

    // Adder
    public void addSim(Sim sim) {
        simList.add(sim);

        int houseX = 0;
        int houseY = 0;

        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (perumahan.get(i, j) != null) {
                    System.out.println(perumahan.get(i, j).getOwner().getNamaLengkap());
                    if (perumahan.get(i, j).getOwner().equals(sim)) {
                            houseX = i; houseY = j;
                    }
                }
            }
        }

        int x, y;
        if (houseX > getLength() - 1) x = houseX - 1;
        else x = houseX + 1;
        if (houseY > getWidth() - 1) y = houseY - 1;
        else y = houseY + 1;

        while (mapWorld.get(x, y) == 2) {
            x--;
        }

        sim.getPosisi().changeLoc(x, y);
        System.out.println(sim.getNamaLengkap() + " is at " + x + ", " + y);
        System.out.println(sim.getNamaLengkap() + " is at " + sim.getPosisi().getX() + ", " + sim.getPosisi().getY());
    }

    public void createSim(Sim sim) {
        createHouse(sim);
        addSim(sim);
    }

    public Rumah createHouse(Sim owner) {
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
        house.setPosisi(x, y);
        setNewHouse(x, y, house);
        houseMap.set(x, y, true);
        return house;
    }

    // Debugger

    public void printWorld() {
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                System.out.print(houseMap.get(i, j));
                if (j < getWidth() - 1) {
                    System.out.print(" | ");
                } else {
                    System.out.println();
                }
            }
        }
    }
}
