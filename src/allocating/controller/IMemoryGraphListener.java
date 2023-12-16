package allocating.controller;

import allocating.entity.RunningProcess;

public interface IMemoryGraphListener {
    public void onProcessBlockClicked(RunningProcess process);
}
