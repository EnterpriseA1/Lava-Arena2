package game_project;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    private GameUI gameUI;
    private JLabel titleLabel;
    private JButton startGameButton;
    private JButton exitButton;

    public MainMenu(GameUI gameUI) {
        this.gameUI = gameUI;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        
        // Create center panel for menu items
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 30, 0);

        // Create and style the title label
        titleLabel = new JLabel("LAVA ARENA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(178, 34, 34)); // Dark red color
        centerPanel.add(titleLabel, gbc);

        // Create and style the start game button
        startGameButton = createStyledButton("Start Game");
        startGameButton.addActionListener(e -> gameUI.showGamePanel());
        
        // Create and style the exit button
        exitButton = createStyledButton("Exit Game");
        exitButton.addActionListener(e -> System.exit(0));

        // Add some vertical spacing between buttons
        gbc.insets = new Insets(10, 0, 10, 0);
        centerPanel.add(startGameButton, gbc);
        centerPanel.add(exitButton, gbc);

        // Add the center panel to the main panel
        add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(70, 130, 180)); // Steel blue color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Lighter blue when hovering
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }
}