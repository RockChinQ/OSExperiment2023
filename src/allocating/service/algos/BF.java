package allocating.service.algos;

import allocating.entity.ProcessOccupancy;
import allocating.entity.RunningProcess;
import allocating.entity.Space;
import allocating.service.AbstractAllocatingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;


public class BF extends AbstractAllocatingAlgorithm {
    @Override
    public String getAlgoName() {
        return "BF";
    }

    @Override
    public void addProcess(int memorySize, RunningProcess process) {
        int[] map = new int[memorySize];

        for (ProcessOccupancy occupancy : processOccupancies) {
            for (int j = occupancy.getStart(); j < occupancy.getEnd(); j++) {
                map[j] = 1;
            }
        }

        // 找到所有空间块
        ArrayList<Space> spaces = new ArrayList<>();

        int currentSpaceSize = 0;

        for (int i = 0; i < memorySize; i++) {
            if (map[i] == 0) {
                currentSpaceSize++;
                if (i == memorySize - 1) {
                    spaces.add(new Space(i - currentSpaceSize + 1, i));
                }
            } else {
                if (currentSpaceSize > 0) {
                    spaces.add(new Space(i - currentSpaceSize, i));
                }
                currentSpaceSize = 0;
            }
        }

        // 找到空间大于 process.getNeedSize() 的最小的空间块
        spaces.sort(Comparator.comparingInt(Space::getSize));

        boolean allocated = false;
        for (Space space : spaces) {
            if (space.getSize() >= process.getNeedSize()) {
                processOccupancies.add(new ProcessOccupancy(space.getStart(), process));
                allocated = true;
                break;
            }
        }

        if (!allocated) {
            System.out.println("内存不足");
        }

    }
}
