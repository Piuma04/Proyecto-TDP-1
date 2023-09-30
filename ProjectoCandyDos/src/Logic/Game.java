package Logic;
import GUI.GUI;

public class Game {
    private Board myBoard;
    private Level myLevel;
    private LevelGenerator myLevelGenerator;
    private GUI myGui;
    private int lives;
    
    public void update() {
        
    }

    public void loadLevel(int level) {
        
    }
    
    public void swap(int direction){
        myBoard.swap(direction);
    }
    
    public void move(int direction) {
        myBoard.movePlayerDirection(direction);
    }
}
