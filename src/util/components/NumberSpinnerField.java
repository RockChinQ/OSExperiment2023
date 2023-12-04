package util.components;

import javax.swing.*;
import java.awt.*;

public class NumberSpinnerField extends JPanel {
    public static final Font LABEL_FONT = new Font("Dialog.bold", Font.BOLD, 14);
    public static final Font CONTENT_FONT = new Font("Dialog.bold", Font.BOLD, 12);


    public JLabel text = new JLabel("undefined");
    //    public JTextField input=new JTextField();
    public JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, -100, 10000, 1));
    public static final Color fcl = new Color(56, 56, 56);
    public static final Color bgf = new Color(255, 255, 255);

    public NumberSpinnerField(String name, int width, int height, double sep) {
        init(name, width, height, sep);
    }

    public NumberSpinnerField(String name, int width, int height, int sepx) {
        init(name, width, height, (double) sepx / (double) width);
    }

    public void init(String name, int width, int height, double sep) {
        this.setLayout(null);
        this.setSize(width, height);

        text.setText(name);
        text.setSize((int) (width * sep), height);
        text.setLocation(0, 0);
        text.setForeground(fcl);
        text.setFont(LABEL_FONT);
        this.add(text);

        spinner.setSize(width - (int) (width * sep), height - 4);
        spinner.setLocation((int) (width * sep), 2);
        spinner.setBorder(BorderFactory.createEtchedBorder());
        spinner.setFont(CONTENT_FONT);
        this.add(spinner);

    }

    public float getValue() {
        return Float.parseFloat(spinner.getValue().toString());
    }

    public void setValue(float v) {
        this.spinner.setValue(v);
    }

    public void updateCom() {
        text.setBackground(getBackground());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        text.setEnabled(enabled);
        spinner.setEnabled(enabled);
    }
}
