package Application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Start menu of the game.
 *
 * @author Iqbal
 */
public class StartMenu extends JFrame {

    //window dimensions
    private final int WINDOW_WIDTH = 950;
    private final int WINDOW_HEIGHT = 750;

    public StartMenu(){
        initUI();

        setVisible(true);
    }

    /**
     * Initializes window properties
     */
    public void initUI(){
        setTitle("Chap's Challenge: Among Us Edition");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JButton startButton = new JButton("Start");
        startButton.setHorizontalAlignment(SwingConstants.CENTER);
        startButton.addActionListener(e -> {
            //close the start menu and launch the game
            dispose();
            EventQueue.invokeLater(ChapsChallenge::new);
        });


        mainPanel.add(startButton, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }



}
