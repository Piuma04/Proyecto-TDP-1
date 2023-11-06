package Logic;

import java.util.Random;

import Entities.*;
import Enums.Colour;

public class ConcreteCandyFactory implements CandyFactory{
	private static final Random candyPicker = new Random();
	@Override
	public PriorityEntity createWrapped(Block b,Colour color) {
		return new PriorityEntity(new Wrapped(b.getRow(), b.getColumn(), color), 2);
	}

	@Override
	public PriorityEntity createHorizontalStripped(Block b,Colour color) {
		return new PriorityEntity(new Stripped(b.getRow(), b.getColumn(), color,true), 1);
	}

	@Override
	public PriorityEntity createVerticalWrapped(Block b,Colour color) {
		return new PriorityEntity(new Stripped(b.getRow(), b.getColumn(), color,false), 1);
	}

	@Override
	public Entity createCandy(Block b,Colour color) {
		return new Candy(b.getRow(), b.getColumn(), color);
	}

	@Override
	public Entity createGlazed(Block b) {
		return new Glazed(b.getRow(), b.getColumn());
	}

	@Override
	public Entity createEmptySpace(Block b) {
		return new Empty(b.getRow(), b.getColumn());
	}

	@Override
	public Modifier createJelly(Block b) {
		return new Jelly(b.getRow(), b.getColumn());
	}
	public Entity createRandomCandy(Block b) {
		return new Candy(b.getRow(), b.getColumn(), randomColour());
	}
	 private Colour randomColour() {
	        Colour[] colores = { Colour.BLUE, Colour.GREEN, Colour.PURPLE, Colour.RED, Colour.YELLOW };
	        return colores[candyPicker.nextInt(0, colores.length)];
	}
}
