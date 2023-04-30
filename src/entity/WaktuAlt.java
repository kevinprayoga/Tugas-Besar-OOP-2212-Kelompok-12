package entity;

public class WaktuAlt {
    private static int time = 0;
    private static int remainTime = 720;
    private static int day = 0;

    public static int getTime() {
        return time;
    }

    public static int getRemainTime(){
        return remainTime;
    }

    public static int getDay() {
        return day;
    }

    public static void addSecond() throws InterruptedException{
        Thread.sleep(1000);
        time += 1;
        day += time/720;
        time = time %720;
        remainTime = 720 - time;
    }
}