package main;

import entity.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {
    private static GamePanel gamePanel;

    @SuppressWarnings("unchecked")
    public static void readLoadFile(int pathOption) {
        // JSON parser object to parse read file
        gamePanel = GamePanel.getGamePanel();
        String[] paths = new String[] { "data/simSaveFile.json" };

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(paths[pathOption - 1])) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray simList = (JSONArray) obj;
            // System.out.println(simList);

            // Iterate over simFile array
            simList.forEach(sim -> parseSimObject((JSONObject) sim));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseSimObject(JSONObject simFile) {
        // Get sim object within list
        JSONObject simObject = (JSONObject) simFile.get("SIM");

        // Create the SIMs
        String namaLengkap = (String) simObject.get("namaLengkap");
        int charType = (int) (long) simObject.get("charType");
        String pekerjaan = (String) simObject.get("pekerjaan");
        int uang = (int) (long) simObject.get("uang");

        JSONObject inventoryObject = (JSONObject) simObject.get("inventory");
        String itemSet = inventoryObject.keySet().toString();
        String items = itemSet.substring(1, itemSet.length() - 1);
        String[] itemArray = items.split(", ");

        int kekenyangan = (int) (long) simObject.get("kekenyangan");
        int mood = (int) (long) simObject.get("mood");
        int kesehatan = (int) (long) simObject.get("kesehatan");
        int wood = (int) (long) simObject.get("wood");
        int totalWaktuKerja = (int) (long) simObject.get("totalWaktuKerja");
        int timeEmployed = (int) (long) simObject.get("timeEmployed");
        int timeTidur = (int) (long) simObject.get("timeTidur");
        int dayTidur = (int) (long) simObject.get("dayTidur");
        Boolean kesejahAltTidur = (Boolean) simObject.get("kesejahAltTidur");
        int timeMakan = (int) (long) simObject.get("timeMakan");
        int dayMakan = (int) (long) simObject.get("dayMakan");
        Boolean kesejahAltBAir = (Boolean) simObject.get("kesejahAltBAir");
        int startTimeVacation = (int) (long) simObject.get("startTimeVacation");
        int startDayVacation = (int) (long) simObject.get("startDayVacation");
        int bonusInc = (int) (long) simObject.get("bonusInc");
        int gajiBank = (int) (long) simObject.get("gajiBank");
        String currentPosition = (String) simObject.get("currentPosition");
        String currentHouse = (String) simObject.get("homeVisit");

        JSONObject rumahObject = (JSONObject) simObject.get("rumah");
        int x = (int) (long) rumahObject.get("x");
        int y = (int) (long) rumahObject.get("y");

        // System.out.println(namaLengkap);
        // System.out.println(charType);
        // System.out.println(pekerjaan);
        // System.out.println(uang);
        // System.out.println(Arrays.toString(itemArray));
        // System.out.println(kekenyangan);
        // System.out.println(x);

        try {
            Sim player = new Sim(namaLengkap, charType, true);
            gamePanel.addPlayableSims(player);
            player.getPekerjaanPrinter().setJob(pekerjaan);
            player.setMinusUang(100 - uang);
            
            // String[] a = items.split(", ");
            for (String i : itemArray) {
                int loop = (int) inventoryObject.get(i);
                for (int j = 0; j < loop; j++) {
                    player.getInventory().addItem(new NonMakanan(i));
                }
            }

            player.setKekenyangan(kekenyangan);
            player.setMood(mood);
            player.setKesehatan(kesehatan);
            player.setWood(wood);
            player.setTotalWaktuKerja(totalWaktuKerja);
            player.setTimeEmployed(timeEmployed);
            player.setTimeTidur(timeTidur);
            player.setDayTidur(dayTidur);
            player.setKesejahAltTidur(kesejahAltTidur);
            player.setTimeMakan(timeMakan);
            player.setDayMakan(dayMakan);
            player.setKesejahAltBAir(kesejahAltBAir);
            player.setStartTimeVacation(startTimeVacation);
            player.setStartDayVacation(startDayVacation);
            player.setBonusInc(bonusInc);
            // player.setMyRumah(gamePanel.getWorld().getHouse(x, y));
            player.setGajiBank(gajiBank);
            // player.setMyRumah(new Rumah(player, x, y));

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    

    private static void parseWorldObject(JSONObject worldFile) {
        // Get World Object and its properties from worldFile
        JSONObject worldObject = (JSONObject) worldFile.get("SIM");

        // Create All World Related Objects
        int freq = (int) worldObject.get("freq");

        try {
            JSONArray perumahan = (JSONArray) worldObject.get("perumahan");
            perumahan.forEach(rumah -> parseRumahObject((JSONObject) rumah));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseRumahObject(JSONObject rumahFile) {
    JSONObject rumahObject = (JSONObject) rumahFile.get("rumah");

    String owner = (String) rumahObject.get("owner");
    int x = (int) rumahObject.get("x");
    int y = (int) rumahObject.get("y");
    String image = (String) rumahObject.get("image");

    JSONArray roomMap = (JSONArray) rumahObject.get("roomMap");
    Iterator<String[]> iterator = roomMap.iterator();
    
    JSONArray rooms = (JSONArray) rumahObject.get("rooms");

    // Apply
    Rumah house = gamePanel.getSim(owner).getMyRumah();
    house.setPosisi(x, y);
    house.setImage(image);

    int row = 0;
    int column = 0;
    while (iterator.hasNext()) {
        String[] rows = iterator.next();
        Matrix<Integer> houseRoom = house.getRoomBuild();
        while (column != 9) {
            if (rows[column].equals("1")) {
                houseRoom.set(row, column, 1);
            } 
        }
    }
    for (Object i : rooms) {
        JSONObject roomDetails = (JSONObject) i;
    }

    }

    // public static void main(String[] args) {
    //     ReadJSON.readLoadFile(1);
    // }
}