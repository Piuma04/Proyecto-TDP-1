package Logic;

import java.util.List;

import java.util.LinkedList;
import java.util.Set;

import Entities.Block;
import Entities.Entity;
import Interfaces.Equivalent;

import java.util.HashSet;

public class Board {
    private static final int ROWS = 6;
    private static final int COLUMNS = 6;
    private int row, column;
    private Block[][] matriz;
    private Game myGame;

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public void fillBoard() {

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
        return null;

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
        Set<Integer> s = new HashSet<Integer>();
        List<Block> temp = new LinkedList<Block>();
        for (int j = COLUMNS; j > 0 && s.size() < COLUMNS; j--) {
            for (int i = ROWS; i > 0 && s.size() < ROWS; i--) {
                if (matriz[i][j].isEmpty() && !s.contains(j)) {
                    s.add(j);
                }
            }
        }
        fillBoard();
        for (Integer j : s) {
            for (int i = 0; i < ROWS; i++) {
                temp.addAll(checkCombinations(i, j));
            }
        }
        return temp;
    }

    private List<Block> checkCombinations(int row, int column) {
        return null;

    }
}
