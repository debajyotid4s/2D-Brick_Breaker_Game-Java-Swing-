package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        launchMainMenu();  // Launch the main menu on start
    }

    public static void launchMainMenu() {
        JFrame frame = new JFrame("Brick Breaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        setApplicationLogo(frame);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel titleLabel = new JLabel("Brick Breaker", JLabel.CENTER);
        titleLabel.setFont(new Font("monospace", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        menuPanel.add(titleLabel);

        JButton soloButton = new JButton("Play Solo");
        soloButton.setFont(new Font("monospace", Font.BOLD, 30));
        soloButton.setBackground(Color.WHITE);
        soloButton.addActionListener(e -> {
            frame.dispose();
            launchSoloMode();
        });
        menuPanel.add(soloButton);

        JButton competitiveButton = new JButton("Play Competitive");
        competitiveButton.setFont(new Font("monospace", Font.BOLD, 30));
        competitiveButton.setBackground(Color.WHITE);
        competitiveButton.addActionListener(e -> {
            frame.dispose();
            launchCompetitiveMode();
        });
        menuPanel.add(competitiveButton);

        frame.add(menuPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.playMusic("path_here");
    }

    private static void launchSoloMode() {
        JFrame gameFrame = new JFrame("Solo Mode");
        Gameplay soloGameplay = new Gameplay(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 800);
        setApplicationLogo(gameFrame);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.add(soloGameplay);
        gameFrame.setVisible(true);
    }

    private static void launchCompetitiveMode() {
        JFrame gameFrame = new JFrame("Competitive Mode");
        Gameplay competitiveGameplay = new Gameplay(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(800, 800);
        setApplicationLogo(gameFrame);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.add(competitiveGameplay);
        gameFrame.setVisible(true);
    }

    private static void setApplicationLogo(JFrame frame) {
        ImageIcon icon = new ImageIcon("path_here");
        frame.setIconImage(icon.getImage());
    }
}
