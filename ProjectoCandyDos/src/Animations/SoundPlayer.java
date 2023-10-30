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

import GUI.Resources;


public class SoundPlayer {
    private boolean stopped;
    private Clip mySound;
    private AudioFormat audioFormat;
    private int audioBytes;
    private byte[] audioData;

    private String filename;
    private int theme;
    

    public SoundPlayer(String filename) {
        this.filename = filename;
        theme = -1;
        mySound = null;
        stopped = true;
        audioFormat = null;
        audioBytes = 0;
        checkUpdatedResources();
    }

    public void play() {
        checkUpdatedResources();
        stopped = false;
        if (mySound != null) {
            if (mySound.isActive()) {
                mySound.stop();
                mySound.flush();
                while (!stopped)
                    try { Thread.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            mySound.setFramePosition(0);
            mySound.start();
        }
    }

    public void playNew() {
        checkUpdatedResources();
        AudioInputStream audioStream = getAudioStream();
        Clip clip = getClip(audioStream);
        if (clip != null) {
            clip.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    try { audioStream.close(); } catch (IOException e) { e.printStackTrace();}
                }
            });
            clip.start();
        }
    }

    public void loop() { checkUpdatedResources(); if (mySound != null) mySound.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop() { if (mySound != null) { mySound.stop(); mySound.setFramePosition(0); } }
    public void start() { checkUpdatedResources(); if (mySound != null) mySound.start(); }
    public boolean isActive() { return mySound != null && mySound.isActive(); }
    
    private AudioInputStream getAudioStream() {
        return audioData == null ? null : new AudioInputStream(
                new ByteArrayInputStream(audioData),
                audioFormat,
                audioBytes
        );
    }
    
    private Clip getClip(AudioInputStream audioStream) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            if (audioStream != null)
                clip.open(audioStream);
        } catch (LineUnavailableException | IOException e) { e.printStackTrace(); }
        return clip;
    }

    private void checkUpdatedResources() {
        int actualTheme = Resources.getTheme();
        if (actualTheme != theme) {
            theme = actualTheme;
            loadAudioData(filename);
            mySound = getClip(getAudioStream());
            if (mySound != null)
                mySound.addLineListener(event -> {
                    if(event.getType() == LineEvent.Type.STOP) { stopped = true; }
                });
        }
    }

    private void loadAudioData(String filePath) {
        try {
            File audioFile = new File(Resources.getAudioFolderPath() + filePath);
            if (!audioFile.exists() || !audioFile.isFile()) {
                throw new IllegalArgumentException("Invalid audio file path: " + Resources.getAudioFolderPath() + filePath);
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