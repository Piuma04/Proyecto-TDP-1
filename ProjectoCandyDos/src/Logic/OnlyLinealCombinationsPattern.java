package Logic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Entities.Colour;
import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;

public class OnlyLinealCombinationsPattern extends Combination{

	public OnlyLinealCombinationsPattern(Board b) {
		super(b);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * basicamente, se redefine check full combinations para que asi, si se usa este tipo de combinaciones,
	 * checkFullCombinations no devuelva las combianciones de cada bloque en particular
	 * asi, no se puede romper algo que esta en forma de L o T, por ejemplo
	 * lo que feu removido es lo que etsa comentado
	 */
	protected Entity checkFullCombination(Block block, Set<Block> combinationsOut) {
        List<PriorityEntity> candys = new LinkedList<PriorityEntity>();
        PriorityEntity candy = null;

        Set<Block> combination = new HashSet<Block>();
        candy = checkBlockCombination(block, combination);
        if (candy != null) candys.add(candy);
        combinationsOut.addAll(combination);
       /* for (Block b : combination) {
            Set<Block> currentCombinations = new HashSet<Block>();
            candy = checkBlockCombination(b, currentCombinations);
            if (candy != null)
                candys.add(candy);
            combinationsOut.addAll(currentCombinations);
        }*/
        // Get maximum priority.
        candy = candys.size() > 0 ? candys.get(0) : null;
        
        for (PriorityEntity pe : candys) {
            if (pe.getPriority() > candy.getPriority())
                candy = pe;
        }

        return candy != null ? candy.getEntity() : null;
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