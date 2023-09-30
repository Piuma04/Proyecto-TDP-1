package Logic;

import Entities.Candy;
import Entities.Colour;
import GUI.GUI;

public class Game {
	private static final int MAX_LIVES = 5;
	public static final int UP = 15000;
	public static final int DOWN = 15001;
	public static final int LEFT = 15002;
	public static final int RIGHT = 15003;
	
    private Board myBoard;
    private Level myLevel;
    //private LevelGenerator myLevelGenerator;
    private GUI myGui;
    private int lives;
    
    public static void main(String[] args) {
        
        Game myGame = new Game();
        myGame.loadLevel(1);
        System.out.println("Hey!");
    }

    
    Game() {
        lives = MAX_LIVES;
        myBoard = new Board();
        myLevel = new Level(new Candy(0,0,Colour.GREEN), 12, 40, 180);
    }

    public void update() {
        
    }

    public void loadLevel(int level) {
        LevelGenerator.generateLevel("src/Levels/level" + String.valueOf(level) + ".txt", myBoard);
    }
    
    public void swap(int direction){
        myBoard.swap(direction);
    }
    
    public void move(int direction) {
        myBoard.movePlayerDirection(direction);
    }
}
