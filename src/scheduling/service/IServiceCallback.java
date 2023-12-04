package scheduling.service;

import scheduling.entity.ProcessControlBlock;

public interface IServiceCallback {
    void onPCBListChanged();
    void onFinishedListChanged();
}
