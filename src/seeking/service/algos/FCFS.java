package seeking.service.algos;

import seeking.entity.CalcResult;
import seeking.service.ISeekingAlgorithm;

public class FCFS implements ISeekingAlgorithm {

    @Override
    public String getAlgoName() {
        return "FCFS";
    }

    @Override
    public CalcResult calc(int initialMagneticTrack, int[] targets) {
        // First Come First Serve
        int totalMovingTracks = 0;
        int[] magneticTracksOrder = new int[targets.length];

        for (int i = 0; i < targets.length; i++) {
            magneticTracksOrder[i] = targets[i];
            totalMovingTracks += Math.abs(targets[i] - initialMagneticTrack);
            initialMagneticTrack = targets[i];
        }

        double averageSeekingLength = (double) totalMovingTracks / targets.length;

        return new CalcResult(getAlgoName(), magneticTracksOrder, totalMovingTracks, averageSeekingLength);
    }
}
