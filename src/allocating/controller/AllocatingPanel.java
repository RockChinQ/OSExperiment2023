package allocating.controller;

import allocating.entity.AllocatingResult;
import allocating.entity.RunningProcess;
import allocating.service.AllocatingService;
import allocating.service.AbstractAllocatingAlgorithm;
import util.components.NumberSpinnerField;

import javax.swing.*;
import java.util.ArrayList;

public class AllocatingPanel extends JPanel {
    NumberSpinnerField memorySizeField = new NumberSpinnerField("内存块数量", 160, 30, 80);

    JButton initializeButton = new JButton("初始化");

    JButton addProcess = new JButton("添加进程");

    AllocatingService allocatingService = null;
    ArrayList<MemoryGraphPanel> memoryGraphPanels = new ArrayList<>();
    public AllocatingPanel() {
        this.setLayout(null);
        this.setSize(620, 500);

        memorySizeField.setLocation(10, 10);
        memorySizeField.setValue(100);
        this.add(memorySizeField);

        initializeButton.setLocation(180, 10);
        initializeButton.setSize(100, 30);
        initializeButton.addActionListener((e) -> {
            // 初始化 分配服务
            allocatingService = new AllocatingService((int) memorySizeField.getValue());

            AbstractAllocatingAlgorithm[] algorithms = allocatingService.getAlgorithms();

            // 根据算法数量，创建对应数量的内存图面板
            for (AbstractAllocatingAlgorithm algo : algorithms){
                MemoryGraphPanel memoryGraphPanel = getMemoryGraphPanel(algo);
                memoryGraphPanel.setSize(600, 50);
                memoryGraphPanels.add(memoryGraphPanel);
                this.add(memoryGraphPanel);
            }

            AllocatingPanel.this.repaint();

            memorySizeField.setEnabled(false);
            initializeButton.setEnabled(false);
            addProcess.setEnabled(true);
        });
        this.add(initializeButton);

        addProcess.setLocation(280, 10);
        addProcess.setSize(100, 30);
        addProcess.setEnabled(false);
        addProcess.addActionListener((e) -> {
            // 添加进程
            AddProcessDialog addProcessDialog = new AddProcessDialog(this);
            addProcessDialog.setVisible(true);
        });
        this.add(addProcess);
    }

    private MemoryGraphPanel getMemoryGraphPanel(AbstractAllocatingAlgorithm algo) {
        MemoryGraphPanel memoryGraphPanel = new MemoryGraphPanel((int) memorySizeField.getValue(), algo.getAlgoName(), new IMemoryGraphListener() {
            @Override
            public void onProcessBlockClicked(RunningProcess process) {
                int option = JOptionPane.showConfirmDialog(AllocatingPanel.this, "进程名：" + process.getName() + "\n所需内存：" + process.getNeedSize()+"\n是否释放该进程？", "释放进程", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    AllocatingResult[] results = allocatingService.removeProcess(process);
                    updateProcessList(results);
                }
            }
        });
        memoryGraphPanel.setLocation(10, 60 + memoryGraphPanels.size() * 70);
        return memoryGraphPanel;
    }

    public void updateProcessList(AllocatingResult[] allocatingResults) {
        // 更新内存图
        for (AllocatingResult allocatingResult : allocatingResults) {
            for (MemoryGraphPanel memoryGraphPanel : memoryGraphPanels) {
                if (memoryGraphPanel.getName().equals(allocatingResult.getAlgoName())) {
                    memoryGraphPanel.setProcesses(allocatingResult.getProcessOccupancies());
                    memoryGraphPanel.repaint();
                }
            }
        }
    }
}
