package Renderer;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This is responsible for displaying the Maze on the screen
 * including all it's tiles, animations and sound effects
 * @author Chris
 */
public class Renderer extends Canvas {
    private Map<String, Image> images;

    public Renderer(){
        images = new HashMap<>();

        //Really compact way of loading all the images into memory
        File[] files = new File(System.getProperty("user.dir") + "/images").listFiles();
        for (File image : files){
            images.put(image.getName(), Toolkit.getDefaultToolkit().getImage(image.getPath()));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g; //Graphics 2D gives you more drawing options

        //Set background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    public static void main(String[] args) {
        new Renderer(); //Just for testing (and so I don't interfere with other modules
    }
}
