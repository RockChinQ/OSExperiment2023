package allocating.controller;

import allocating.entity.ProcessOccupancy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class MemoryGraphPanel extends JPanel {
    public static final int SHEET_WIDTH = 6;
    public static final Font FONT = new Font("Serif", Font.PLAIN, 8);
    public static final Font PROCESS_NAME = new Font("Serif", Font.PLAIN, 10);

    protected int sheetAmount = 100;

    private String name;

    public String getName() {
        return name;
    }

    ArrayList<ProcessOccupancy> processes = new ArrayList<>();

    public void setProcesses(ArrayList<ProcessOccupancy> processes) {
        this.processes.clear();
        this.processes.addAll(processes);
    }

    IMemoryGraphListener iMemoryGraphListener;

    public MemoryGraphPanel(int sheetAmount, String name, IMemoryGraphListener listener) {
        this.sheetAmount = sheetAmount;
        this.name = name;
        this.iMemoryGraphListener = listener;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // 点击一个进程时，触发事件
                int sheetIndex = e.getX() / SHEET_WIDTH;
                for (ProcessOccupancy process : processes) {
                    if (process.getAllocatedBegin() <= sheetIndex && process.getAllocatedBegin() + process.getProcess().getNeedSize() > sheetIndex) {
                        iMemoryGraphListener.onProcessBlockClicked(process.getProcess());
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {

        // 绘制算法名称
        g.drawString(name, 0, 10);
        g.setColor(Color.GREEN);
        g.fillRect(0, 15,SHEET_WIDTH*sheetAmount, getHeight()-23);

        g.setFont(PROCESS_NAME);
        // 绘制已分配
        for (ProcessOccupancy process : processes) {
            g.setColor(Color.RED);
            int begin = process.getAllocatedBegin();
            int end = begin + process.getProcess().getNeedSize();
            g.fillRect(begin * SHEET_WIDTH, 15, (end - begin) * SHEET_WIDTH, getHeight()-23);
            g.setColor(Color.BLACK);
            g.drawString(process.getProcess().getName(), begin * SHEET_WIDTH, 25);
        }

        // 绘制分割线
        g.setColor(Color.BLACK);
        for (int i = 0; i < sheetAmount; i++) {
            g.drawLine(i * SHEET_WIDTH, 15, i * SHEET_WIDTH, getHeight()-(i%10==0?0:8));
        }

        g.setFont(FONT);
        // 绘制每格标号
        for (int i = 0; i < sheetAmount; i++) {
            if (i%10!=0){
                continue;
            }
            g.drawString(String.valueOf(i), i * SHEET_WIDTH, 50);
        }
    }
}
