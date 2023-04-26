package entity;

public class Waktu {
    private static int startTime;
    private int remainTime;

    public Waktu() {
        startTime = (int) System.currentTimeMillis() / 1000;
    }

    public int getTime() {
        int currentTime = (int) System.currentTimeMillis() / 1000;
        return currentTime - startTime;
    }

    public int getDay() {
        // 1 hari = 12 menit = 720 detik
        int timeInSec = getTime();
        int day = timeInSec / 720;
        return day;
    }

    public int getRemainTime(){
        int timeInSec = getTime();
        remainTime = timeInSec % 720;
        return remainTime;
    }
}