package org.mikemilligan.BlackJack.GUI;

import org.mikemilligan.BlackJack.GUI.Buttons.DoubleDownButton;
import org.mikemilligan.BlackJack.GUI.Buttons.HitButton;
import org.mikemilligan.BlackJack.GUI.Buttons.SplitButton;
import org.mikemilligan.BlackJack.GUI.Buttons.StandButton;
import org.mikemilligan.Card;
import org.mikemilligan.Deck;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class BlackJackGUI extends JFrame {
    Container contentPane;
    JLabel minBet;
    JLabel maxBet;
    JLabel currentBet;
    JLabel stage;
    JTextArea history;

    public BlackJackGUI() {
        this.setTitle("BlackJack");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setMinimumSize(new Dimension(400,400));
        this.setLocationRelativeTo(null); // Start in centre

        contentPane = setupContentPane(); // All the stuff goes in here

        this.setVisible(true);
    }

    private Container setupContentPane() {
        Container pane = getContentPane();

        // Add content to pane
        pane.add(setupPageStart(), BorderLayout.PAGE_START);
        pane.add(setupCentre(), BorderLayout.CENTER);
        pane.add(setupInfoPane(), BorderLayout.LINE_END);
        pane.add(setupButtonPanel(), BorderLayout.PAGE_END);

        return pane;
    }

    private JPanel setupPageStart() {
        JPanel pageStart = new JPanel();
        // Visuals
        pageStart.setBorder(new LineBorder(Color.black));
        // Components
        pageStart.add(new JLabel("page start"));
        return pageStart;
    }

    private JPanel setupCentre() {
        JPanel centre = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = createCommonConstraints(centre);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
//        constraints.anchor = GridBagConstraints.CENTER;
        // Visuals
        centre.setBorder(new LineBorder(Color.black));
        // Components
        JPanel dealer = setupHandPanel();
        dealer.setBorder(new LineBorder(Color.red));

        JPanel player = setupHandPanel();
        player.setBorder(new LineBorder(Color.blue));

        constraints.gridy = 0;
        centre.add(dealer, constraints);
        constraints.gridy = 1;
        centre.add(player, constraints);
        return centre;
    }

    private JPanel setupHandPanel() {
        JPanel handPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = createCommonConstraints(handPanel);
        constraints.anchor = GridBagConstraints.NORTH;
        JLabel handTotal = addTitledInfoLabel(handPanel, "Total", "XX");
        handTotal.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel cardPanel = new JPanel();
        Deck deck = new Deck(true);
        for (int i = 0; i < 2; i++) {
            Card card = deck.draw();
            ImageIcon cardImage = card.getIcon();
            JLabel cardLabel = new JLabel(cardImage);
            cardPanel.add(cardLabel);
        }
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        handPanel.add(cardPanel, constraints);

        return handPanel;
    }

    private JPanel setupInfoPane() {
        JPanel infoPane = new JPanel(new GridBagLayout());

        // Visuals
        infoPane.setBorder(new LineBorder(Color.black));
        setFixedWidth(infoPane, 150, 100, 400);

        // Components
        minBet = addTitledInfoLabel(infoPane, "Min Bet", "10");
        maxBet = addTitledInfoLabel(infoPane, "Max Bet", "1000");
        currentBet = addTitledInfoLabel(infoPane, "Current Bet", "100");
        stage = addTitledInfoLabel(infoPane, "Stage", "Your Turn");
        history = addTitledScrollPane(infoPane, "History");

        return infoPane;
    }

    private JLabel addTitledInfoLabel(JPanel panel, String title, String text) {
        GridBagConstraints constraints = createCommonConstraints(panel);

        JLabel label = new JLabel(text);
        label.setBorder(new TitledBorder(title));

        panel.add(label, constraints);

        return label;
    }

    private JTextArea addTitledScrollPane(JPanel panel, String title) {
        GridBagConstraints constraints = createCommonConstraints(panel);
        constraints.fill = GridBagConstraints.BOTH; // Fill remaining space
        constraints.weighty = 1; // Allow vertical expansion

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new TitledBorder(title));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane, constraints);

        return textArea;
    }

    private JPanel setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        // Visuals
        buttonPanel.setBorder(new LineBorder(Color.black));
        // Components
        buttonPanel.add(new SplitButton());
        buttonPanel.add(new HitButton());
        buttonPanel.add(new StandButton());
        buttonPanel.add(new DoubleDownButton());

        return buttonPanel;
    }

//    Helper Methods
    private void setFixedWidth(Component component, int width, int minHeight, int prefHeight) {
        component.setMinimumSize(new Dimension(width, minHeight));
        component.setPreferredSize(new Dimension(width, prefHeight));
    }

    private GridBagConstraints createCommonConstraints(JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL; // Fill horizontal space
        constraints.weightx = 1; // Allow horizontal expansion
        constraints.insets = new Insets(5, 3, 2, 5);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = panel.getComponentCount();

        return constraints;
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT)
        BlackJackGUI gui = new BlackJackGUI();
    }
}
