package Animations;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundPlayer {

    private AudioInputStream audioStream;
    private Clip mySound;
    private boolean stopped;

    public SoundPlayer(String filename) {
        mySound = null;
        stopped = true;
        try {
            audioStream = AudioSystem.getAudioInputStream(new File("src/music/" + filename));
            mySound = AudioSystem.getClip();
            mySound.open(audioStream);
            //mySound.start();
            //mySound.addLineListener(event -> { if(event.getType() == LineEvent.Type.STOP) event.getLine().close(); });
            mySound.addLineListener(event -> {
                System.out.println(event.getType());
                
                if(event.getType() == LineEvent.Type.STOP) {
                    stopped = true;
                }
                if(event.getType() == LineEvent.Type.START) {
                    
                    System.out.println("Started");
                }
                
            });
        } catch( IOException | UnsupportedAudioFileException | LineUnavailableException e) {System.out.println(e.getMessage());}
    }

    public void play() {
        stopped = false;
        if (mySound.isActive()) {
            mySound.stop();
            while (!stopped)
                try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        mySound.setFramePosition(0);
        mySound.start();
    }
}
