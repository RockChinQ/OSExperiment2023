package allocating.entity;

import java.util.ArrayList;

public class AllocatingResult {
    String algoName;

    ArrayList<ProcessOccupancy> processOccupancies;

    public AllocatingResult(String algoName, ArrayList<ProcessOccupancy> processOccupancies) {
        this.algoName = algoName;
        this.processOccupancies = processOccupancies;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public ArrayList<ProcessOccupancy> getProcessOccupancies() {
        return processOccupancies;
    }
}
