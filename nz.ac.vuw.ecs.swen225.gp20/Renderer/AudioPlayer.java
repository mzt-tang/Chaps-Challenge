package Renderer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Deals with sound aspect of the game
 * @author Chris
 */
public class AudioPlayer {
    private final Map<String, Clip> sounds;

    /**
     * Creates an audio player, loads audio files from
     * the directory /Resources/audio
     */
    public AudioPlayer() {
        sounds = new HashMap<>();

        //Iterates through the audio directory and loads all the clips
        File[] files = new File(System.getProperty("user.dir") + "/Resources/audio").listFiles();
        for (File file : files){
            try {
                //Creates the audio clip and stores it in the map to be played later
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                String name = file.getName().substring(0,file.getName().length()-4); //removes .wav extension
                sounds.put(name, clip);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Play a given sound from the game
     * @param name The sound file without the file extension eg "DoorOpen"
     */
    public void playSound(String name){
        Clip clip = sounds.get(name);
        clip.setFramePosition(0);
        clip.start();
    }
}
