package Logic;

import java.util.HashSet;
import java.util.Set;

import Entities.Colour;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;

public class VanillaPattern extends Combination{

	public VanillaPattern(Board b) {
		super(b);
	}

	protected PriorityEntity checkBlockCombination(Block block, Set<Block> combinationsOut) {
        Set<Block> combination = new HashSet<Block>();
        Set<Block> consecutiveH = new HashSet<Block>();
        Set<Block> consecutiveV = new HashSet<Block>();
        Colour color = block.getEntity().getColour();
        consecutiveH = consecutiveH(block);
        consecutiveV = consecutiveV(block);
        combination.add(block);
        combination.addAll(consecutiveV);
        combination.addAll(consecutiveH);
        int hSize = consecutiveH.size();
        int vSize = consecutiveV.size();
        int row = block.getRow();
        int column = block.getColumn();
        PriorityEntity entity = null;
        if ((hSize + vSize < 6) && (hSize + vSize > 3) && (hSize >= 2) && (vSize >= 2))
            entity = new PriorityEntity(new Wrapped(row, column, color), 2);
        else if (hSize == 3)
            entity = new PriorityEntity(new Stripped(row, column, color, false), 1);
        else if (vSize == 3)
            entity = new PriorityEntity(new Stripped(row, column, color, true), 1);
        if (combination.size() >= 3)
            combinationsOut.addAll(combination);
        return entity;
    }
}
