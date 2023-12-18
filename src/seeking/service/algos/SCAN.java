package seeking.service.algos;

import seeking.entity.CalcResult;
import seeking.service.ISeekingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;

public class SCAN implements ISeekingAlgorithm {
    @Override
    public String getAlgoName() {
        return "SCAN";
    }

    @Override
    public CalcResult calc(int initialMagneticTrack, int[] targets) {
        // SCAN,当磁头正在由里向外移动时，SCAN算法所选择的下一个访问对象应是其欲访问的磁道，既在当前磁道之外，又是距离最近的。这样由里向外地访问，直至再无更外的磁道需要访问时，才将换向，由外向里移动。也叫电梯算法。
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
        for (Integer integer : targetLargerThanInitial) {
            magneticTracksOrder[resultIndex] = integer;
            totalMovingTracks += Math.abs(integer - initialMagneticTrack);
            initialMagneticTrack = integer;
            resultIndex++;
        }

        // 再走到最里面
        for (int i = targetSmallerThanInitial.size() - 1; i >= 0; i--) {
            magneticTracksOrder[resultIndex] = targetSmallerThanInitial.get(i);
            totalMovingTracks += Math.abs(targetSmallerThanInitial.get(i) - initialMagneticTrack);
            initialMagneticTrack = targetSmallerThanInitial.get(i);
            resultIndex++;
        }

        double averageSeekingLength = (double) totalMovingTracks / targets.length;

        return new CalcResult(getAlgoName(), magneticTracksOrder, totalMovingTracks, averageSeekingLength);
    }
}
