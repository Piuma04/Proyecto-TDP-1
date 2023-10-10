package Logic;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.HashSet;

import java.util.Random;

import Animations.SoundPlayer;
import GUI.Gui;

import Entities.Candy;
import Entities.Colour;
import Entities.Entity;

import Interfaces.Equivalent;
import Interfaces.VisualEntity;


public class Board {
    private static final int ROWS = 6;
    private static final int COLUMNS = 6;
    private int playerRow, playerColumn;
    private Block[][] matrix;
    private Gui myGui;
    private Combination combinations;
    
    private static SoundPlayer explosion = new SoundPlayer("ps/move2.wav"); // new SoundPlayer("nam.wav");
    private static SoundPlayer blockMove = new SoundPlayer("ps/move020.wav");
    private static SoundPlayer entityMove = new SoundPlayer("ps/click.wav");

    public Board(Gui gui) 
    {
        matrix = new Block[ROWS][COLUMNS];
        playerRow = ROWS/2;
        playerColumn = COLUMNS/2;
        myGui = gui;
        combinations = new Combination(this);
        for (int row = 0; row < ROWS; row++)
            for (int column = 0; column < COLUMNS; column++) 
            {
                Block block =  new Block(row, column);
                matrix[row][column] = block;
                addVisualEntity(block);
            }
    }
    
    /**
     * returns the amount of {@code rows} the board has.
     * @return {@code rows}
     */
    public static int getRows() { return ROWS; }

    /**
     * returns the amount of {@code columns} the board has.
     * @return {@code columns}
     */
    public static int getColumns()  { return COLUMNS; }

    /*
     * private Set<Integer> columnsToFill() { Set<Integer> s = new
     * HashSet<Integer>();
     * 
     * for (int j = COLUMNS - 1; j >= 0 && s.size() < COLUMNS; j--) { for (int i =
     * ROWS - 1; i >= 0 && s.size() < COLUMNS && !s.contains(j); i--) { if
     * (matrix[i][j].isEmpty()) { s.add(j); } } } }
     */
    /**
     * Moves the player towards a specific direction.
     * @param direction the direction the player will move towards to
     */
    public void movePlayerDirection(int direction) 
    {
        
        switch (direction) 
        {
            case Game.DOWN: 
            {
                movePlayerPosition(playerRow + 1, playerColumn);
                break;
            }
            case Game.UP: 
            {
                movePlayerPosition(playerRow - 1, playerColumn);
                break;
            }
            case Game.LEFT: 
            {
                movePlayerPosition(playerRow, playerColumn - 1);
                break;
            }
            case Game.RIGHT: 
            {
                movePlayerPosition(playerRow, playerColumn + 1);
                break;
            }
        }
    }

    /**
     * Swaps the element the player is on with one that is relative to a specific direction
     * @param direction in which the element will be swapped
     * @return a list of the elements destroyed
     */
    public List<Equivalent> swap(int direction) 
    {
        List<Equivalent> destroyed = new LinkedList<Equivalent>();
        switch (direction) 
        {
        case Game.DOWN: {
            destroyed = swapEntities(playerRow + 1, playerColumn);
            break;
            }
        case Game.UP: {
            destroyed = swapEntities(playerRow - 1, playerColumn);
            break;
            }
        case Game.LEFT: {
            destroyed = swapEntities(playerRow, playerColumn - 1);
            break;
            }
        case Game.RIGHT: {
            destroyed = swapEntities(playerRow, playerColumn + 1);
            break;
            }
        }
        return destroyed;
    }

    /**
     * returns a specific block from the board
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @return Block from the board,in the row and column specified
     */
    public Block getBlock(int row, int column) 
    { 
        return matrix[row][column];
    }
    
    /**
     * Sets the player's position
     * @param newRow valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param newColumn valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     */
    public void setPlayerPosition(int newRow, int newColumn) 
    {
        matrix[newRow][newColumn].focus();
    }
    
    public Candy createRandomCandy(int row, int column) {
        Candy entity = new Candy(row, column, randomColour());
        addVisualEntity(entity);
        return entity;
    }
    
    public void associateEntity(int row, int column, Entity entity) {
        getBlock(row, column).setEntity(entity);
        addVisualEntity(entity);
    }
    
    /**
     * Sets a new entity in the block specified with row and column
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @param entity to be set on the block
     */
    public void setEntity(int row, int column, Entity entity) 
    {
        getBlock(row, column).setEntity(entity);
        entity.changePosition(row, column);
    }

    public void addVisualEntity(VisualEntity entity) {
        entity.setGraphicalEntity(myGui.addLogicEntity(entity));
    }
    /**
     * Destroys the entity inside the block specified with {@code row} and {@code column}
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     */
    public Entity destroyEntity(int row, int column) { return getBlock(row, column).destroyEntity(); }
    
    /**
     * Swaps the entity the player is on with the one specified with {@code row} and {@code column}.
     * @param newRow valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param newColumn valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @return Elements destroyed by potential combinations
     */
    private List<Equivalent> swapEntities(int newRow, int newColumn) 
    {
        Entity e1, e2;
        Set<Integer> columnsToCheck;
        Set<Block> remaining = new HashSet<Block>();
        
        List<Entity> powerCandys = new LinkedList<Entity>();
        Entity powerCandy = null;
        
        List<Equivalent> destroyed = new LinkedList<Equivalent>();
        boolean canExchange = false;

        if (isValidBlock(newRow, newColumn)) 
        {
            entityMove.play();
            Block b1 = matrix[playerRow][playerColumn];
            Block b2 = matrix[newRow][newColumn];
            e1 = b1.getEntity();
            e2 = b2.getEntity();
            canExchange = e1.isSwappable(e2);
            if (canExchange) 
            {
                b1.swapEntity(b2);
                powerCandy = combinations.checkCombinations(playerRow, playerColumn, remaining);
                if (powerCandy != null) powerCandys.add(powerCandy);
                if(!remaining.contains(matrix[newRow][newColumn]))      //Checks whether the second entity's combination is already checked
                {
                    powerCandy = combinations.checkCombinations(newRow, newColumn, remaining);
                    if (powerCandy != null) powerCandys.add(powerCandy); 
                }
                if (!remaining.isEmpty()) 
                {
                    while (!remaining.isEmpty()) //While there are remaining combinations, destroy them,fill the board, and check again
                    {
                        myGui.playSound(explosion); 
                        destroyed.addAll(destroyEntities(remaining));
                        for (Entity entity : powerCandys)
                            associateEntity(entity.getRow(), entity.getColumn(), entity);
                        powerCandys.clear();
                        columnsToCheck = fillBoard();
                        powerCandys.addAll(combinations.checkRemainingCombinations(columnsToCheck, remaining));
                    }
                } else b1.swapEntity(b2);
            }   
        }
        //System.out.println(destroyed.toString());
        return destroyed;
    }
    /**
     * Destroys the entities inside the blocks specified.
     * @param remaining list of blocks set to be destroyed
     * @return entities destroyed
     */
    private List<Equivalent> destroyEntities(Set<Block> remaining)
    {
        List<Equivalent> destroyed = new LinkedList<Equivalent>();
        List<Block> destroyables = new LinkedList<Block>();

        for (Block b : remaining) 
        {
            destroyables.addAll(b.getEntity().getDestroyables(this));
        }
        for (Block b : destroyables) 
        {
            if(b.hasModifiers())
                destroyed.add(b.popModifier());
            destroyed.add(b.getEntity());
            destroyEntity(b.getRow(), b.getColumn());
             
        }
        remaining.clear();
        return destroyed;
    }
    /**
     * Fills the board pulling down the elements and putting random elements on the empty blocks
     * @return {@code columns} that were filled
     */
    private Set<Integer> fillBoard() 
    {
        Map<Integer, List<Block>> emptyColumns = new HashMap<Integer, List<Block>>();
        List<Entity> candys = new LinkedList<Entity>();
        Set<Integer> emptyColumnsIndexes = new HashSet<Integer>();

        for (int column = 0; column < COLUMNS; column++) {
            emptyColumns.put(column, new LinkedList<Block>());

            for (int row = ROWS-1; row >= 0; row--) {
                Block block = getBlock(row, column);
                if (block.isEmpty())
                    emptyColumns.get(column).add(block);
            }

            int amountEmptyBlocks =  emptyColumns.get(column).size();
            for (int i = 0; i < amountEmptyBlocks; i++) {
                candys.add(createRandomCandy(i - amountEmptyBlocks, column));
            }
        }

        
        for (int col = 0; col < COLUMNS; col++) {
            List<Block> emptyBlocks = emptyColumns.get(col);
            int amountExtraCandys = emptyBlocks.size();

            if (amountExtraCandys <= 0) continue;
            emptyColumnsIndexes.add(col);
            Block lower = emptyBlocks.get(0);
            
            Queue<Entity> q = new ArrayDeque<Entity>();
            for (int candyIdx = 0; candyIdx < amountExtraCandys; candyIdx++)
                q.add(candys.remove(0));

            Block current = null;
            for (int i = 0; i <= lower.getRow(); i++)
            {
                current = getBlock(i, col);
                if (!current.isEmpty())
                    q.add(current.getEntity());
                setEntity(i, col, q.poll());
            }
        }

        return emptyColumnsIndexes;
    }
    /**
     * 
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @return {@code true} if it is a valid position inside the board.
     */
    public static boolean isValidBlock(int row, int column) {
        return row >= 0 && row < ROWS && column >= 0 && column < COLUMNS;
    }
    /**
     * Moves player's position to a new {@code row} and new {@code column} 
     * @param newRow valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param newColumn valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     */
    private void movePlayerPosition(int newRow, int newColumn) {
        if ((0 <= newRow) && (newRow < ROWS) && (0 <= newColumn) && (newColumn < COLUMNS)) {
            blockMove.playNew();
            if (matrix[newRow][newColumn].focus()) {
                matrix[playerRow][playerColumn].defocus();
                playerRow = newRow;
                playerColumn = newColumn;
            }
        }
    }
    /**
     * returns a random {@link Colour}, posibilities are RED,BLUE,PURPLE,YELLOW,GREEN
     * @return random colour
     */
    private Colour randomColour() {
        Colour[] colores = { Colour.BLUE, Colour.GREEN, Colour.PURPLE, Colour.RED, Colour.YELLOW };
        Random r = new Random();
        return colores[Math.abs(r.nextInt()) % 5];
    }
}