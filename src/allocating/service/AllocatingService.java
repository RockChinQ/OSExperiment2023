package allocating.service;

import allocating.entity.AllocatingResult;
import allocating.entity.RunningProcess;
import allocating.service.algos.BF;
import allocating.service.algos.FF;
import allocating.service.algos.WF;

import java.util.ArrayList;

public class AllocatingService {
    int memorySize = 0;

    ArrayList<RunningProcess> processes;

    ArrayList<AbstractAllocatingAlgorithm> algorithms;

    public AllocatingService(int memorySize) {
        this.memorySize = memorySize;
        this.processes = new ArrayList<>();

        algorithms = new ArrayList<>();
        algorithms.add(new FF());
        algorithms.add(new BF());
        algorithms.add(new WF());
    }

    public AbstractAllocatingAlgorithm[] getAlgorithms() {
        return algorithms.toArray(new AbstractAllocatingAlgorithm[0]);
    }

    public AllocatingResult[] addProcess(RunningProcess process) {
        processes.add(process);

        AllocatingResult[] results = new AllocatingResult[algorithms.size()];

        for (int i = 0; i < algorithms.size(); i++) {
            algorithms.get(i).addProcess(memorySize, process);
            results[i] = new AllocatingResult(algorithms.get(i).getAlgoName(), algorithms.get(i).getProcessOccupancies());
        }

        return results;
    }

    public AllocatingResult[] removeProcess(RunningProcess process) {
        processes.remove(process);

        for (int i = 0; i < algorithms.size(); i++) {
            algorithms.get(i).removeProcess(process);
        }

        AllocatingResult[] results = new AllocatingResult[algorithms.size()];
        for (int i = 0; i < algorithms.size(); i++) {
            results[i] = new AllocatingResult(algorithms.get(i).getAlgoName(), algorithms.get(i).getProcessOccupancies());
        }

        return results;
    }

}
