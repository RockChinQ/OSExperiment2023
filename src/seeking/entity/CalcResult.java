package seeking.entity;

public class CalcResult {
    String algoName;
    int[] magneticTracksOrder;
    int totalMovingTracks;
    double averageSeekingLength;

    public CalcResult(String algoName, int[] magneticTracksOrder, int totalMovingTracks, double averageSeekingLength) {
        this.algoName = algoName;
        this.magneticTracksOrder = magneticTracksOrder;
        this.totalMovingTracks = totalMovingTracks;
        this.averageSeekingLength = averageSeekingLength;
    }

    public String getAlgoName() {
        return algoName;
    }

    public int[] getMagneticTracksOrder() {
        return magneticTracksOrder;
    }

    public int getTotalMovingTracks() {
        return totalMovingTracks;
    }

    public double getAverageSeekingLength() {
        return averageSeekingLength;
    }
}
