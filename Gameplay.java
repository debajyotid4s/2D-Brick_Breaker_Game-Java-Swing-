package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean isSolo;
    private boolean play = false;
    private Timer timer;
    private final int delay = 1;

    
    private boolean gameStarted = false;
    private boolean isPaused = false;

    // Bricks
    private int[][] bricks;
    private int brickWidth, brickHeight;

    // Paddles
    private int player1X = 350; // Bottom paddle (Arrow keys)
    private int player2X = 350; // Top paddle (A and D keys)

    // Balls
    private int ball1X, ball1Y, ball1XDir = -1, ball1YDir = -2;
    private int ball2X, ball2Y, ball2XDir = 1, ball2YDir = 2;

    // Scores
    private int player1Score = 0, player2Score = 0;

    // Key press states
    private boolean leftKeyPressed = false, rightKeyPressed = false;
    private boolean aKeyPressed = false, dKeyPressed = false;
    
    private TimeOnGame timeOnGame;
    private JLabel timerLabel;

    public Gameplay(boolean isSolo) {
        this.isSolo = isSolo;
        initGame();

        // Set up the timer label
        timerLabel = new JLabel("Time: 00:00");
        add(timerLabel, BorderLayout.NORTH);

        timeOnGame = new TimeOnGame(timerLabel);
        timeOnGame.startTimer();

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        addKeyListener(this);
    }

    private void initGame() {
        int rows = isSolo ? 4 : 6;
        int cols = isSolo ? 8 : 10;
        bricks = new int[rows][cols];
        brickWidth = 700 / cols;
        brickHeight = 150 / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bricks[i][j] = 1;
            }
        }

        player1X = 350;

        ball1X = player1X + 40;
        ball1Y = 580;
        ball1XDir = -1;
        ball1YDir = -1;

        if (!isSolo) {
        	ball2X = player2X + 40;
            ball2Y = 38;
            ball2XDir = 1;
            ball2YDir = 1;
        
        }

        player1Score = 0;
        player2Score = 0;

        play = true;
        gameStarted = false;
    }





    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (!gameStarted) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("monospace", Font.BOLD, 30));
            g.drawString("Press any key to start", 230, 350);
            return;
        }

        // Draw the game elements
        drawBricks(g);

        // Display scores
        g.setColor(Color.PINK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (isSolo) {
            g.drawString("Score: " + player1Score, 20, 30);
        } else {
            g.drawString("Player 1 Score: " + player1Score, 20, getHeight() - 20);
            g.drawString("Player 2 Score: " + player2Score, 20, 30);
        }

        // Draw paddles
        g.setColor(Color.WHITE);
        g.fillRect(player1X, getHeight() - 50, 100, 8); // Player 1 Paddle
        if (!isSolo) g.fillRect(player2X, 30, 100, 8); // Player 2 Paddle

        // Draw balls
        g.setColor(Color.RED);
        g.fillOval(ball1X, ball1Y, 20, 20); // Ball 1
        if (!isSolo) {
            g.setColor(Color.BLUE);
            g.fillOval(ball2X, ball2Y, 20, 20); // Ball 2
        }

        // If Pauses
        if (isPaused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Paused", getWidth() / 2 - 120, getHeight() / 2 - 20);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press ESC to Resume", getWidth() / 2 - 100, getHeight() / 2 + 20);
            return;
        }

        // Show Game Over
        if (!play) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            if (isSolo) {
                g.drawString("Game Over!", 300, 300);
                g.setFont(new Font("monospace", Font.PLAIN, 20));
                g.drawString("Score: " + player1Score, 300, 350);
            } else {
                if (ball1Y > getHeight()) {
                    g.drawString("Player 2 Wins!", 250, 300);
                } else if (ball2Y < 0) {
                    g.drawString("Player 1 Wins!", 250, 300);
                }
            }
            g.setFont(new Font("monospace", Font.PLAIN, 20));
            g.drawString("Press Enter to Restart", 300, 400);
        }
    }







    private void drawBricks(Graphics g) {
        int totalBrickWidth = bricks[0].length * brickWidth;
        int totalBrickHeight = bricks.length * brickHeight;

        int startX = (800 - totalBrickWidth) / 2;
        int startY = isSolo ? 50 : (800 - totalBrickHeight) / 2;

        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(startX + j * brickWidth, startY + i * brickHeight, brickWidth, brickHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(startX + j * brickWidth, startY + i * brickHeight, brickWidth, brickHeight);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play && !isPaused) {

            ball1X += ball1XDir;
            ball1Y += ball1YDir;


            if (ball1X < 0 || ball1X > getWidth() - 20) ball1XDir = -ball1XDir;
            if (ball1Y < 0) ball1YDir = -ball1YDir;
            if (ball1Y > getHeight()) {
                showGameOver("Player 2 Wins!");
                return;
            }


            ball1YDir = checkCollision(ball1X, ball1Y, ball1XDir, ball1YDir, 1);


            if (!isSolo) {
                ball2X += ball2XDir;
                ball2Y += ball2YDir;


                if (ball2X < 0 || ball2X > getWidth() - 20) ball2XDir = -ball2XDir;
                if (ball2Y < 0) {
                    showGameOver("Player 1 Wins!");
                    return;
                }


                ball2YDir = checkCollision(ball2X, ball2Y, ball2XDir, ball2YDir, 2);
            }
        }
        repaint();
    }

    private int checkCollision(int ballX, int ballY, int ballXDir, int ballYDir, int ballId) {
        Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);


        if (ballId == 1) { // Player 1's ball
            Rectangle paddle1Rect = new Rectangle(player1X, getHeight() - 50, 100, 8); // Player 1's paddle
            if (ballRect.intersects(paddle1Rect)) {
                ballYDir = -Math.abs(ballYDir);
            }
        } else if (ballId == 2) {
            Rectangle paddle2Rect = new Rectangle(player2X, 30, 100, 8); // Player 2's paddle
            if (ballRect.intersects(paddle2Rect)) {
                ballYDir = Math.abs(ballYDir);
            }
        }


        int totalBrickWidth = bricks[0].length * brickWidth;
        int totalBrickHeight = bricks.length * brickHeight;
        int startX = (800 - totalBrickWidth) / 2;
        int startY = isSolo ? 50 : (800 - totalBrickHeight) / 2;

        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    int brickX = startX + j * brickWidth;
                    int brickY = startY + i * brickHeight;
                    Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                    if (ballRect.intersects(brickRect)) {
                        bricks[i][j] = 0;
                        if (ballId == 1) player1Score += 5;
                        if (ballId == 2) player2Score += 5;


                        if (ballX + 19 <= brickX || ballX + 1 >= brickX + brickWidth) {
                            ballXDir = -ballXDir;
                        } else {
                            ballYDir = -ballYDir;
                        }

                        repaint();
                        return ballYDir;
                    }
                }
            }
        }
        return ballYDir;
    }




    private void showGameOver(String winnerMessage) {
        play = false;
        timeOnGame.stopTimer();
        String elapsedTime = timeOnGame.getElapsedTime();

        JOptionPane.showMessageDialog(this,
            winnerMessage + "\n" +
            "Player 1 Score: " + player1Score + "\n" +
            "Player 2 Score: " + player2Score + "\n" +
            "Game Time: " + elapsedTime,
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE);

        timeOnGame.resetTimer();
        repaint();
    }




    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameStarted) {
            gameStarted = true;
            timeOnGame.startTimer();
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !play) {
            initGame();
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            isPaused = !isPaused;
            if (!isPaused) {
                timer.start();
            } else {
                timer.stop();
            }
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftKeyPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightKeyPressed = true;

        if (e.getKeyCode() == KeyEvent.VK_A) aKeyPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_D) dKeyPressed = true;

        if (leftKeyPressed && player1X > 0) player1X -= 20;
        if (rightKeyPressed && player1X < getWidth() - 100) player1X += 20;

        if (aKeyPressed && player2X > 0) player2X -= 20;
        if (dKeyPressed && player2X < getWidth() - 100) player2X += 20;

        repaint();
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftKeyPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightKeyPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_A) aKeyPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_D) dKeyPressed = false;
    }


    @Override
    public void keyTyped(KeyEvent e) {}
}
