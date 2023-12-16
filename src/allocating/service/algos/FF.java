package allocating.service.algos;

import allocating.entity.ProcessOccupancy;
import allocating.entity.RunningProcess;
import allocating.service.AbstractAllocatingAlgorithm;

public class FF extends AbstractAllocatingAlgorithm {
    @Override
    public String getAlgoName() {
        return "FF";
    }

    @Override
    public void addProcess(int memorySize, RunningProcess process) {
        int start = 0;

        // 根据每个已有进程的占用的空间，找出从0开始最符合process.getNeedSize的空间块
        int[] map = new int[memorySize];

        for (ProcessOccupancy occupancy : processOccupancies) {
            for (int j = occupancy.getStart(); j < occupancy.getEnd(); j++) {
                map[j] = 1;
            }
        }

        boolean allocated = false;

        int currentSpaceSize = 0;
        for (int i = 0; i < memorySize; i++) {
            if (map[i] == 0) {
                currentSpaceSize++;
                if (currentSpaceSize == process.getNeedSize()) {
                    start = i - currentSpaceSize + 1;
                    allocated = true;
                    break;
                }
            } else {
                currentSpaceSize = 0;
            }
        }

        if (allocated) {
            processOccupancies.add(new ProcessOccupancy(start, process));
        } else {
            System.out.println("内存不足");
        }
    }

}
