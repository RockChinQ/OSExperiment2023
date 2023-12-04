package scheduling.controller;

import scheduling.entity.ProcessControlBlock;
import scheduling.service.IServiceCallback;
import scheduling.service.SchedulingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SchedulingPanel extends JPanel {

    SchedulingService schedulingService = new SchedulingService();

    String[] COLUMNS = new String[]{"ID", "Name", "Priority", "Time"};
    DefaultTableModel model = new DefaultTableModel(new Object[0][0], COLUMNS);

    JTable table = new JTable(model) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        @Override
        public boolean isCellSelected(int row, int column) {
            return false;
        }
    };
    JScrollPane tableScrollPane = new JScrollPane(table);


    String[] FINISHED_COLUMNS = new String[]{"ID", "Name"};
    DefaultTableModel finishedModel = new DefaultTableModel(new Object[0][0], FINISHED_COLUMNS);

    JTable finishedTable = new JTable(finishedModel) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        @Override
        public boolean isCellSelected(int row, int column) {
            return false;
        }
    };

    JScrollPane finishedTableScrollPane = new JScrollPane(finishedTable);

    JLabel runningProcessLabel = new JLabel("当前运行进程：");
    JLabel finishedProcessLabel = new JLabel("已完成进程：");
    public SchedulingPanel() {
        this.setLayout(null);

        JButton addProcessControlBlockButton = new JButton("添加 PCB");
        addProcessControlBlockButton.setBounds(10, 10, 150, 30);
        addProcessControlBlockButton.addActionListener(e -> {
            AddProcessControlBlockDialog addProcessControlBlockDialog = new AddProcessControlBlockDialog(schedulingService);
            addProcessControlBlockDialog.setVisible(true);
        });
        this.add(addProcessControlBlockButton);

        JButton nextStepButton = new JButton("运行一步");
        nextStepButton.setBounds(160, 10, 150, 30);
        nextStepButton.addActionListener(e -> {
            schedulingService.nextStep();
            this.repaint();
        });
        this.add(nextStepButton);

        JButton clearAllButton = new JButton("清空所有");
        clearAllButton.setBounds(310, 10, 150, 30);
        clearAllButton.addActionListener(e -> {
            schedulingService.clear();
            this.repaint();
        });
        this.add(clearAllButton);

//        table.setBounds(10, 50, 300, 300);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(30);
//        this.add(table);

        runningProcessLabel.setBounds(10, 50, 300, 20);
        this.add(runningProcessLabel);

        tableScrollPane.setBounds(10, 80, 300, 330);
        this.add(tableScrollPane);

        finishedProcessLabel.setBounds(320, 50, 300, 20);
        this.add(finishedProcessLabel);

        finishedTableScrollPane.setBounds(320, 80, 150, 330);
        this.add(finishedTableScrollPane);
        schedulingService.setServiceCallback(new IServiceCallback() {
            @Override
            public void onPCBListChanged() {
                DefaultTableModel model = SchedulingPanel.this.model;

                model.getDataVector().clear();
                model.fireTableDataChanged();

                for (ProcessControlBlock pcb : schedulingService.getProcessControlBlocks()) {
                    model.addRow(new Object[]{
                            pcb.getId(),
                            pcb.getName(),
                            pcb.getPriority(),
                            pcb.getOccupyTime()
                    });
                }

                SchedulingPanel.this.table.repaint();
            }

            @Override
            public void onFinishedListChanged() {
                DefaultTableModel finishedModel = SchedulingPanel.this.finishedModel;

                finishedModel.getDataVector().clear();
                finishedModel.fireTableDataChanged();

                for (ProcessControlBlock pcb : schedulingService.getFinishedProcessControlBlocks()) {
                    finishedModel.addRow(new Object[]{
                            pcb.getId(),
                            pcb.getName()
                    });
                }

                SchedulingPanel.this.finishedTable.repaint();
            }
        });
    }

}
