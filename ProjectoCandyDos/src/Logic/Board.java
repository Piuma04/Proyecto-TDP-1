package Logic;

import java.util.List;

import java.util.LinkedList;
import java.util.Set;

import Entities.Block;
import Entities.Candy;
import Entities.Colour;
import Entities.Entity;
import Interfaces.Equivalent;

import java.util.HashSet;
import java.util.Random;

public class Board {
    private static final int ROWS = 6;
    private static final int COLUMNS = 6;
    private int row, column;
    private Block[][] matriz;
    private Game myGame;

    public Board(Game g)
    {
    	row = 3;
    	column = 3;
    	for(int i = 0;i<ROWS;i++)
    		for(int j = 0;j<COLUMNS;j++)
    			matriz[i][j] = new Block();
    }
    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public Set<Integer> fillBoard() {
        Set<Integer> s = new HashSet<Integer>();
        boolean canNext = false;
        for (int j = COLUMNS; j > 0 && s.size() < COLUMNS; j--) {
            for (int i = ROWS; i > 0 && s.size() < COLUMNS; i--) {
                if (matriz[i][j].isEmpty() && !s.contains(j)) {
                    s.add(j);
                }
            }
        }
        for (Integer j : s) {
            for (int i = ROWS; i > 0; i--) {
                if (matriz[i][j].isEmpty()) {
                    int nextEntity = i;
                    while (nextEntity >= 0 && !canNext) {
                        nextEntity--;
                        canNext = !matriz[i][j].isEmpty();
                    }
                    if (canNext)
                        matriz[i][j].swapEntity(matriz[nextEntity][j]);
                    else
                    	//New
                    for(int cont = i;cont>0;cont--)
                    {
                    	Entity e = new Candy(cont,j,Colour.YELLOW);
                    	matriz[i][j].setEntity(e);
                    }
                    canNext = false;//
                }
            }
        }
        return s;
    }

    public void movePlayerDirection(int direction) {
        switch (direction) {
        case Game.DOWN: {
            setPlayerPosition(row + 1, column);
            break;
        }
        case Game.UP: {
            setPlayerPosition(row - 1, column);
            break;
        }
        case Game.LEFT: {
            setPlayerPosition(row, column - 1);
            break;
        }
        case Game.RIGHT: {
            setPlayerPosition(row, column + 1);
            break;
        }
        }
    }

    public List<Equivalent> swap(int direction) {
        switch (direction) {
        case Game.DOWN: {
            swapEntities(row + 1, column);
            break;
        }
        case Game.UP: {
            swapEntities(row - 1, column);
            break;
        }
        case Game.LEFT: {
            swapEntities(row, column - 1);
            break;
        }
        case Game.RIGHT: {
            swapEntities(row, column + 1);
            break;
        }
        }
        return null; // deber retornar bien
    }

    public Block getBlock(int row, int column) {
        return matriz[row][column];
    }

    public List<Equivalent> destroyEntities(List<Block> l) {
        List<Equivalent> destroyed = new LinkedList<Equivalent>();
        for (Block b : l) {
            //destroyed.addAll(b.getEntity().getDestroyables(this));
            b.destroyEntity();
        }
        return destroyed;
    }

    private void setPlayerPosition(int newRow, int newColumn) {
        if ((0 <= newRow) && (newRow < ROWS) && (0 <= newColumn) && (newColumn < COLUMNS)) {
            if (matriz[newRow][newRow].focus()) {
                matriz[row][column].defocus();
                row = newRow;
                column = newColumn;
            }
        }
    }

    private List<Equivalent> swapEntities(int newRow, int newColumn) {
        Entity e1, e2;
        List<Block> l1, l2;
        List<Equivalent> destroyed = new LinkedList<Equivalent>();
        boolean canExchange = false;
        Block b1 = matriz[row][column];
        Block b2 = matriz[newRow][newColumn];

        if (newRow < matriz.length && newColumn < matriz[0].length) {
            e1 = b1.getEntity();
            e2 = b2.getEntity();
            canExchange = e1.isSwappable(e2);
            if (canExchange) {
                b1.swapEntity(b2);
                l1 = checkCombinations(row, column);
                l2 = checkCombinations(newRow, newColumn);
                l1.addAll(l2);
                destroyed = destroyEntities(l1);
                // checkRemainingCombinations(); ver Detalles
            }
        }
        return destroyed;
    }

    private List<Block> checkRemainingCombinations() {
        List<Block> combinations = new LinkedList<Block>();
        for (Integer j : fillBoard()) {
            for (int i = 0; i < ROWS; i++) {
                combinations.addAll(checkCombinations(i, j));
            }
        }
        return combinations;
    }

    private List<Block> checkCombinations(int row, int column) {
        return null;
    }
}
