/*
 * FILE NAME    : DriverNonMakanan.java
 * AUTHOR       : 18221045 Ivan Aldy Ganesen
 * DESCRIPTION  :
 * This file serves as a driver (testing place) for NonMakanan.java
 * file that has been made before.
*/

import java.util.Scanner;   // for inputs
import java.util.ArrayList; // to create an ArrayList
import java.util.Arrays;    // to convert into List

public class DriverNonMakanan {
    public static void main(String[] args) {
        Scanner take = new Scanner(System.in); // define a new scanner
        String inp = take.next(); // takes an input

        // This is the start of handling unwanted inputs
        // defining an array list of acceptable type inputs
        final ArrayList<String> NonMakananList = new ArrayList<>(Arrays.asList("KasurKingSize", "KasurQueenSize", "KasurSingleSize",
        "Shower", "Toilet", "KomporGas", "KomporListrik", "RakBuku", "MejaKursi", "Jam"));
        while (!NonMakananList.contains(inp)) { // enters this loop as long as the input is incorrect
            System.out.println("Wrong Type!");
            inp = take.next();
        }
        // this is the end of handling unwanted inputs

        take.close(); // close the scanner

        NonMakanan test = new NonMakanan(inp);  // define a new NonMakanan variable

        // information print-out
        System.out.println(test.getDimensi());  // get dimension info
        System.out.println(test.getHarga());    // get price
        System.out.println(test.getAksi());     // get action
        System.out.println(test.getJenis());    // get type
    }
}
