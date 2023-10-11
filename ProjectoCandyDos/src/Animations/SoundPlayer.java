package Animations;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundPlayer {

    private boolean stopped;
    private Clip mySound;
    private AudioFormat audioFormat;
    private int audioBytes;
    private byte[] audioData;

    public SoundPlayer(String filename) {
        mySound = null;
        stopped = true;
        audioFormat = null;
        audioBytes = 0;
        loadAudioData(filename);
        mySound = getClip(getAudioStream());
        mySound.addLineListener(event -> {
            if(event.getType() == LineEvent.Type.STOP) { stopped = true; }
        });
    }

    public void play() {
        stopped = false;
        if (mySound.isActive()) {
            mySound.stop();
            mySound.flush();
            while (!stopped)
                try { Thread.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        mySound.setFramePosition(0);
        //mySound.start();
    }

    public void playNew() {
        AudioInputStream audioStream = getAudioStream();
        Clip clip = getClip(audioStream);
        clip.addLineListener(event -> {
            if(event.getType() == LineEvent.Type.STOP) {
                clip.close();
                try { audioStream.close(); } catch (IOException e) { e.printStackTrace();}
            }
        });
        //clip.start();
    }

    public void loop() {} //{ mySound.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop() { mySound.stop(); }
    public void start() {} // { mySound.start(); }
    public boolean isActive() { return mySound.isActive(); }
    
    private AudioInputStream getAudioStream() {
        return new AudioInputStream(
                new ByteArrayInputStream(audioData),
                audioFormat,
                audioBytes
        );
    }
    
    private Clip getClip(AudioInputStream audioStream) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException | IOException e) { e.printStackTrace(); }
        return clip;
    }
    
    private void loadAudioData(String filePath) {
        try {
            File audioFile = new File("src/music/" + filePath);
            if (!audioFile.exists() || !audioFile.isFile()) {
                throw new IllegalArgumentException("Invalid audio file path: " + filePath);
            }

            try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
                audioFormat = audioStream.getFormat();
                audioBytes = (int) (audioStream.getFrameLength() * audioFormat.getFrameSize());
                audioData = new byte[audioBytes];
                int bytesRead = audioStream.read(audioData, 0, audioBytes);
                if (bytesRead != audioBytes) { throw new IOException("Error reading audio data"); }
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
}