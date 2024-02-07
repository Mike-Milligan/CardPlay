package org.mikemilligan.BlackJack.GUI;

import org.mikemilligan.BlackJack.Hand;
import org.mikemilligan.Card;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class BlackJackGUI extends JFrame {
    private static final Insets INSETS = new Insets(5, 3, 2, 5);
    Container contentPane;
    JLabel minBet;
    JLabel maxBet;
    JLabel currentBet;
    JLabel stage;
    JTextArea history;
    JPanel dealer;
    JPanel playersPanel;
    List<JPanel> playerPanels;

//    HitButton hitButton;
//    StandButton standButton;
//    SplitButton splitButton;
//    DoubleDownButton doubleDownButton;

    public BlackJackGUI() {
        playerPanels = new ArrayList<>();
        setupFrame();

        contentPane = setupContentPane(); // All the stuff goes in here

        this.setVisible(true);
    }

    private void setupFrame() {
        this.setTitle("BlackJack");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setMinimumSize(new Dimension(400, 500));
        this.setLocationRelativeTo(null); // Start in centre
    }

    private Container setupContentPane() {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        // Add content to pane
//        pane.add(setupPageStart(), BorderLayout.PAGE_START);
        pane.add(setupCentre(), BorderLayout.CENTER);
        pane.add(setupInfoPane(), BorderLayout.LINE_END);
//        pane.add(setupButtonPanel(), BorderLayout.PAGE_END);

        return pane;
    }

    private JPanel setupPageStart() {
        JPanel pageStart = new JPanel();
        // Visuals
        pageStart.setBorder(new LineBorder(Color.black));
        // Components
//        pageStart.add(new JLabel("page start"));
        return pageStart;
    }

    private JPanel setupCentre() {
        JPanel centre = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = createCommonConstraints(centre);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1; // Allow vertical expansion
        // Visuals
        centre.setBorder(new LineBorder(Color.black));
        // Components
        dealer = setupPlayPanel(true);
        dealer.setBorder(new LineBorder(Color.red));

        playersPanel = setupPlayPanel(false);
        playersPanel.setBorder(new LineBorder(Color.blue));

        constraints.gridy = 0;
        centre.add(dealer, constraints);
        constraints.gridy = 1;
        centre.add(playersPanel, constraints);
        return centre;
    }

    private JPanel setupPlayPanel(boolean dealerHand) {
        JPanel playPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = createCommonConstraints(playPanel);

        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;

        if (dealerHand) {
            return createHandPanel(false);
        }

        return playPanel;
    }

    private JPanel createHandPanel(boolean isPlayerHand) {
        JPanel handPanel = new JPanel(new GridBagLayout());


        GridBagConstraints constraints = createCommonConstraints(handPanel);
        constraints.anchor = GridBagConstraints.NORTH;

        JLabel handTotal = addTitledInfoLabel(handPanel, "Total", "0");
        handTotal.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(null);

        constraints.gridy = handPanel.getComponentCount();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        handPanel.add(cardPanel, constraints);

        if (isPlayerHand) {
            JLabel currentBet = addTitledInfoLabel(handPanel, "Current Bet", "0");
            currentBet.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel balance = addTitledInfoLabel(handPanel, "Balance", "500");
            balance.setHorizontalAlignment(SwingConstants.CENTER);
        }

        handPanel.setBorder(new LineBorder(Color.green));

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

//    private JPanel setupButtonPanel() {
//        JPanel buttonPanel = new JPanel(new FlowLayout());
//        // Visuals
//        buttonPanel.setBorder(new LineBorder(Color.black));
//        // Components
//        splitButton = new SplitButton();
//        hitButton = new HitButton();
//        standButton = new StandButton();
//        doubleDownButton = new DoubleDownButton();
//
//        buttonPanel.add(splitButton);
//        buttonPanel.add(hitButton);
//        buttonPanel.add(standButton);
//        buttonPanel.add(doubleDownButton);
//
//        return buttonPanel;
//    }

    //    Helper Methods
    private void setFixedWidth(Component component, int width, int minHeight, int prefHeight) {
        component.setMinimumSize(new Dimension(width, minHeight));
        component.setPreferredSize(new Dimension(width, prefHeight));
    }

    private GridBagConstraints createCommonConstraints(JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL; // Fill horizontal space
        constraints.weightx = 1; // Allow horizontal expansion
        constraints.insets = INSETS;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = panel.getComponentCount();

        return constraints;
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            BlackJackGUI gui = new BlackJackGUI();
        });
    }


    // Update display values
    public void refresh() {
        revalidate();
        repaint();
    }

    public void setMinBet(int value) {
        minBet.setText(String.valueOf(value));
        refresh();
    }

    public void setMaxBet(int value) {
        maxBet.setText(String.valueOf(value));
        refresh();
    }

    public void setCurrentBet(int value) {
        currentBet.setText(String.valueOf(value));
        refresh();
    }

    public void setStage(String value) {
        stage.setText(value);
        addToHistory(value);
        refresh();
    }

    public void addToHistory(String text) {
        history.append(text + "\n");
        history.setCaretPosition(history.getDocument().getLength());
        refresh();
    }

    // Get Input / Display message
    public int promptForIntValue(String message) {
        String input = JOptionPane.showInputDialog(null, message);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showErrorMessage("Value must be an integer");
            return promptForIntValue(message);
        }
    }

    public String promptForStringValue(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    public Button promptForOption() {
        String[] options = {"HIT", "STAND"};

        var selection = JOptionPane.showOptionDialog(
                null,
                "Select one:",
                "Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        return switch (selection) {
            case 0 -> Button.HIT;
            case 1 -> Button.STAND;
            default -> null;
        };
    }

    public int promptForYesNo(String message) {

        return JOptionPane.showOptionDialog(
                null,
                message,
                "Choose",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
                );
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfoMessage(String message) {
        JOptionPane.showConfirmDialog(null, message, "Info", JOptionPane.DEFAULT_OPTION);
    }

    // Player setup
    public void addPlayerHand(String name) {
        GridBagConstraints constraints = createCommonConstraints(playersPanel);

        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = playersPanel.getComponentCount();
        JPanel handPanel = createHandPanel(true);
        handPanel.setBorder(new TitledBorder(name));
        handPanel.setName(name);
        playerPanels.add(handPanel);
        playersPanel.add(handPanel, constraints);
        refresh();
    }

    public void updatePlayerBet(int playerId, int bet) {
        JPanel playerPanel = playerPanels.get(playerId);
        JLabel betLabel = (JLabel) playerPanel.getComponent(2);
        betLabel.setText(String.valueOf(bet));
        refresh();
    }

    public void updatePlayerBalance(int playerId, int balance) {
        JPanel playerPanel = playerPanels.get(playerId);
        JLabel balanceLabel = (JLabel) playerPanel.getComponent(3);
        balanceLabel.setText(String.valueOf(balance));
        refresh();
    }

    public void updateHand(int playerId, Hand hand) {
        final int DEALER_ID = -1;
        JPanel cardPanel;
        if (playerId == DEALER_ID) {
            cardPanel = (JPanel) dealer.getComponent(1);
        } else {
            JPanel playerPanel = playerPanels.get(playerId);
            cardPanel = (JPanel) playerPanel.getComponent(1);
        }

        cardPanel.removeAll();

        int x = 0;
        int dx = 20;

        for (Card card : hand.getCards()) {
            ImageIcon cardImage = card.getIcon();
            JLabel cardLabel = new JLabel(cardImage);

            cardLabel.setBounds(x, 0, cardImage.getIconWidth(), cardImage.getIconHeight());
            x += dx;

            cardPanel.add(cardLabel);

            cardPanel.setComponentZOrder(cardLabel, 0);
        }

        Dimension newDims = new Dimension(80 + x, 120);
        cardPanel.setPreferredSize(newDims);

        refresh();
    }

    public void updateTotal(int playerId, Object total) {
        JLabel totalLabel;
        if (playerId == -1) {
            totalLabel = (JLabel) dealer.getComponent(0);
        } else {
            JPanel playerPanel = playerPanels.get(playerId);
            totalLabel = (JLabel) playerPanel.getComponent(0);
        }

        totalLabel.setText(String.valueOf(total));
        refresh();
    }

    // Button methods
    public enum Button { HIT, STAND, SPLIT, DOUBLE_DOWN }
}
