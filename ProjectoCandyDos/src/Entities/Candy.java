package Entities;

import java.util.List;
import java.util.Set;

import Enums.Colour;
import Interfaces.Equivalent;
import Interfaces.SpecialDestroy;
import Interfaces.Swappable;

import java.util.HashSet;
import java.util.LinkedList;

import Logic.Block;
import Logic.Board;

public class Candy extends Entity {
    public Candy(int rowPosition, int columnPosition, Colour colour) { super(rowPosition, columnPosition, colour); }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); }
    @Override public boolean isEqual(Candy c)           { return this.colour == c.getColour(); }

    @Override public boolean isSwappable(Swappable e)   { return e.canReceive(this); }
    @Override public boolean canReceive(Candy c)        { return true; }
    @Override public boolean canReceive(Stripped s)     { return true; }
    @Override public boolean canReceive(Wrapped w)      { return true; }
    @Override public boolean canReceive(MegaStripped m) { return true; }
    @Override public boolean canReceive(Bomb b)         { return true; }

    @Override
    public Set<Block> getSpecialDestroy(SpecialDestroy e, Board b) { return e.getSpecialDestroyables(this, b); }

    @Override
    public Set<Block> getSpecialDestroyables(MegaStripped m, Board b) {
        Set<Block> s = new HashSet<>();
        if (m.getColour() == colour) { s.addAll(m.getDestroyables(b)); }
        return s;
    }

    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        toDestroy.add(b.getBlock(row, column));
        int[] adyacentRows = { -1, 0, 1, 0 };
        int[] adyacentColumns = { 0, -1, 0, 1 };
        for (int i = 0; i < 4; i++) {
            int newRow = row + adyacentRows[i];
            int newColumn = column + adyacentColumns[i];
            if (Board.isValidBlockPosition(newRow, newColumn)) {
                Block block = b.getBlock(newRow, newColumn);
                if (block.getEntity().hasCollateralDamage())
                    toDestroy.add(block);
            }
        }
        return toDestroy;
    }

    @Override
    public int getScore() {
        int score = 0;
        switch (this.colour) {
            case RED:    score = 5;  break;
            case BLUE:   score = 20; break;
            case YELLOW: score = 20; break;
            case PURPLE: score = 25; break;
            case GREEN:  score = 10; break;
            // case ORANGE:score = 15; break
            default: score = 0; break;
        }
        return score;
    }

    public String toString() { return super.setStringColor("C"); }
}
