package seeking.service;

import seeking.entity.CalcResult;

public interface ISeekingAlgorithm {
    public String getAlgoName();
    public CalcResult calc(int initialMagneticTrack, int[] targets);
}
