package Logic;

import GUI.GUI;

public class Game {
	
	public static final int UP = 15000;
	public static final int DOWN = 15001;
	public static final int LEFT = 15002;
	public static final int RIGHT = 15003;
	
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
