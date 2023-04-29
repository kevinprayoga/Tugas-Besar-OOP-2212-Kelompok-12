package entity;

public class Waktu {
    private static int startTime;
    private int remainTime;
    private int day = 0;

    public Waktu() {
        synchronized(Waktu.class){
            startTime = (int) System.currentTimeMillis() / 1000;
        }
    }

    public synchronized int getTime() {
        int currentTime = (int) System.currentTimeMillis() / 1000;
        return currentTime - startTime;
    }

    public synchronized int getDay() {
        // 1 hari = 12 menit = 720 detik
        int time = getTime();
        day = time / 720;
        return day;
    }

    public synchronized int getRemainTime(){
        int time = getTime();
        remainTime = time % 720;
        return remainTime;
    }

    public synchronized void reset() {
        startTime = (int) System.currentTimeMillis() / 1000;
    }
}