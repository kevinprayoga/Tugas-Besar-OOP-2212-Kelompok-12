package entity;
import java.util.HashMap;
import java.util.Scanner;

public class World {
    // Singleton makes sure that the is only one World object
    private static World single_instance = null;

    // Attributes
    private Dimensi dimensi;
    private String[][] mapWorld;
    private HashMap<Integer, Rumah> perumahan;

    // Constructor
    private World(int length, int width) {
        dimensi = new Dimensi(length, width);
        this.mapWorld = new String[dimensi.getLength()][dimensi.getWidth()];
        this.perumahan = new HashMap<>(dimensi.getWidth(), dimensi.getLength());

        // Set Default Value to EMPTY, which means EMPTY SPACE
        for (String[] i : this.mapWorld) {
            for (int j = 0; j < length; j++) {
                i[j] = "EMPTY";
            }
        }
    }

    // Static Method to create an instance of World Class
    public static synchronized World getWorld() {
        if (single_instance == null) {
            Scanner scanner = new Scanner(System.in);
            int length = scanner.nextInt();
            int width = scanner.nextInt();
            single_instance = new World(length,width);
        }

        return single_instance;
    }   

    // Methods
    public int getLength() {
        return dimensi.getLength();
    }

    public int getWidth() {
        return dimensi.getWidth();
    }

    public String[][] getmapWorld() {
        return this.mapWorld;
    }

    public Rumah getHouse(int x, int y) {
        // Due to the difference between Cartesian Diagram mapping & Matrix mapping,
        // the Matrix is turned into Cartesian Diagram;
        return this.perumahan.get(x + (this.getLength() * (y - 1)));
    }

    public void setNewHouse(int x, int y) {
        this.mapWorld[this.getLength() - x][y - 1] = "HOUSE";
        // this.perumahan.put(x + (5 * (y - 1)), new House());
    }

    public void printWorld() {
        for (String[] i : this.mapWorld) {
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
