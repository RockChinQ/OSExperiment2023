import scheduling.controller.SchedulingPanel;
import seeking.controller.SeekingPanel;
import util.components.DialogManager;

import javax.swing.*;

public class MainFrame extends JFrame{

    JTabbedPane tabbedPane = new JTabbedPane();

    SchedulingPanel schedulingPanel = new SchedulingPanel();
    SeekingPanel seekingPanel = new SeekingPanel();

    public MainFrame()  {
        this.setTitle("操作系统实验 20215178 秦骏言");
        this.setSize(620, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(tabbedPane);

        DialogManager.setCenter(this);

        this.tabbedPane.add("1. Scheduling", schedulingPanel);
        this.tabbedPane.add("3. Seeking", seekingPanel);

        this.tabbedPane.setSelectedComponent(seekingPanel);
    }
}
