package org.mikemilligan.BlackJack.GUI.Buttons;

import javax.swing.*;

public class DoubleDownButton extends JButton {
    public DoubleDownButton() {
        String name = "Double Down";
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
