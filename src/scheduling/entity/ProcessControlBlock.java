package scheduling.entity;

public class ProcessControlBlock {
    public static int INDEX = 0;

    int id = 0;

    String name;

    int priority;

    int occupyTime;

    PCBStatus status;

    public int getId() {
        return id;
    }

    public ProcessControlBlock(String name, int priority, int occupyTime) {
        this.name = name;
        this.priority = priority;
        this.occupyTime = occupyTime;
        this.status = PCBStatus.READY;

        this.id = INDEX;
        INDEX++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getOccupyTime() {
        return occupyTime;
    }

    public void setOccupyTime(int occupyTime) {
        this.occupyTime = occupyTime;
    }

    public PCBStatus getStatus() {
        return status;
    }

    public void setStatus(PCBStatus status) {
        this.status = status;
    }
}
