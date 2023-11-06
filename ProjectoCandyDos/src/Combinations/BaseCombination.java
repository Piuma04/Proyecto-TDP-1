package Combinations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

import Enums.Colour;
import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;
import Logic.Block;
import Logic.Board;

public abstract class BaseCombination implements CombinationLogic {

    protected static final int MAX_WRAPPED_COMBINATION_SIZE = 6;
    protected static final int STRIPPED_COMBINATION_SIZE = 4;
    protected static final int MIN_COMBINATION_SIZE = 3;

    protected Board board;

    public BaseCombination(Board b) { board = b; }

    protected final Coord up = new Coord(-1,0);
	protected final Coord down = new Coord(1,0);
	protected final Coord left = new Coord(0,-1);
	protected final Coord right = new Coord(0,1);
	@Override
	public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut) {
		 Set<Block> combinations = new HashSet<Block>();
	        PriorityEntity candy = null;
	        for (Block block : blocks) {
	            if (!combinations.contains(block)) {
	                candy = checkBlockHorizontalVerticalCombination(block, combinations);
	                if (candy != null)
	                    candysOut.add(candy.getEntity());
	            }
	        }
	        return combinations;
	}
	protected PriorityEntity checkBlockHorizontalVerticalCombination(Block block, Set<Block> combinationsOut) {
	        final Colour color = board.getBlockColour(block);
	        Set<Block> consecutiveH = consecutiveHorizontal(block);
	        Set<Block> consecutiveV = consecutiveVertical(block);
	        final int hSize = consecutiveH.size() + 1; // block to check added.
	        final int vSize = consecutiveV.size() + 1; // block to check added.
	        final int row = block.getRow();
	        final int column = block.getColumn();
	        PriorityEntity entity = null;
	        //Set<Block> tComb = getT(block);
	        Set<Block> lComb = getL(block);
	        Set<Block> xComb = getX(block);
	        //combinationsOut.addAll(tComb);
	        combinationsOut.addAll(lComb);
	        combinationsOut.addAll(xComb);
	        if(!lComb.isEmpty() || !xComb.isEmpty())
	        {
	        	entity = new PriorityEntity(new Wrapped(block.getRow(),block.getColumn(),color),2);
	        }
	        if (hSize >= MIN_COMBINATION_SIZE) {
	            combinationsOut.add(block);
	            combinationsOut.addAll(consecutiveH);
	            if (hSize == STRIPPED_COMBINATION_SIZE)
	                entity = new PriorityEntity(new Stripped(row, column, color, false), 1);
	        }
	        else if (vSize >= MIN_COMBINATION_SIZE) {
	            combinationsOut.add(block);
	            combinationsOut.addAll(consecutiveV);
	            if (vSize == STRIPPED_COMBINATION_SIZE)
	                entity = new PriorityEntity(new Stripped(row, column, color, true), 1);
	        }
	        System.out.println(combinationsOut);
	        return entity;
	    }
	
	        
    public Set<Block> checkRemainingCombinations(Map<Integer, List<Block>> emptyColumnBlocks, List<Entity> candysOut) {
        Set<Block> unchecked = new HashSet<Block>();
        for (int col = 0; col < Board.getColumns(); col++) {
            List<Block> oldEmptyBlocks = emptyColumnBlocks.get(col);
            if (oldEmptyBlocks.size() > 0)
            {
                Block lower = oldEmptyBlocks.get(0);
                for (int row = lower.getRow(); row >= 0; row--) {
                    Block block = board.getBlock(row, col);
                    if (!block.isEmpty())
                        unchecked.add(block);
                }
            }
        }
        return checkCombinations(unchecked, candysOut);
    }

	protected Set<Block> getX(Block block) {
		Set<Block> x = new HashSet<Block>();
		for(List<Coord> xShape: getXShapes())
			 x.addAll(getBlockShape(block,xShape));
		return x;
	}
	protected Set<Block> getL(Block block)
	 {
		 Set<Block> l = new HashSet<Block>();
		 for(List<Coord> lShape: getLShapesUpR())
			 l.addAll(getBlockShape(block,lShape));
		 for(List<Coord> lShape: getLShapesUpL())
			 l.addAll(getBlockShape(block,lShape));
	     for(List<Coord> lShape: getLShapesDownR())
	    	 l.addAll(getBlockShape(block,lShape));
	     for(List<Coord> lShape: getLShapesDownL())
	    	 l.addAll(getBlockShape(block,lShape));
	     for(List<Coord> lShape: getLShapesRightU())
	    	 l.addAll(getBlockShape(block,lShape));
	     for(List<Coord> lShape: getLShapesRightD())
	    	 l.addAll(getBlockShape(block,lShape));
	     for(List<Coord> lShape: getLShapesLeftU())
	    	 l.addAll(getBlockShape(block,lShape));
	     for(List<Coord> lShape: getLShapesLeftD())
	    	 l.addAll(getBlockShape(block,lShape));
	     return l;
	 }
	protected Set<Block> getT(Block block)
	 {
		 Set<Block> t = new HashSet<Block>();
		 for(List<Coord> tShape: getTShapesUp())
			 t.addAll(getBlockShape(block,tShape));
	     for(List<Coord> tShape: getTShapesDown())
	    	 t.addAll(getBlockShape(block,tShape));
	     for(List<Coord> tShape: getTShapesRight())
	    	 t.addAll(getBlockShape(block,tShape));
	     for(List<Coord> tShape: getTShapesLeft())
	    	 t.addAll(getBlockShape(block,tShape));
	     return t;
	 }
	protected List<List<Coord>> getXShapes() {
		List<List<Coord>> xShapes = new LinkedList<>();
		
		// Define las coordenadas de las 5 formas "L" y agrégalas a la lista
        List<Coord> xShape1 = List.of(up,left,down,right);
        List<Coord> xShape2 = List.of(up,mult(up,2),sum(up,left),sum(up,right));
        List<Coord> xShape3 = List.of(down,mult(down,2),sum(down,left),sum(down,right));
        List<Coord> xShape4 = List.of(right,mult(right,2),sum(down,right),sum(up,right));
        List<Coord> xShape5 = List.of(left,mult(left,2),sum(down,left),sum(up,left));

        xShapes.add(xShape1);
        xShapes.add(xShape2);
        xShapes.add(xShape3);
        xShapes.add(xShape4);
        xShapes.add(xShape5);

        return xShapes;
	}
	protected List<List<Coord>> getLShapesLeftU() {
		 List<List<Coord>> lShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "L" y agrégalas a la lista
	        List<Coord> lShape1 = List.of(left, mult(left,2), sum(mult(left,2),up), sum(mult(left,2),mult(up,2)));
	        List<Coord> lShape2 = List.of(right, left, sum(left,up), sum(left,mult(up,2)));
	        List<Coord> lShape3 = List.of(right, mult(right,2), up, mult(up,2));
	        List<Coord> lShape4 = List.of(down,up,sum(down,right),sum(down,mult(right,2)));
	        List<Coord> lShape5 = List.of(down,mult(down,2),sum(mult(down,2),right),sum(mult(down,2),mult(right,2)));

	        lShapes.add(lShape1);
	        lShapes.add(lShape2);
	        lShapes.add(lShape3);
	        lShapes.add(lShape4);
	        lShapes.add(lShape5);

	        return lShapes;
	}
	protected List<List<Coord>> getLShapesLeftD() {
		 List<List<Coord>> lShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "L" y agrégalas a la lista
		 	List<Coord> lShape1 = List.of(left, mult(left,2), sum(mult(left,2),down), sum(mult(left,2),mult(down,2)));
	        List<Coord> lShape2 = List.of(right, left, sum(left,down), sum(left,mult(down,2)));
	        List<Coord> lShape3 = List.of(right, mult(right,2), down, mult(down,2));
	        List<Coord> lShape4 = List.of(down,up,sum(up,right),sum(up,mult(right,2)));
	        List<Coord> lShape5 = List.of(up,mult(up,2),sum(mult(up,2),right),sum(mult(up,2),mult(right,2)));

	        lShapes.add(lShape1);
	        lShapes.add(lShape2);
	        lShapes.add(lShape3);
	        lShapes.add(lShape4);
	        lShapes.add(lShape5);

	        return lShapes;
	}
	protected List<List<Coord>> getLShapesRightU() {
		 List<List<Coord>> lShapes = new LinkedList<>();
	        // Define las coordenadas de las 5 formas "L" y agrégalas a la lista
	        List<Coord> lShape1 = List.of(right, mult(right,2), sum(mult(right,2),up), sum(mult(right,2),mult(up,2)));
	        List<Coord> lShape2 = List.of(left, right, sum(right,up), sum(right,mult(up,2)));
	        List<Coord> lShape3 = List.of(left, mult(left,2), up, mult(up,2));
	        List<Coord> lShape4 = List.of(up,down,sum(down,left),sum(down,mult(left,2)));
	        List<Coord> lShape5 = List.of(down,mult(down,2),sum(mult(down,2),left),sum(mult(down,2),mult(left,2)));

	        lShapes.add(lShape1);
	        lShapes.add(lShape2);
	        lShapes.add(lShape3);
	        lShapes.add(lShape4);
	        lShapes.add(lShape5);

	        return lShapes;
	}
	protected List<List<Coord>> getLShapesRightD() {
		 List<List<Coord>> lShapes = new LinkedList<>();
	        // Define las coordenadas de las 5 formas "L" y agrégalas a la lista
	        List<Coord> lShape1 = List.of(right, mult(right,2), sum(mult(right,2),down), sum(mult(right,2),mult(down,2)));
	        List<Coord> lShape2 = List.of(left, right, sum(right,down), sum(right,mult(down,2)));
	        List<Coord> lShape3 = List.of(left, mult(left,2), down, mult(down,2));
	        List<Coord> lShape4 = List.of(up,down,sum(up,left),sum(up,mult(left,2)));
	        List<Coord> lShape5 = List.of(up,mult(up,2),sum(mult(up,2),left),sum(mult(up,2),mult(left,2)));

	        lShapes.add(lShape1);
	        lShapes.add(lShape2);
	        lShapes.add(lShape3);
	        lShapes.add(lShape4);
	        lShapes.add(lShape5);

	        return lShapes;
	}
	protected List<List<Coord>> getLShapesDownL() {
		 List<List<Coord>> lShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "L" y agrégalas a la lista
	        List<Coord> lShape1 = List.of(down, mult(down,2), sum(mult(down,2),left),sum(mult(down,2),mult(left,2)));
	        List<Coord> lShape2 = List.of(up, down, sum(down,left),sum(down,mult(left,2)));
	        List<Coord> lShape3 = List.of(up, mult(up,2), left,mult(left,2));
	        List<Coord> lShape4 = List.of(left, right,sum(right,up),sum(right,mult(up,2)));
	        List<Coord> lShape5 = List.of(right, mult(right,2),sum(mult(right,2),up),sum(mult(right,2),mult(up,2)));

	        lShapes.add(lShape1);
	        lShapes.add(lShape2);
	        lShapes.add(lShape3);
	        lShapes.add(lShape4);
	        lShapes.add(lShape5);

	        return lShapes;
	}
	protected List<List<Coord>> getLShapesDownR() {
		 List<List<Coord>> lShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "L" y agrégalas a la lista
	        List<Coord> lShape1 = List.of(down, mult(down,2), sum(mult(down,2),right),sum(mult(down,2),mult(right,2)));
	        List<Coord> lShape2 = List.of(up, down, sum(down,right),sum(down,mult(right,2)));
	        List<Coord> lShape3 = List.of(up, mult(up,2), right,mult(right,2));
	        List<Coord> lShape4 = List.of(right,left,sum(left,up),sum(left,mult(up,2)));
	        List<Coord> lShape5 = List.of(left, mult(left,2),sum(mult(left,2),up),sum(mult(left,2),mult(up,2)));

	        lShapes.add(lShape1);
	        lShapes.add(lShape2);
	        lShapes.add(lShape3);
	        lShapes.add(lShape4);
	        lShapes.add(lShape5);

	        return lShapes;
	}
	protected List<List<Coord>> getLShapesUpL() {
		 List<List<Coord>> lShapes = new LinkedList<>();

		// Define las coordenadas de las 5 formas "L" y agrégalas a la lista
	        List<Coord> lShape1 = List.of(up, mult(up,2), sum(mult(up,2),left), sum(mult(up,2),mult(left,2)));
	        List<Coord> lShape2 = List.of(down, up, sum(up,left), sum(up,mult(left,2)));
	        List<Coord> lShape3 = List.of(down, mult(down,2), left, mult(left,2));
	        List<Coord> lShape4 = List.of(left, right, sum(right,down), sum(right,mult(down,2)));
	        List<Coord> lShape5 = List.of(right, mult(right,2), sum(down,mult(right,2)), sum(mult(down,2),mult(right,2)));
	        
	        lShapes.add(lShape1);
	        lShapes.add(lShape2);
	        lShapes.add(lShape3);
	        lShapes.add(lShape4);
	        lShapes.add(lShape5);

	        return lShapes;
	}
	protected List<List<Coord>> getLShapesUpR() {
		 List<List<Coord>> lShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "L" y agrégalas a la lista
		 	List<Coord> lShape1 = List.of(up, mult(up,2), sum(mult(up,2),right), sum(mult(up,2),mult(right,2)));
	        List<Coord> lShape2 = List.of(down, up, sum(up,right), sum(up,mult(right,2)));
	        List<Coord> lShape3 = List.of(down, mult(down,2), right, mult(right,2));
	        List<Coord> lShape4 = List.of(right, left, sum(left,down), sum(left,mult(down,2)));
	        List<Coord> lShape5 = List.of(left, mult(left,2), sum(down,mult(left,2)), sum(mult(down,2),mult(left,2)));

	        lShapes.add(lShape1);
	        lShapes.add(lShape2);
	        lShapes.add(lShape3);
	        lShapes.add(lShape4);
	        lShapes.add(lShape5);

	        return lShapes;
		}
	protected List<List<Coord>> getTShapesLeft() {
		 List<List<Coord>> tShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "T" y agrégalas a la lista
	        List<Coord> tShape1 = List.of(new Coord(0, -1), new Coord(0, -2), new Coord(1, -2), new Coord(-1, -2));
	        List<Coord> tShape2 = List.of(new Coord(0, 1), new Coord(0, -1), new Coord(1, -1), new Coord(-1, -1));
	        List<Coord> tShape3 = List.of(new Coord(0, 1), new Coord(0, 2), new Coord(1, 0), new Coord(-1, 0));
	        List<Coord> tShape4 = List.of(new Coord(1, 0), new Coord(1, 1), new Coord(1, 2), new Coord(2, 0));
	        List<Coord> tShape5 = List.of(new Coord(-1, 0), new Coord(-1, 1), new Coord(-1, 2), new Coord(-2, 0));

	        tShapes.add(tShape1);
	        tShapes.add(tShape2);
	        tShapes.add(tShape3);
	        tShapes.add(tShape4);
	        tShapes.add(tShape5);

	        return tShapes;
	}
	protected List<List<Coord>> getTShapesRight() {
		 List<List<Coord>> tShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "T" y agrégalas a la lista
		 	List<Coord> tShape1 = List.of(new Coord(0, 1), new Coord(0, 2), new Coord(1, 2), new Coord(-1, 2));
	        List<Coord> tShape2 = List.of(new Coord(0, -1), new Coord(0, 1), new Coord(1, 1), new Coord(-1, 1));
	        List<Coord> tShape3 = List.of(new Coord(0, -1), new Coord(0, -2), new Coord(1, 0), new Coord(-1, 0));
	        List<Coord> tShape4 = List.of(new Coord(1, 0), new Coord(1, -1), new Coord(1, -2), new Coord(2, 0));
	        List<Coord> tShape5 = List.of(new Coord(-1, 0), new Coord(-1, -1), new Coord(-1, -2), new Coord(-2, 0));

	        tShapes.add(tShape1);
	        tShapes.add(tShape2);
	        tShapes.add(tShape3);
	        tShapes.add(tShape4);
	        tShapes.add(tShape5);

	        return tShapes;
	}
	protected List<List<Coord>> getTShapesDown() {
		 List<List<Coord>> tShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "T" y agrégalas a la lista
	        List<Coord> tShape1 = List.of(new Coord(1, 0), new Coord(2, 0), new Coord(2, 1), new Coord(2, -1));
	        List<Coord> tShape2 = List.of(new Coord(-1, 0), new Coord(1, 0), new Coord(1, -1), new Coord(1, 1));
	        List<Coord> tShape3 = List.of(new Coord(-1, 0), new Coord(-2, 0), new Coord(0, 1), new Coord(0, -1));
	        List<Coord> tShape4 = List.of(new Coord(-1, 1), new Coord(-2, 1), new Coord(0, 1), new Coord(0, 2));
	        List<Coord> tShape5 = List.of(new Coord(-1, -1), new Coord(-2, -1), new Coord(0, -1), new Coord(0, -2));

	        tShapes.add(tShape1);
	        tShapes.add(tShape2);
	        tShapes.add(tShape3);
	        tShapes.add(tShape4);
	        tShapes.add(tShape5);

	        return tShapes;
	}
	protected List<List<Coord>> getTShapesUp() {
	        List<List<Coord>> tShapes = new LinkedList<>();

	        // Define las coordenadas de las 5 formas "T" y agrégalas a la lista
	        List<Coord> tShape1 = List.of(new Coord(-1, 0), new Coord(-2, 0), new Coord(-2, 1), new Coord(-2, -1));
	        List<Coord> tShape2 = List.of(new Coord(1, 0), new Coord(-1, 0), new Coord(-1, 1), new Coord(-1, -1));
	        List<Coord> tShape3 = List.of(new Coord(1, 0), new Coord(2, 0), new Coord(0, 1), new Coord(0, -1));
	        List<Coord> tShape4 = List.of(new Coord(1, 1), new Coord(2, 1), new Coord(0, 1), new Coord(0, 2));
	        List<Coord> tShape5 = List.of(new Coord(1, -1), new Coord(2, -1), new Coord(0, -1), new Coord(0, -1));

	        tShapes.add(tShape1);
	        tShapes.add(tShape2);
	        tShapes.add(tShape3);
	        tShapes.add(tShape4);
	        tShapes.add(tShape5);

	        return tShapes;
	    }
	//Detalles con la formacion t
	protected Set<Block> getBlockShape(Block b,List<Coord> tShape)
	{
		Set<Block> t = new HashSet<Block>();
		int row = b.getRow();
		int col = b.getColumn();
		boolean isT = true;
		Colour blockColor = b.getEntity().getColour();
		t.add(b);
		for(Coord c: tShape)
		{
			if(Board.isValidBlockPosition(row+c.getRow(), col+c.getColumn()) && blockColor==board.getBlock(row+c.getRow(),col+c.getColumn()).getEntity().getColour())
				t.add(board.getBlock(row+c.getRow(),col+c.getColumn()));
			else 
				isT = false;
		}
		if(!isT)
			t.clear();
		return t;
	}
	protected Coord sum(Coord c,Coord c2)
	{
		int row1 = c.getRow();
		int col1 = c.getColumn();
		int row2 = c2.getRow();
		int col2 = c2.getColumn();
		Coord prod = new Coord();
		prod.setRow(row1+row2);
		prod.setColumn(col1+col2);
		return prod;
	}
	protected Coord mult(Coord c,int mult)
	{
		int row = c.getRow();
		int col = c.getColumn();
		Coord prod = new Coord();
		prod.setRow(row*mult);
		prod.setColumn(col*mult);
		return prod;
	}

    /**
     * Biased to Horizontal Combinations.
     */
    protected PriorityEntity checkStraightBlockHorizontalVerticalCombination(Block block, Set<Block> combinationsOut) {
        final Colour color = board.getBlockColour(block);

        final Set<Block> consecutiveH = consecutiveHorizontal(block);
        final Set<Block> consecutiveV = consecutiveVertical(block);

        final int hSize = consecutiveH.size() + 1; // block to check added.
        final int vSize = consecutiveV.size() + 1; // block to check added.

        PriorityEntity entity = null;
        final int row = block.getRow();
        final int column = block.getColumn();

        if (hSize >= MIN_COMBINATION_SIZE) {
            combinationsOut.add(block);
            combinationsOut.addAll(consecutiveH);
            if (hSize == STRIPPED_COMBINATION_SIZE)
                entity = new PriorityEntity(new Stripped(row, column, color, false), 1);
        }
        else if (vSize >= MIN_COMBINATION_SIZE) {
            combinationsOut.add(block);
            combinationsOut.addAll(consecutiveV);
            if (vSize == STRIPPED_COMBINATION_SIZE)
                entity = new PriorityEntity(new Stripped(row, column, color, true), 1);
        }
        return entity;
    }

    protected Set<Block> consecutiveVertical(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        if (!Board.hasMovableEntity(block))
            return blocks;
        Set<Block> verticalDown = consecutiveVerticalDown(block);
        Set<Block> verticalUp = consecutiveVerticalUp(block);
        final int verticalSize = verticalDown.size() + verticalUp.size() + 1;  // + 1 is the checked block.
        if (verticalSize >= MIN_COMBINATION_SIZE) {
            blocks.addAll(verticalDown);
            blocks.addAll(verticalUp);
        }
        return blocks;
    }
    
    protected Set<Block> consecutiveVerticalUp(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        boolean cumple = true;
        for (int r = row - 1; r >= 0 && r < Board.getRows() && cumple; r--) {
            Block current = board.getBlock(r, column);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        return blocks;
    }
    
    protected Set<Block> consecutiveVerticalDown(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        boolean cumple = true;
        for (int r = row + 1; Board.isValidBlockPosition(r, column) && cumple; r++) {
            Block current = board.getBlock(r, column);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        return blocks;
    }


    protected Set<Block> consecutiveHorizontal(Block block) {
        
        Set<Block> blocks = new HashSet<Block>();
        if (!Board.hasMovableEntity(block))
            return blocks;
        Set<Block> horizontalLeft = consecutiveHorizontalLeft(block);
        Set<Block> horizontalRight = consecutiveHorizontalRight(block);
        final int horizontalSize = horizontalLeft.size() + horizontalRight.size() + 1; // + 1 is the checked block.
        if (horizontalSize >= MIN_COMBINATION_SIZE) {
            blocks.addAll(horizontalLeft);
            blocks.addAll(horizontalRight);
        }
        return blocks;
    }
    
    protected Set<Block> consecutiveHorizontalLeft(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        boolean cumple = true;
        for (int c = column - 1; Board.isValidBlockPosition(row, c) && cumple; c--) {
            Block current = board.getBlock(row, c);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        return blocks;
    }
    
    protected Set<Block> consecutiveHorizontalRight(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        boolean cumple = true;
        for (int c = column + 1; Board.isValidBlockPosition(row, c) && cumple; c++) {
            Block current = board.getBlock(row, c);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        return blocks;
    }
}