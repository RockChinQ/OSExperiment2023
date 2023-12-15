package seeking.service.algos;

import seeking.entity.CalcResult;
import seeking.service.ISeekingAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class NStepSCAN implements ISeekingAlgorithm {
    public static final int QUEUE_SIZE = 5;
    @Override
    public String getAlgoName() {
        return "NStepSCAN";
    }

    @Override
    public CalcResult calc(int initialMagneticTrack, int[] targets) {
        int totalMovingTracks = 0;

        int[] magneticTracksOrder = new int[targets.length];

        ArrayList<ArrayList<Integer>> queues = new ArrayList<>();

        ArrayList<Integer> currentQueue = new ArrayList<>();

        for (int i = 0; i < targets.length; i++) {
            if (currentQueue.size() < QUEUE_SIZE) {
                currentQueue.add(targets[i]);
            } else {
                queues.add(currentQueue);
                currentQueue = new ArrayList<>();
                currentQueue.add(targets[i]);
            }
        }
        if (queues.get(queues.size() - 1) != currentQueue) {
            queues.add(currentQueue);
        }

        // 排序每个队列
        for (ArrayList<Integer> queue : queues) {
            queue.sort(Integer::compareTo);
        }

        int index = 0;

        // FCFS 每个队列
        for (ArrayList<Integer> queue : queues) {
            ArrayList<Integer> targetLargerThanInitial = new ArrayList<>();
            ArrayList<Integer> targetSmallerThanInitial = new ArrayList<>();

            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i) > initialMagneticTrack) {
                    targetLargerThanInitial.add(queue.get(i));
                } else {
                    targetSmallerThanInitial.add(queue.get(i));
                }
            }

            // 从小到大排序
            targetLargerThanInitial.sort(Integer::compareTo);
            targetSmallerThanInitial.sort(Integer::compareTo);

            // 先走到最外面
            for (int i = 0; i< targetLargerThanInitial.size(); i++) {
                magneticTracksOrder[index] = targetLargerThanInitial.get(i);
                totalMovingTracks += Math.abs(targetLargerThanInitial.get(i) - initialMagneticTrack);
                initialMagneticTrack = targetLargerThanInitial.get(i);
                index++;
            }

            // 再走到最里面
            for (int i = targetSmallerThanInitial.size() - 1; i >= 0; i--) {
                magneticTracksOrder[index] = targetSmallerThanInitial.get(i);
                totalMovingTracks += Math.abs(targetSmallerThanInitial.get(i) - initialMagneticTrack);
                initialMagneticTrack = targetSmallerThanInitial.get(i);
                index++;
            }
        }

        double averageSeekingLength = (double) totalMovingTracks / targets.length;

        return new CalcResult(getAlgoName()+"(N="+NStepSCAN.QUEUE_SIZE+")", magneticTracksOrder, totalMovingTracks, averageSeekingLength);
    }
}
