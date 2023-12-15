package seeking.service.algos;

import seeking.entity.CalcResult;
import seeking.service.ISeekingAlgorithm;

public class SSTF implements ISeekingAlgorithm {
    @Override
    public String getAlgoName() {
        return "SSTF";
    }

    @Override
    public CalcResult calc(int initialMagneticTrack, int[] targets) {
        // Shortest Seek Time First
        int totalMovingTracks = 0;
        int[] magneticTracksOrder = new int[targets.length];

        int[] targetsCopy = new int[targets.length];
        for (int i = 0; i < targets.length; i++) {
            targetsCopy[i] = targets[i];
        }

        for (int i = 0; i < targets.length; i++) {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < targetsCopy.length; j++) {
                if (Math.abs(targetsCopy[j] - initialMagneticTrack) < min) {
                    min = Math.abs(targetsCopy[j] - initialMagneticTrack);
                    minIndex = j;
                }
            }
            magneticTracksOrder[i] = targetsCopy[minIndex];
            totalMovingTracks += Math.abs(targetsCopy[minIndex] - initialMagneticTrack);
            initialMagneticTrack = targetsCopy[minIndex];
            targetsCopy[minIndex] = Integer.MAX_VALUE;
        }

        double averageSeekingLength = (double) totalMovingTracks / targets.length;

        return new CalcResult(getAlgoName(), magneticTracksOrder, totalMovingTracks, averageSeekingLength);
    }
}
