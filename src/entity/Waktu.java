package entity;

public class Waktu {
    private static int time = 0;
    private static int remainTime = 720;
    private static int day = 0;
    private static int timeDelay = 50;
    private static int actionTimer;

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

    public static int getActionTimer(){
        return actionTimer;
    }

    public static void setActionTimer(int time){
        actionTimer = time;
    }

    public static void addTime() throws InterruptedException{
        Thread thread = new Thread(new Runnable(){
            public void run(){
                try{
                    while (actionTimer > 0) {
                        System.out.println(actionTimer);
                        Thread.sleep(timeDelay);
                        time += 1;
                        day += time/720;
                        time = time %720;
                        remainTime = 720 - time;
                        actionTimer -= 1;
                    }
                } catch (Exception e){
                    e.getMessage();
                }
            }
        });
        thread.start();
        
    }
}