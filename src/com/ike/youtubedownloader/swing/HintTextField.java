package com.ike.youtubedownloader.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author Ike
 * @version 1.0A
 **/
public class HintTextField extends JTextField {

    private String hintText;

    public HintTextField(String hintText) {
        super();
        this.hintText = hintText;
        this.setFont(this.getFont().deriveFont(20.f));
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                HintTextField.this.setFont(HintTextField.this.getFont().deriveFont(Font.PLAIN));
                HintTextField.this.setForeground(Color.BLACK);
                String text = HintTextField.super.getText();
                if (text.equals(HintTextField.this.hintText)) {
                    HintTextField.super.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (HintTextField.this.getText() == null || HintTextField.this.getText().equals("")) {
                    HintTextField.this.setText(HintTextField.this.hintText);
                    HintTextField.this.setFont(HintTextField.this.getFont().deriveFont(Font.ITALIC));
                    HintTextField.this.setForeground(Color.GRAY);
                }
            }
        });
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public String getHintText() {
        return hintText;
    }

    @Override
    public String getText() {
        if (super.getText().equals(this.hintText)) {
            return "";
        }
        return super.getText();
    }
}
