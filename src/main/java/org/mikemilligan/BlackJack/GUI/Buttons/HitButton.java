package org.mikemilligan.BlackJack.GUI.Buttons;

import javax.swing.*;

public class HitButton extends JButton {
    public HitButton() {
        String name = "Hit";
        this.setText(name);
        // On click
        this.addActionListener(actionEvent -> {
            if (actionEvent.getActionCommand().equals(name)) {
                System.out.println(name);
            }
        });
    }

}
