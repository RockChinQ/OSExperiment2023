package allocating.entity;

public class ProcessOccupancy {
    int allocatedBegin = 0;

    RunningProcess process;

    public ProcessOccupancy(int allocatedBegin, RunningProcess process) {
        this.allocatedBegin = allocatedBegin;
        this.process = process;
    }

    public int getAllocatedBegin() {
        return allocatedBegin;
    }

    public RunningProcess getProcess() {
        return process;
    }

    public int getStart() {
        return allocatedBegin;
    }

    public int getEnd() {
        return allocatedBegin + process.getNeedSize();
    }

    public int setStart(int start) {
        return allocatedBegin = start;
    }
}
