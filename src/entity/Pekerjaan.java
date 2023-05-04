package entity;

public abstract class Pekerjaan {
    private String jobString;

    public Pekerjaan() {
        jobString = getRandomJob();
    }

    public abstract int getGaji();

    public String getJob() {
        return jobString;
    }

    public static String getRandomJob(){
        int r = (int) (Math.random()*7);
        String jobRND = new String [] {"BadutSulap", "Barista", "Koki", "Polisi", "Programmer", "Dokter", "Guru"}[r];
        return jobRND;
    }
}
    