package main;

import java.util.Arrays;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import entity.*;
import main.GamePanel.*;

public class GameLoader {
    private static final GamePanel gamePanel = GamePanel.getGamePanel();

    public static void loadGame(int opt) {
        System.out.println("Load Game");

        gamePanel.reset();
        ReadJSON.readLoadFile(opt);

        gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
        gamePanel.leastRecentlyUsed.push(GameState.CHARACTER_SELECTION_SCREEN);
        System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
    }

    public static void saveGame(ArrayList<Sim> playableSims, World world) {
        // MAKE SAVE FILE FOR SIM

        // Array of sim
        JSONArray simArr = new JSONArray();
        // Object sim
        JSONObject simObj = new JSONObject();

        for (Sim sim : playableSims) {
            // Sim Details
            JSONObject simDetails = new JSONObject();
            simDetails.put("namaLengkap", sim.getNamaLengkap());
            simDetails.put("charType", sim.getCharType());
            simDetails.put("pekerjaan", sim.getPekerjaan().getJob());
            simDetails.put("uang", sim.getUang());

            // object inventory
            JSONObject simInvent = new JSONObject();
            
            for (String nonMakanan : sim.getInventory().getInventoryList()) {
                simInvent.put(nonMakanan, sim.getInventory().getItemValue(nonMakanan));
            }
            // Put array to object
            simDetails.put("inventory", simInvent);

            simDetails.put("kekenyangan", sim.getKekenyangan());
            simDetails.put("mood", sim.getMood());
            simDetails.put("kesehatan", sim.getKesehatan());
            simDetails.put("wood", sim.getWood());
            simDetails.put("totalWaktuKerja", sim.getTotalWaktuKerja());
            simDetails.put("timeEmployed", sim.getTimeEmployed());
            simDetails.put("timeTidur", sim.getTimeTidur());
            simDetails.put("dayTidur", sim.getDayTidur());
            simDetails.put("kesejahAltTidur", sim.getKesejahAltTidur());
            simDetails.put("timeMakan", sim.getTimeMakan());
            simDetails.put("dayMakan", sim.getDayMakan());
            simDetails.put("kesejahAltBAir", sim.getKesejahAltBAir());
            simDetails.put("startTimeVacation", sim.getStartTimeVacation());
            simDetails.put("startDayVacation", sim.getStartDayVacation());
            simDetails.put("bonusInc", sim.getBonusInc());
            simDetails.put("gajiBank", sim.getGajiBank());
            simDetails.put("currentPosition", sim.getCurrentPosition());
            simDetails.put("currentHouse", sim.getCurrentHouse());

            JSONObject posRumah = new JSONObject();
            posRumah.put("x", sim.getMyRumah().getPosisi().getX());
            posRumah.put("y", sim.getMyRumah().getPosisi().getY());

            simDetails.put("rumah", posRumah);

            // put into sim object
            simObj.put("SIM", simDetails);
            // put into sim array
            simArr.add(simObj);
        }
        // Write JSON file for sim.JSON
        try (FileWriter file = new FileWriter("simSave.json")) {
            // We can write any JSONArray or JSONObject instance to the file
            file.write(simArr.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // MAKE SAVE FILE FOR WORLD

        // Attribute for home
        JSONObject rumahObj = new JSONObject();

        for (int i = 0; i < world.getPerumahan().getRow(); i++) {
            for (int j = 0; j < world.getPerumahan().getColumn(); j++) {
                if (world.getHouseMap().get(i, j) == true) {
                    rumahObj.put("owner", world.getHouse(i, j).getOwner());
                    rumahObj.put("x", world.getHouse(i, j).getPosisi().getX());
                    rumahObj.put("y", world.getHouse(i, j).getPosisi().getY());
                    rumahObj.put("image", world.getHouse(i, j).getType());

                    // for roomMap
                    JSONArray roomMapMat = new JSONArray();

                    for (int x = 0; x < 9; x++) {
                        JSONArray roomMappArr = new JSONArray();
                        for (int y = 0; y < 9; y++) {
                            if (world.getHouse(i, j).getRoomBuild().get(x, y) != 2) {
                                roomMappArr.add(Integer.toString(world.getHouse(i, j).getRoomBuild().get(x, y)));
                            } else {
                                for (String room : world.getHouse(i, j).getRuanganList()) {
                                    if (world.getHouse(i, j).getMatRoom().get(x, y).getPosisi() == world.getHouse(i, j).getRuangan(room).getPosisi()) {
                                        roomMappArr.add(room);
                                    }
                                }
                            }
                        }
                        roomMapMat.add(roomMappArr);
                    }
                    rumahObj.put("roomMap", roomMapMat);
                    

                }
            }
        }

        JSONArray perumArr = new JSONArray();
        perumArr.add(rumahObj);

        JSONObject perum = new JSONObject();
        perum.put("perumahan", perumArr);

        // Write JSON file for sim.JSON
        try (FileWriter file = new FileWriter("worldSave.json")) {
            // We can write any JSONArray or JSONObject instance to the file
            file.write(perum.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newGame(int opt) {
        System.out.println("New game");
        
        gamePanel.reset();
        
        gamePanel.setGameState(GameState.CHARACTER_SELECTION_SCREEN);
        gamePanel.leastRecentlyUsed.push(GameState.CHARACTER_SELECTION_SCREEN);
        System.out.println(Arrays.toString(gamePanel.leastRecentlyUsed.toArray()));
    }
} 
