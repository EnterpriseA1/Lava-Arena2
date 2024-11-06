package game_project;

import javax.swing.*;
import java.awt.*;


public class GameOverScreen extends JPanel {
    private JLabel winnerLabel;
    private JButton mainMenuButton;
    private GameUI gameUI;

    public GameOverScreen(GameUI gameUI) {
        this.gameUI = gameUI;
        setOpaque(true); // Ensure panel is opaque
        setBackground(new Color(240, 240, 240));
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Create center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(true);
        centerPanel.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 30, 0);

        // Create and style the winner label
        winnerLabel = new JLabel("Game Over!");
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        centerPanel.add(winnerLabel, gbc);

        // Create and style the main menu button
        mainMenuButton = new JButton("Back to Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 18));
        mainMenuButton.setPreferredSize(new Dimension(200, 50));
        mainMenuButton.setBackground(new Color(70, 130, 180));
        mainMenuButton.setForeground(Color.WHITE);
        mainMenuButton.setFocusPainted(false);
        mainMenuButton.setBorderPainted(false);
        mainMenuButton.setOpaque(true);

        // Add hover effect
        mainMenuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mainMenuButton.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                mainMenuButton.setBackground(new Color(70, 130, 180));
            }
        });

        // Add action listener
        mainMenuButton.addActionListener(e -> {
            System.out.println("Returning to main menu");
            gameUI.showMainMenu();
        });

        gbc.insets = new Insets(20, 0, 0, 0);
        centerPanel.add(mainMenuButton, gbc);

        // Add the center panel
        add(centerPanel, BorderLayout.CENTER);
        
        // Ensure everything is visible
        setVisible(true);
    }

    public void showWinner(String winner) {
        if ("Player".equals(winner)) {
            winnerLabel.setForeground(new Color(50, 205, 50));
            winnerLabel.setText("Player Wins!");
        } else {
            winnerLabel.setForeground(new Color(178, 34, 34));
            winnerLabel.setText("Enemy Wins!");
        }
        System.out.println("Winner label updated: " + winner);
    }
}