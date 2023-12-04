package scheduling.service;

import scheduling.entity.PCBStatus;
import scheduling.entity.ProcessControlBlock;

import java.util.ArrayList;

public class SchedulingService {
    IServiceCallback serviceCallback;

    ArrayList<ProcessControlBlock> processControlBlocks = new ArrayList<>();

    ArrayList<ProcessControlBlock> finishedProcessControlBlocks = new ArrayList<>();
    public SchedulingService() {
    }

    private void sortDesc() {
        processControlBlocks.sort((o1, o2) -> o2.getPriority() - o1.getPriority());
    }

    public IServiceCallback getServiceCallback() {
        return serviceCallback;
    }

    public void setServiceCallback(IServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    public void addProcessControlBlock(ProcessControlBlock processControlBlock) {
        processControlBlocks.add(processControlBlock);

        this.sortDesc();

        this.serviceCallback.onPCBListChanged();
    }

    public ArrayList<ProcessControlBlock> getProcessControlBlocks() {
        return processControlBlocks;
    }

    public ArrayList<ProcessControlBlock> getFinishedProcessControlBlocks() {
        return finishedProcessControlBlocks;
    }

    public void nextStep() {
        if (processControlBlocks.size() > 0) {
            ProcessControlBlock processControlBlock = processControlBlocks.get(0);
            processControlBlock.setOccupyTime(processControlBlock.getOccupyTime() - 1);
            processControlBlock.setPriority(processControlBlock.getPriority() - 1);
            if (processControlBlock.getOccupyTime() <= 0) {
                processControlBlock.setStatus(PCBStatus.FINISHED);
                finishedProcessControlBlocks.add(processControlBlock);
                this.serviceCallback.onFinishedListChanged();
                processControlBlocks.remove(0);
            }
            this.sortDesc();
            this.serviceCallback.onPCBListChanged();
        }
    }

    public void clear() {
        processControlBlocks.clear();
        finishedProcessControlBlocks.clear();
        this.serviceCallback.onPCBListChanged();
        this.serviceCallback.onFinishedListChanged();
    }

}
