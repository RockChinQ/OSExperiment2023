package allocating.entity;

public class Space {
    int start;
    int end;

    public Space(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getSize() {
        return end - start;
    }
}
