package allocating.controller;

import allocating.entity.RunningProcess;
import allocating.service.AllocatingService;
import util.components.DialogManager;
import util.components.InputField;
import util.components.NumberSpinnerField;

import javax.swing.*;

public class AddProcessDialog extends JDialog {
    AllocatingPanel allocatingPanel;

    InputField processName = new InputField("进程名", 150, 30, 70);
    NumberSpinnerField needSize = new NumberSpinnerField("所需内存", 150, 30, 70);

    JButton confirmButton = new JButton("确认");

    public AddProcessDialog(AllocatingPanel allocatingPanel) {
        this.setTitle("添加进程");
        this.setLayout(null);
        this.setBounds(0, 0, 200, 220);
        this.allocatingPanel = allocatingPanel;

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        processName.setLocation(20, 20);
        this.add(processName);

        needSize.setLocation(20, 60);
        needSize.setValue(10);
        this.add(needSize);

        confirmButton.setBounds(20, 120, 100, 35);
        confirmButton.addActionListener((e) -> {
            if (processName.getValue().isBlank()) {
                JOptionPane.showMessageDialog(AddProcessDialog.this, "进程名不能为空");
                return;
            }
            this.allocatingPanel.updateProcessList(this.allocatingPanel.allocatingService.addProcess(new RunningProcess(
                    processName.getValue(),
                    (int) needSize.getValue()
            )));
            AddProcessDialog.this.dispose();
        });
        this.add(confirmButton);

        DialogManager.setCenter(this);
    }
}
