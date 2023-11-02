package VisualPlayers;

import java.util.LinkedList;
import java.util.List;

public class Resources {

    
    private static int theme = 0;
    private static String[] themes = { "PlayCrush", "CandyCrush" };
    private static List<Observer> observers = new LinkedList<Observer>();

    private Resources() {};

    public static void setTheme(int id) {
        theme = id > themes.length - 1 ? theme : id;
        notifyObservers();
    }

    public static int getTheme() { return theme; }

    public static String getImagesFolderPath() { return "src/resources/" + themes[theme] + "/images/"; }
    public static String getAudioFolderPath()  { return "src/resources/" + themes[theme] + "/music/"; }
    public static String getLevelsFolderPath() { return "src/resources/levels/"; }
    public static String getScorePath() { return "src/resources/levels/scores.txt"; }

    // START SUBJECT METHODS.
    public static void registerObserver(Observer observer) { observers.add(observer); }
    public static void removeObserver(Observer observer) { observers.remove(observer); }

    public static void notifyObservers() {
        for (Observer observer : observers)
            observer.update(theme);
    }
    // END SUBJECT METHODS.
}
