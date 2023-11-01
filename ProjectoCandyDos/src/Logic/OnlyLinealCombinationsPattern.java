package Logic;

import java.util.HashSet;
import java.util.Set;

import Entities.Colour;
import Entities.PriorityEntity;
import Entities.Stripped;

public class OnlyLinealCombinationsPattern extends Combination{

	public OnlyLinealCombinationsPattern(Board b) {
		super(b);
		// TODO Auto-generated constructor stub
	}
	
	protected PriorityEntity checkBlockCombination(Block block, Set<Block> combinationsOut) {
        Set<Block> combination = new HashSet<Block>();
        Set<Block> consecutiveH = new HashSet<Block>();
        Set<Block> consecutiveV = new HashSet<Block>();
        Colour color = block.getEntity().getColour();
        consecutiveH = consecutiveH(block);
        consecutiveV = consecutiveV(block);
        combination.add(block);
        int hSize = consecutiveH.size();
        int vSize = consecutiveV.size();
        int row = block.getRow();
        int column = block.getColumn();
        PriorityEntity entity = null;
        
        if (hSize == 3) {
            entity = new PriorityEntity(new Stripped(row, column, color, true), 1);
            combination.addAll(consecutiveH);
        }
        else if (vSize == 3) {
            entity = new PriorityEntity(new Stripped(row, column, color, false), 1);
            combination.addAll(consecutiveV);
        }
        else if(vSize>=2) 
        	combination.addAll(consecutiveV);
        else if(hSize>=2) 
        	combination.addAll(consecutiveH);
        
        
        if (combination.size() >= 3)
            combinationsOut.addAll(combination);
        return entity;
    }
	

	
}