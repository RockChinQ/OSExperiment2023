package allocating.entity;

public class RunningProcess {
    String name;

    int needSize;

    public RunningProcess(String name, int needSize) {
        this.name = name;
        this.needSize = needSize;
    }

    public String getName() {
        return name;
    }

    public int getNeedSize() {
        return needSize;
    }
}
