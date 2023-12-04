package scheduling.controller;

import scheduling.entity.ProcessControlBlock;
import util.components.DialogManager;
import util.components.InputField;
import util.components.NumberSpinnerField;
import scheduling.service.SchedulingService;

import javax.swing.*;

public class AddProcessControlBlockDialog extends JDialog {
    SchedulingService schedulingService;

    InputField nameInputField = new InputField("Name", 150, 30, 70);
    NumberSpinnerField priorityInputField = new NumberSpinnerField("优先级", 150, 30, 70);
    NumberSpinnerField occupyTimeInputField = new NumberSpinnerField("运行时间", 150, 30, 70);

    JButton confirmButton = new JButton("确认");
    public AddProcessControlBlockDialog(SchedulingService service){
        this.setTitle("添加 PCB");
        this.schedulingService = service;

        this.setLayout(null);
        this.setBounds(0, 0, 200, 220);

        DialogManager.setCenter(this);

        nameInputField.setLocation(20,20);
        this.add(nameInputField);

        priorityInputField.setLocation(20, 60);
        this.add(priorityInputField);

        occupyTimeInputField.setLocation(20, 100);
        this.add(occupyTimeInputField);

        confirmButton.setBounds(20, 140, 100, 35);
        confirmButton.addActionListener((e)->{
            if (nameInputField.getValue().isBlank()) {
                javax.swing.JOptionPane.showMessageDialog(AddProcessControlBlockDialog.this, "进程名不能为空");
                return;
            }
            schedulingService.addProcessControlBlock(
                    new ProcessControlBlock(
                            nameInputField.getValue(),
                            (int) priorityInputField.getValue(),
                            (int) occupyTimeInputField.getValue()
                    )
            );
            AddProcessControlBlockDialog.this.dispose();
        });
        this.add(confirmButton);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
