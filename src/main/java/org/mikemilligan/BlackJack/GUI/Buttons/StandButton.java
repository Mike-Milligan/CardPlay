package org.mikemilligan.BlackJack.GUI.Buttons;

import javax.swing.*;

public class StandButton extends JButton {
    public StandButton() {
        String name = "Stand";
        this.setText(name);
        // On click
        this.addActionListener(actionEvent -> {
            if (actionEvent.getActionCommand().equals(name)) {
                System.out.println(name);
            }
        });
    }
}
