package seeking.controller;

import seeking.entity.CalcResult;
import seeking.service.DiskService;
import util.components.InputField;
import util.components.NumberSpinnerField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

public class SeekingPanel extends JPanel {
    NumberSpinnerField initialMagneticTrackField = new NumberSpinnerField("初始磁道号", 150, 30, 80);
    NumberSpinnerField maxMagneticTrackField = new NumberSpinnerField("最大磁道号", 150, 30, 80);

    NumberSpinnerField amountOfGenerateField = new NumberSpinnerField("生成数量", 150, 30, 80);
    JButton generateRandomButton = new JButton("随机生成");
    JTextArea randomMagneticTrackField = new JTextArea();

    JButton calculateButton = new JButton("计算");

    String[] COLUMNS = new String[]{
            "算法",
            "顺序",
            "移动磁道总数",
            "平均寻道长度",
    };

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

    public SeekingPanel() {
        this.setLayout(null);

        initialMagneticTrackField.setLocation(20, 20);
        initialMagneticTrackField.setValue(50);
        this.add(initialMagneticTrackField);

        maxMagneticTrackField.setLocation(190, 20);
        maxMagneticTrackField.setValue(100);
        this.add(maxMagneticTrackField);

        amountOfGenerateField.setLocation(20, 60);
        amountOfGenerateField.setValue(10);
        this.add(amountOfGenerateField);

        generateRandomButton.setBounds(190, 60, 80, 30);
        generateRandomButton.addActionListener(e -> {
            int initialMagneticTrack = (int) initialMagneticTrackField.getValue();
            int maxMagneticTrack = (int) maxMagneticTrackField.getValue();
            int amountOfGenerate = (int) amountOfGenerateField.getValue();
            int[] randomMagneticTracks = DiskService.generateRandomMagneticTracks(0, maxMagneticTrack, amountOfGenerate);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < randomMagneticTracks.length; i++) {
                stringBuilder.append(randomMagneticTracks[i]);
                if (i != randomMagneticTracks.length - 1) {
                    stringBuilder.append(", ");
                }
            }
            randomMagneticTrackField.setText(stringBuilder.toString());

            calc();
        });
        this.add(generateRandomButton);

        randomMagneticTrackField.setBounds(360, 20, 150, 60);
//        randomMagneticTrackField.setEditable(false);
        randomMagneticTrackField.setLineWrap(true);
        randomMagneticTrackField.setBorder(BorderFactory.createEtchedBorder());
        this.add(randomMagneticTrackField);

        calculateButton.setBounds(520, 20, 80, 30);
        calculateButton.addActionListener(e -> {
            calc();
        });
        this.add(calculateButton);

        tableScrollPane.setBounds(20, 110, 560, 250);
        this.add(tableScrollPane);

        // 设置每一列宽度
        int[] columnWidth = new int[]{
                20,
                280,
                60,
                60,
        };

        for (int i = 0; i < columnWidth.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);
        }
    }

    public void calc(){

        if (randomMagneticTrackField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成随机序列");
            return;
        }
        String[] targetsString = randomMagneticTrackField.getText().split(",");

        int[] targets = new int[targetsString.length];
        for (int i = 0; i < targetsString.length; i++) {
            targets[i] = Integer.parseInt(targetsString[i].trim());
        }

        int initialMagneticTrack = (int) initialMagneticTrackField.getValue();
        CalcResult[] results = DiskService.calc(initialMagneticTrack, targets);

        // 更新表格
        model.getDataVector().clear();
        model.fireTableDataChanged();

        // 按照 CalcResult 的totalMovingTracks排序
        Arrays.sort(results, (o1, o2) -> {
            if (o1.getTotalMovingTracks() > o2.getTotalMovingTracks()) {
                return 1;
            } else if (o1.getTotalMovingTracks() < o2.getTotalMovingTracks()) {
                return -1;
            } else {
                return 0;
            }
        });

        for (int i = 0; i < results.length; i++) {
            CalcResult result = results[i];
            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j < result.getMagneticTracksOrder().length; j++) {
                stringBuilder.append(result.getMagneticTracksOrder()[j]);
                if (j != result.getMagneticTracksOrder().length - 1) {
                    stringBuilder.append(", ");
                }
            }

            model.addRow(new Object[]{
                    result.getAlgoName(),
                    stringBuilder.toString(),
                    result.getTotalMovingTracks(),
                    result.getAverageSeekingLength(),
            });
        }

        table.repaint();
    }
}
