package GUI;

public class Resources {

    private static String[] themes = { "PlayCrush", "CandyCrush" };
    private static int theme = 0;

    private Resources() {};

    public static void setTheme(int id) { theme = id > themes.length - 1 ? theme : id; }
    public static int getTheme() { return theme; }

    public static String getImagesFolderPath() { return "src/resources/" + themes[theme] + "/images/"; }
    public static String getAudioFolderPath()  { return "src/resources/" + themes[theme] + "/music/"; }
    public static String getLevelsFolderPath() { return "src/resources/levels/"; }
    public static String getScorePath() { return "src/resources/levels/scores.txt"; }
}
