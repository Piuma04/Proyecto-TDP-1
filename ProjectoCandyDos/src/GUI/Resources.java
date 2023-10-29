package GUI;

public class Resources {

    private static String theme = "PlayCrush";

    private Resources() {};

    public static void setTheme(boolean old) { theme = old ? "PlayCrush" : "CandyCrush"; }

    public static String getImagesFolderPath() { return "src/resources/" + theme + "/images/"; }
    public static String getAudioFolderPath()  { return "src/resources/" + theme + "/music/"; }
    public static String getLevelsFolderPath() { return "src/resources/" + theme + "/levels/"; }
}
