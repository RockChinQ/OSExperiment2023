package seeking.service;

import seeking.entity.CalcResult;
import seeking.service.algos.*;

public class DiskService {
    public static int[] generateRandomMagneticTracks(int initialMagneticTrack, int maxMagneticTrack, int amountOfGenerate) {
        int[] randomMagneticTracks = new int[amountOfGenerate];
        for (int i = 0; i < amountOfGenerate; i++) {
            int randomNumber = (int) (Math.random() * (maxMagneticTrack - initialMagneticTrack + 1)) + initialMagneticTrack;
            // 如果重复了，就重新生成
            while (true) {
                boolean isRepeat = false;
                for (int j = 0; j < i; j++) {
                    if (randomNumber == randomMagneticTracks[j]) {
                        isRepeat = true;
                        break;
                    }
                }
                if (isRepeat) {
                    randomNumber = (int) (Math.random() * (maxMagneticTrack - initialMagneticTrack + 1)) + initialMagneticTrack;
                } else {
                    break;
                }
            }
            randomMagneticTracks[i] = randomNumber;
        }
        return randomMagneticTracks;
    }

    public static ISeekingAlgorithm[] seekingAlgorithms;

    static {
        seekingAlgorithms = new ISeekingAlgorithm[]{
                new FCFS(),
                new SSTF(),
                new SCAN(),
                new CSCAN(),
                new NStepSCAN()
        };
    }

    public static CalcResult[] calc(int initialMagneticTrack, int[] targets) {
        CalcResult[] results = new CalcResult[seekingAlgorithms.length];
        for (int i = 0; i < seekingAlgorithms.length; i++) {
            results[i] = seekingAlgorithms[i].calc(initialMagneticTrack, targets);
        }
        return results;
    }
}
