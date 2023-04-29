// Created the references for the World

import java.util.HashMap;

public class World {
    // Singleton makes sure that the is only one World object
    private static World single_instance = null;

    // Attributes
    private int length;
    private int width;
    private String[][] residence;
    private HashMap<Integer, House> houses;

    // Constructor
    private World() {
        this.length = 5;
        this.width = 5;
        this.residence = new String[this.length][this.width];
        this.houses = new HashMap<>(width, length);

        // Set Default Value to EMPTY, which means EMPTY SPACE
        for (String[] i : this.residence) {
            for (int j = 0; j < length; j++) {
                i[j] = "EMPTY";
            }
        }
    }

    // Static Method to create an instance of World Class
    public static synchronized World getWorld() {
        if (single_instance == null) {
            single_instance = new World();
        }

        return single_instance;
    }   

    // Methods
    public int getLength() {
        return this.length;
    }

    public int getWidth() {
        return this.width;
    }

    public String[][] getResidence() {
        return this.residence;
    }

    public House getHouse(int x, int y) {
        // Due to the difference between Cartesian Diagram mapping & Matrix mapping,
        // the Matrix is turned into Cartesian Diagram;
        return this.houses.get(x + (5 * (y - 1)));
    }

    public void setNewHouse(int x, int y) {
        this.residence[5 - x][y - 1] = "HOUSE";
        // this.houses.put(x + (5 * (y - 1)), new House());
    }

    public void printWorld() {
        for (String[] i : this.residence) {
            for (int j = 0; j < i.length; j++) {
                System.out.print(i[j]);
                if (j < i.length - 1) {
                    System.out.print(" | ");
                }
                else {
                    System.out.println();
                }
            }
        }
    }
}
