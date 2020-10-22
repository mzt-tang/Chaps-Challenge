package Application;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Start menu of the game.
 *
 * @author Iqbal
 */
public class StartMenu extends JFrame {

    //window dimensions
    private final int WINDOW_WIDTH = 950;
    private final int WINDOW_HEIGHT = 750;

    /**
     * Constructs the start menu
     */
    public StartMenu(){
        initUI();
        setVisible(true);
    }


    /**
     * Initializes window properties and sets the clickable areas
     */
    public void initUI(){
        setTitle("Chap's Challenge: Among Us Edition");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        //Load and add the image to the JFrame
        try {
            BufferedImage image = ImageIO.read(new File("Resources/StartMenu.jpeg"));
            JLabel menuScreen = new JLabel(new ImageIcon(image));
            this.add(menuScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                //start
                if (x > 265 && x < 413 && y > 470 && y < 577){
                    //close the start menu and launch the game
                    dispose();
                    EventQueue.invokeLater(ChapsChallenge::new);
                }
                //help
                if (x > 543 && x < 690 && y > 467 && y < 577){
                    System.out.println("HELP CLICKED");
                }
            }
        });

        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
