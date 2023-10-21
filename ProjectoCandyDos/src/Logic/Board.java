package Logic;

import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Random;

import Animations.SoundPlayer;
import GUI.Gui;

import Entities.Candy;
import Entities.Colour;
import Entities.Empty;
import Entities.Entity;
import Interfaces.Equivalent;
import Interfaces.VisualEntity;

public class Board {
    private static final int ROWS = 6;
    private static final int COLUMNS = 6;
    private static final SoundPlayer explosion = new SoundPlayer("ps/move2.wav"); // new SoundPlayer("nam.wav");
    private static final SoundPlayer blockMove = new SoundPlayer("ps/move100.wav");
    private static final SoundPlayer entityMove = new SoundPlayer("ps/click.wav");
    private static final Entity dummy = new Candy(0, 0, Colour.NONE);
    private static final Random candyPicker = new Random();
    
    private int playerRow, playerColumn;
    private Block[][] matrix;
    private Gui myGui;
    private Combination combinationLogic;
    private boolean playerSetted;

    public Board(Gui gui) {
        matrix = new Block[ROWS][COLUMNS];
        playerRow = ROWS / 2;
        playerColumn = COLUMNS / 2;
        myGui = gui;
        combinationLogic = new Combination(this);
        playerSetted = false;
        for (int row = 0; row < ROWS; row++)
            for (int column = 0; column < COLUMNS; column++) {
                Block block = new Block(row, column);
                matrix[row][column] = block;
                addVisualEntity(block);
                block.getGraphicalEntity().setSkipQueue(true);
            }
        gui.executeAfterAnimation(() -> { setPlayerPosition(playerRow, playerColumn); });
    }

    public static int getRows() { return ROWS; }

    public static int getColumns() { return COLUMNS; }

    /**
     * @see {@link Game#UP} {@link Game#DOWN} {@link Game#LEFT} {@link Game#RIGHT}
     */
    public void movePlayerDirection(int direction) {
        if (playerSetted)
            switch (direction) {
            case Game.DOWN:
                movePlayerPosition(playerRow + 1, playerColumn);
                break;
            case Game.UP:
                movePlayerPosition(playerRow - 1, playerColumn);
                break;
            case Game.LEFT:
                movePlayerPosition(playerRow, playerColumn - 1);
                break;
            case Game.RIGHT:
                movePlayerPosition(playerRow, playerColumn + 1);
                break;
            }
    }

    /**
     * Swaps the element the player is on with one that is relative to a specific
     * direction
     * 
     * @param direction in which the element will be swapped
     * @return a list of the elements destroyed
     * @see {@link Game#UP} {@link Game#DOWN} {@link Game#LEFT} {@link Game#RIGHT}
     */
    public List<Equivalent> swap(int direction) {
        List<Equivalent> destroyed = new LinkedList<Equivalent>();
        switch (direction) {
        case Game.UP:
            destroyed = swapEntities(playerRow - 1, playerColumn);
            break;
        case Game.DOWN:
            destroyed = swapEntities(playerRow + 1, playerColumn);
            break;
        case Game.LEFT:
            destroyed = swapEntities(playerRow, playerColumn - 1);
            break;
        case Game.RIGHT:
            destroyed = swapEntities(playerRow, playerColumn + 1);
            break;
        }
        return destroyed;
    }

    public Block getBlock(int row, int column) { return matrix[row][column]; }

    public void setPlayerPosition(int newRow, int newColumn) {
        playerSetted = true;
        matrix[newRow][newColumn].focus();
    }

    public Candy createRandomCandy(int row, int column) {
        Candy entity = new Candy(row, column, randomColour());
        addVisualEntity(entity);
        return entity;
    }

    /**
     * Sets the entity of block at (column, row) to new entity. associates to the
     * GUI the entity. mostly used for creation.
     * 
     * @param row
     * @param column
     * @param entity MUST NOT HAVE BEEN ALREADY ADDED TO GUI.
     */
    public void associateEntity(int row, int column, Entity entity) {
        getBlock(row, column).setEntity(entity);
        addVisualEntity(entity);
    }

    /**
     * Sets the entity of block at (column, row) to new entity AND changes the
     * position of the entity.
     * 
     * @param row
     * @param column
     * @param entity
     */
    public void setEntity(int row, int column, Entity entity) {
        getBlock(row, column).setEntity(entity);
        entity.changePosition(row, column);
    }

    /**
     * Given a {@link VisualEntity} adds graphical entity to the GUI.
     * 
     * @param entity
     */
    public void addVisualEntity(VisualEntity entity) { entity.setGraphicalEntity(myGui.addLogicEntity(entity)); }

    public Entity destroyEntity(int row, int column) { return getBlock(row, column).destroyEntity(); }

    private List<Equivalent> swapEntities(int newRow, int newColumn) {
        Set<Block> combinations = new HashSet<Block>();
        List<Entity> powerCandys = new LinkedList<Entity>();
        List<Equivalent> destroyed = new LinkedList<Equivalent>();
        if (isValidBlock(newRow, newColumn)) {
            Block b1 = matrix[playerRow][playerColumn];
            Block b2 = matrix[newRow][newColumn];
            if (canSwap(b1, b2)) {
                entityMove.playNew();
                b1.swapEntity(b2);
                combinations.add(b1);
                combinations.add(b2);
                if (specialSwap(b1, b2)) {
                    combinations.addAll(b1.getEntity().getDestroyables(this));
                    combinations.addAll(b2.getEntity().getDestroyables(this));
                }
                else
                    combinations = combinationLogic.checkCombinations(combinations, powerCandys);
                if (!combinations.isEmpty()) {
                    do // While there are remaining combinations, destroy them,fill the board, and
                       // check again
                    {
                        destroyed.addAll(destroyEntities(combinations));
                        for (Entity entity : powerCandys)
                            associateEntity(entity.getRow(), entity.getColumn(), entity);
                        powerCandys.clear();
                        Map<Integer, List<Block>> emptyBlocks = fillBoard();
                        combinations = combinationLogic.checkRemainingCombinations(emptyBlocks, powerCandys);
                    }
                    while (!combinations.isEmpty());
                }  else b1.swapEntity(b2);
            }
        }
        return destroyed;
    }

    private List<Equivalent> destroyEntities(Set<Block> toDestroy) {
        List<Equivalent> destroyed = new LinkedList<Equivalent>();
        List<Block> destroyables = new LinkedList<Block>();
        for (Block b : toDestroy) { destroyables.addAll(b.getEntity().getDestroyables(this)); }
        if (!destroyables.isEmpty()) { myGui.playSound(explosion); }
        for (Block b : destroyables) {
            if (b.hasModifiers())
                destroyed.add(b.popModifier());
            destroyed.add(b.getEntity());
            destroyEntity(b.getRow(), b.getColumn());
        }
        toDestroy.clear();
        return destroyed;
    }

    private Map<Integer, List<Block>> fillBoard() {
        Map<Integer, List<Block>> emptyColumns = new HashMap<Integer, List<Block>>();
        Map<Integer, Integer> newCandys = new HashMap<Integer, Integer>();
        List<Entity> candys = new LinkedList<Entity>();
        // for every column, add all empty blocks found to map emptyColumns.
        // count amount of candys to create and create them.
        for (int column = 0; column < COLUMNS; column++) {
            List<Block> emptyBlocks = new LinkedList<Block>();
            int extraCandys = 0;
            for (int row = ROWS - 1; row >= 0; row--) {
                Block block = getBlock(row, column);
                if (block.isEmpty()) {
                    emptyBlocks.add(block);
                    extraCandys++;
                }
                else if (!hasMovableEntity(block))
                    extraCandys = 0;
            }
            newCandys.put(column, extraCandys);
            for (int i = 1; i <= extraCandys; i++)
                candys.add(createRandomCandy(-i, column));
            emptyColumns.put(column, emptyBlocks);
        }
        /* *
         * for every column which has empty blocks,
         * get lower empty block and try to fill it obtaining the entity above.
         * for all non movable blocks, stop.
         * if above all non movables, get candys above. 
         * */
        for (int col = 0; col < COLUMNS; col++) {
            List<Block> emptyBlocks = emptyColumns.get(col);
            int amountExtraCandys = newCandys.get(col);
            if (!emptyBlocks.isEmpty()) {
                Block lower = emptyBlocks.get(0);
                for (int i = lower.getRow(); i >= amountExtraCandys; i--) {
                    Block current = getBlock(i, col);
                    if (!current.isEmpty())
                        continue;
                    Block toSwap = upperNotEmpty(current);
                    if (toSwap != null) {
                        setEntity(current.getRow(), current.getColumn(), toSwap.getEntity());
                        toSwap.setEntity(new Empty(toSwap.getRow(), toSwap.getColumn()));
                    }
                }
                for (int i = amountExtraCandys - 1; i >= 0; i--) {
                    Block current = getBlock(i, col);
                    setEntity(current.getRow(), current.getColumn(), candys.remove(0));
                }
            }
        }
        return emptyColumns;
    }

    /**
     * Given a block, checks the column in search of a block with a movable entity.
     * Stops and returns null if non {@link Interfaces.Swappable} with candy is
     * found.
     * 
     * @param block
     * @return entity above in the same column that is not empty. null if non
     * movable entity found.
     */
    public Block upperNotEmpty(Block block) {
        Block nextNotEmpty = null;
        int col = block.getColumn();
        for (int i = block.getRow() - 1; i >= 0 && nextNotEmpty == null; i--) {
            Block current = getBlock(i, col);
            if (current.isEmpty())
                continue;
            if (!hasMovableEntity(current))
                break;
            nextNotEmpty = current;
        }
        return nextNotEmpty;
    }

    /**
     * @param row valid {@code row} values are ({@code row >= 0}) &&
     * ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) &&
     * ({@code column < }{@link Board#COLUMNS}}
     * @return {@code true} if it is a valid position inside the board.
     */
    public static boolean isValidBlock(int row, int column) { return row >= 0 && row < ROWS && column >= 0 && column < COLUMNS; }

    private void movePlayerPosition(int newRow, int newColumn) {
        if (isValidBlock(newRow, newColumn)) {
            blockMove.playNew();
            matrix[playerRow][playerColumn].defocus();
            matrix[newRow][newColumn].focus();
            playerRow = newRow;
            playerColumn = newColumn;
        }
    }

    private Colour randomColour() {
        Colour[] colores = { Colour.BLUE, Colour.GREEN, Colour.PURPLE, Colour.RED, Colour.YELLOW };
        return colores[candyPicker.nextInt(0, colores.length)];
    }

    private boolean canSwap(Block block1, Block block2) {
        final Entity e1 = block1.getEntity();
        final Entity e2 = block2.getEntity();
        return e1.isSwappable(e2);
    }

    private boolean specialSwap(Block block1, Block block2) { return block1.getEntity().isSpecialSwap(block2.getEntity()); }

    public static boolean hasMovableEntity(Block block) { return dummy.isSwappable(block.getEntity()); }

    public boolean compareColors(Block block1, Block block2) {
        return block1.getEntity().getColour() == block2.getEntity().getColour();
    }
}