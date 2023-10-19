package Animations;

import GUI.Drawable;

public class AnimatorSound implements Animator {

    protected AnimatorDriver manager;
    private SoundPlayer mySound;

    public AnimatorSound(AnimatorDriver m, SoundPlayer sound) {
        manager = m;
        mySound = sound;
    }

    @Override public Drawable getDrawable() { return null; }
    @Override public void startAnimation() {
        mySound.play();
        manager.notifyEndAnimation(this, false);
    }
    @Override public int id() { return 3; }
    public String toString()  { return "Sound"; }
}
