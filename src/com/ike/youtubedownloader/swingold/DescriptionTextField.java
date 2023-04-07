package com.ike.youtubedownloader.swingold;

import javax.swing.*;
import java.awt.*;

/**
 * A text field with a Description on top of it
 *
 * @author Ike
 * @version 1.0A
 **/
public class DescriptionTextField extends JPanel {

    private JLabel descriptionLabel;
    private String description;
    private int descriptionWidth;
    private JTextField field;

    public DescriptionTextField(String description) {
        super();
        this.description = description;
        this.descriptionLabel = new JLabel(description);
        this.calculateWidth();
        this.field = new JTextField();
        this.setLayout(null);
        this.descriptionLabel.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.BLACK));
        this.field.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.BLACK));
        this.add(this.descriptionLabel);
        this.add(this.field);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.descriptionLabel.setSize(this.descriptionWidth, height);
        this.field.setSize(width - this.descriptionWidth, height);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.descriptionLabel.setBounds(x, 0, this.descriptionWidth, height);
        this.field.setBounds(x + this.descriptionWidth, 0, width - this.descriptionWidth, height);
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (this.descriptionLabel != null) {
            this.descriptionLabel.setFont(font);
            calculateWidth();
            this.field.setFont(font);
            this.descriptionLabel.setSize(this.descriptionWidth, this.descriptionLabel.getHeight());
            this.field.setSize(this.getWidth() - this.descriptionWidth, this.field.getHeight());
        }
    }

    /**
     * @return The text on the TextField
     */
    public String getFieldValue() {
        return this.field.getText();
    }


    /**
     * Set the text of the TextField
     *
     * @param value The text to set
     */
    public void setFieldValue(String value) {
        this.field.setText(value);
    }

    private void calculateWidth() {
        this.descriptionWidth = (int) (this.description.length() * (this.getFont().getSize() / 1.5f));
    }
}
