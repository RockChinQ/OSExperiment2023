package allocating.service;

import allocating.entity.ProcessOccupancy;
import allocating.entity.RunningProcess;

import java.util.ArrayList;

public abstract class AbstractAllocatingAlgorithm {

    protected ArrayList<ProcessOccupancy> processOccupancies = new ArrayList<>();
    public abstract String getAlgoName();
    public abstract void addProcess(int memorySize, RunningProcess process);
    public void removeProcess(RunningProcess process) {
        for (int i = 0; i < processOccupancies.size(); i++) {
            ProcessOccupancy occupancy = processOccupancies.get(i);
            if (occupancy.getProcess().equals(process)) {
                processOccupancies.remove(i);
                break;
            }
        }
    }

    public ArrayList<ProcessOccupancy> getProcessOccupancies() {
        return this.processOccupancies;
    }
}
