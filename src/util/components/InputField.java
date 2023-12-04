package util.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class InputField extends JPanel {
    public static final Font LABEL_FONT = new Font("Dialog.bold", Font.BOLD, 14);
    public static final Font CONTENT_FONT = new Font("Dialog.bold", Font.BOLD, 12);

    public JLabel text = new JLabel("undefined");
    public JTextField input = new JTextField();
    public static final Color fcl = new Color(56, 56, 56);
    public static final Color bgf = new Color(255, 255, 255);

//    public InputField(String name, int width, int height, double sep) {
//        init(name, width, height, sep);
//    }

    public InputField(String name, int width, int height, int sepx) {
        init(name, width, height, (double) sepx / (double) width, null);
    }

    public InputField(String name, int width, int height, int sepx, IInputFieldModifiedListener modifiedListener) {
        init(name, width, height, (double) sepx / (double) width, modifiedListener);
    }

    public void init(String name, int width, int height, double sep, IInputFieldModifiedListener modifiedListener) {
        this.setLayout(null);
        this.setSize(width, height);

        text.setText(name);
        text.setSize((int) (width * sep), height);
        text.setLocation(0, 0);
        text.setForeground(fcl);
        text.setFont(LABEL_FONT);
        this.add(text);

        input.setSize(width - (int) (width * sep), height - 4);
        input.setLocation((int) (width * sep), 2);
        input.setForeground(fcl);
        input.setBackground(bgf);
        input.setCaretColor(fcl);
        input.setBorder(null);
        input.setBorder(BorderFactory.createEtchedBorder());
        input.setFont(CONTENT_FONT);
        this.add(input);

        if (modifiedListener != null) {
            input.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    modifiedListener.modified();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    modifiedListener.modified();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    modifiedListener.modified();
                }
            });
        }

//        TextEditPopupMenu.patchTo(input);
    }

    public String getValue() {
        return input.getText();
    }

    public void setValue(String s) {
        this.input.setText(s);
    }

    public void updateCom() {
        text.setBackground(getBackground());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        text.setEnabled(enabled);
        input.setEnabled(enabled);
    }
}
