package pekerjaan;

public abstract class Pekerjaan {
    private String jobString;
    private PekerjaanPrinter printer;

    public Pekerjaan() {
        jobString = getRandomJob();
        printer.setJob(jobString);
    }

    public abstract int getGaji();

    public String getJob() {
        return jobString;
    }

    public PekerjaanPrinter getPekerjaanPrinter() {
        return printer;
    }

    public static String getRandomJob(){
        int r = (int) (Math.random()*7);
        String jobRND = new String [] {"BadutSulap", "Barista", "Koki", "Polisi", "Programmer", "Dokter", "Guru"}[r];
        return jobRND;
    }
}
    