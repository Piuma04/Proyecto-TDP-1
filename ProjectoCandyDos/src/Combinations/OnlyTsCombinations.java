package Combinations;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;
import Enums.Colour;
import Logic.Block;
import Logic.Board;

public class OnlyTsCombinations extends BaseCombination {

	public OnlyTsCombinations(Board b) {super(b); }
	//TODO Implement the class correctly
	
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
	        PriorityEntity entity = null;
	        Set<Block> tComb = getT(block);
	        combinationsOut.addAll(tComb);
	        if(!tComb.isEmpty())
	        {
	        	entity = new PriorityEntity(new Wrapped(block.getRow(),block.getColumn(),color),2);
	        }
	        System.out.println(combinationsOut);
	        return entity;
	    }
	 /*
	 private Set<Block> getL(Block block)
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
	 }*/
	 private Set<Block> getT(Block block)
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
	 private List<List<Coord>> getTShapesLeft() {
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


	private List<List<Coord>> getTShapesRight() {
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


	private List<List<Coord>> getTShapesDown() {
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


	public static List<List<Coord>> getTShapesUp() {
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
	private Set<Block> getBlockShape(Block b,List<Coord> tShape)
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
	private Coord sum(Coord c,Coord c2)
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
	private Coord mult(Coord c,int mult)
	{
		int row = c.getRow();
		int col = c.getColumn();
		Coord prod = new Coord();
		prod.setRow(row*mult);
		prod.setColumn(col*mult);
		return prod;
	}
}
