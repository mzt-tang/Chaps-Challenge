package Renderer;

import java.awt.*;

/**
 * This is responsible for displaying the Maze on the screen
 * including all it's tiles, animations and sound effects
 * @author Chris
 */
public class Renderer extends Canvas {
    public Renderer(){

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g; //Graphics 2D gives you more drawing options

        //Set background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
