import scheduling.controller.SchedulingPanel;

import javax.swing.*;

public class MainFrame extends JFrame{

    JTabbedPane tabbedPane = new JTabbedPane();

    SchedulingPanel schedulingPanel = new SchedulingPanel();

    public MainFrame()  {
        this.setTitle("操作系统实验 20215178 秦骏言");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(tabbedPane);

        this.tabbedPane.add("1. Scheduling", schedulingPanel);
    }
}