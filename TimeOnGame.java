package brickBreaker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeOnGame {
    private int timeInSeconds;
    private JLabel timerLabel;
    private Timer timer;

    public TimeOnGame(JLabel timerLabel) {
        this.timerLabel = timerLabel;
        this.timeInSeconds = 0;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeInSeconds++;
                updateTime();
            }
        });
    }

    // Start the timer
    public void startTimer() {
        timer.start();
    }

    // Stop the timer
    public void stopTimer() {
        timer.stop();
    }

    // Reset the timer
    public void resetTimer() {
        timer.stop();
        timeInSeconds = 0;
        updateTime();
    }

    // Update the timer label
    public void updateTime() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    public String getElapsedTime() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
