package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        // Calculate brick dimensions
        brickWidth = (800 - 160) / col;
        brickHeight = 150 / row;
    }

    public void draw(Graphics2D g) {

        int totalBrickWidth = map[0].length * brickWidth;
        int totalBrickHeight = map.length * brickHeight;


        int startX = (800 - totalBrickWidth) / 2;
        int startY = (800 - totalBrickHeight) / 2;

        // Draw each brick in the grid
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    int brickX = startX + (j * brickWidth);
                    int brickY = startY + (i * brickHeight);

                    g.setColor(Color.WHITE);
                    g.fillRect(brickX, brickY, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(brickX, brickY, brickWidth, brickHeight);
                }
            }
        }
    }
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value; // Set brick to invisible (0)
    }
}
