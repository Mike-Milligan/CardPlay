package org.mikemilligan.BlackJack.GUI.Buttons;

import javax.swing.*;

public class SplitButton extends JButton {
    public SplitButton() {
        String name = "Split";
        this.setText(name);
        this.setEnabled(false);
        // On click
        this.addActionListener(actionEvent -> {
            if (actionEvent.getActionCommand().equals(name)) {
                System.out.println(name);
            }
        });
    }
}
