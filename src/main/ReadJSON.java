package main;

import entity.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {
    private static GamePanel gp;

    @SuppressWarnings("unchecked")
    public static void readLoadFile(GamePanel gPanel, int pathOption) {
        // JSON parser object to parse read file
        gp = gPanel;
        String[] paths = new String[] {"data/load1.json", "data/load2.json", "data/load3.json"};

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(paths[pathOption])) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray simList = (JSONArray) obj;
            System.out.println(simList);

            // Iterate over sim array
            simList.forEach(sim -> parseSimObject((JSONObject) sim));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseSimObject(JSONObject sim) {
        // Get sim object within list
        JSONObject simObject = (JSONObject) sim.get("SIM");

        // Create the SIMs
        String namaLengkap = (String) simObject.get("namaLengkap");
        Integer charType = (Integer) simObject.get("charType");
        String jobPrinter = (String) simObject.get("jobPrinter");
        Integer uang = (Integer) simObject.get("uang");

        JSONObject inventoryObject = (JSONObject) sim.get("inventory");
        String[] items = (String[]) inventoryObject.keySet().toArray();

        Integer kekenyangan = (Integer) simObject.get("kekenyangan");
        Integer mood = (Integer) simObject.get("mood");
        Integer kesehatan = (Integer) simObject.get("kesehatan");
        Integer wood = (Integer) simObject.get("wood");
        Integer totalWaktuKerja = (Integer) simObject.get("totalWaktuKerja");
        Integer timeEmployed = (Integer) simObject.get("timeEmployed");
        Integer timeTidur = (Integer) simObject.get("timeTidur");
        Integer dayTidur = (Integer) simObject.get("dayTidur");
        Boolean kesejahAltTidur = (Boolean) simObject.get("kesejahAltTidur");
        Integer timeMakan = (Integer) simObject.get("timeMakan");
        Integer dayMakan = (Integer) simObject.get("dayMakan");
        Boolean kesejahAltBAir = (Boolean) simObject.get("kesejahAltBAir");
        Integer startTimeVacation = (Integer) simObject.get("startTimeVacation");
        Integer startDayVacation = (Integer) simObject.get("startDayVacation");
        Integer bonusInc = (Integer) simObject.get("bonusInc");
        Integer gajiBank = (Integer) simObject.get("gajiBank");

        JSONObject rumahObject = (JSONObject) sim.get("inventory");
        int x = (int) rumahObject.get("x");
        int y = (int) rumahObject.get("y");

        try {
            Sim player = new Sim(namaLengkap, charType, true);
            gp.addPlayableSims(player);
            player.getPekerjaanPrinter().setJob(jobPrinter);
            player.setMinusUang(100 - uang);

            for (String i : items) {
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
            player.setMyRumah(gp.getWorld().getHouse(x, y));
            player.setGajiBank(gajiBank);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}