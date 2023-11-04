package Animations;

import GUI.Drawable;

import VisualPlayers.SoundPlayer;

public class AnimatorSound implements Animator {

    private static final int _id = 3;

    protected AnimatorDriver manager;
    private SoundPlayer sound;

    public AnimatorSound(AnimatorDriver manager, SoundPlayer sound) {
        this.manager = manager;
        this.sound = sound;
    }

    @Override public Drawable getDrawable() { return null; }
    @Override public void startAnimation() {
        sound.play();
        manager.notifyEndAnimation(this);
    }
    @Override public final int id() { return _id; }
    public String toString()  { return "Sound(" + sound.toString() + ")"; }
}
