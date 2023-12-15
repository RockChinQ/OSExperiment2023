package seeking.service.algos;

import seeking.entity.CalcResult;
import seeking.service.ISeekingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;

public class CSCAN implements ISeekingAlgorithm {

    @Override
    public String getAlgoName() {
        return "CSCAN";
    }

    @Override
    public CalcResult calc(int initialMagneticTrack, int[] targets) {
        int totalMovingTracks = 0;
        int[] magneticTracksOrder = new int[targets.length];

        int[] targetsCopy = new int[targets.length];
        for (int i = 0; i < targets.length; i++) {
            targetsCopy[i] = targets[i];
        }

        ArrayList<Integer> targetLargerThanInitial = new ArrayList<>();
        ArrayList<Integer> targetSmallerThanInitial = new ArrayList<>();

        for (int i = 0; i < targetsCopy.length; i++) {
            if (targetsCopy[i] > initialMagneticTrack) {
                targetLargerThanInitial.add(targetsCopy[i]);
            } else {
                targetSmallerThanInitial.add(targetsCopy[i]);
            }
        }

        // 从小到大排序
        targetLargerThanInitial.sort(Comparator.comparingInt(a -> a));
        targetSmallerThanInitial.sort(Comparator.comparingInt(a -> a));

        int resultIndex = 0;
        // 先走到最外面
        for (int i = 0; i< targetLargerThanInitial.size(); i++) {
            magneticTracksOrder[resultIndex] = targetLargerThanInitial.get(i);
            totalMovingTracks += Math.abs(targetLargerThanInitial.get(i) - initialMagneticTrack);
            initialMagneticTrack = targetLargerThanInitial.get(i);
            resultIndex++;
        }

        // 再从最里面走到最外面
        for (int i = 0; i < targetSmallerThanInitial.size(); i++) {
            magneticTracksOrder[resultIndex] = targetSmallerThanInitial.get(i);
            totalMovingTracks += Math.abs(targetSmallerThanInitial.get(i) - initialMagneticTrack);
            initialMagneticTrack = targetSmallerThanInitial.get(i);
            resultIndex++;
        }

        double averageSeekingLength = (double) totalMovingTracks / targets.length;

        return new CalcResult(getAlgoName(), magneticTracksOrder, totalMovingTracks, averageSeekingLength);
    }
}
