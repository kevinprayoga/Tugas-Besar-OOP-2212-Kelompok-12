package entity;

import java.util.ArrayList;
import java.util.List;

public class PekerjaanPrinter {
    private List<Pekerjaan> jobList = new ArrayList<Pekerjaan>();
    private Pekerjaan testPekerjaan;

    public List<Pekerjaan> getPekerjaanList() {
        return jobList;
    }

    public Pekerjaan getTestPekerjaan() {
        return testPekerjaan;
    }

    public void setJob(String newJob) {
        if (newJob.equals("BadutSulap")){
            testPekerjaan = new BadutSulap();
        } else if (newJob.equals("Barista")){
            testPekerjaan = new Barista();
        } else if (newJob.equals("Koki")){
            testPekerjaan = new Koki();
        } else if (newJob.equals("Polisi")){
            testPekerjaan = new Polisi();
        } else if (newJob.equals("Programmer")){
            testPekerjaan = new Programmer();
        } else if (newJob.equals("Dokter")){
            testPekerjaan = new Dokter();
        } else if (newJob.equals("Guru")){
            testPekerjaan = new Guru();
        }
    }
}
