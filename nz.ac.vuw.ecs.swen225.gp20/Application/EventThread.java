package Application;

import Maze.Game;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class EventThread extends Thread {

    private final Game game;
    private final ChapsChallenge chapsChallenge;
    private int timeRemaining;

    public EventThread(Game game, ChapsChallenge chapsChallenge, int timeRemaining) {
        this.game = game;
        this.chapsChallenge = chapsChallenge;
        this.timeRemaining = timeRemaining;
    }

    @Override
    public void run() {
        while (timeRemaining > 0){
            try {
                Thread.sleep(1000); //1 second

                chapsChallenge.getGameplayPanel().revalidate();
                chapsChallenge.getInfoPanel().revalidate();
                chapsChallenge.repaint();
                System.out.println("Time rem " + timeRemaining);
                timeRemaining--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameOver();
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     *
     */
    public void gameOver(){
        int playerOption = JOptionPane.showConfirmDialog(null, "Level 1 Completed!", "Proceed to next level?",
                JOptionPane.YES_NO_OPTION);

        if (playerOption == 0){
            System.out.println("Proceeding to level 2...");
        }
        else {
            System.exit(0);
        }
    }
}
