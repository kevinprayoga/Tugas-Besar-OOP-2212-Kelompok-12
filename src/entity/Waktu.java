package entity;

public class Waktu {
    private static int time = 0;
    private static int remainTime = 720;
    private static int day = 0;
    private static int timeDelay = 100;

    public static int getTime() {
        return time;
    }

    public static void setTimeDelay(int time){
        timeDelay = time;
    }

    public static int getRemainTime(){
        return remainTime;
    }

    public static int getDay() {
        return day;
    }

    public static void addSecond() throws InterruptedException{
        Thread.sleep(timeDelay);
        time += 1;
        day += time/720;
        time = time %720;
        remainTime = 720 - time;
    }
}