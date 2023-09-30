package Entities;

import java.util.List;

import Interfaces.Equivalent;
import Logic.Board;

public class Stripped extends Entity {
	/* Attributes */
	protected boolean isHorizontal;

	/* Constructor */
	public Stripped(int posRow, int posColumn, Colour colour, boolean isHorizontal) {
		this.posRow = posRow;
		this.posColumn = posColumn;
		this.colour = colour;
		this.isHorizontal = isHorizontal;
	}

    /* Constructor */
    public Stripped(int posRow, int posColumn, Colour colour) {
        this.posRow = posRow;
        this.posColumn = posColumn;
        this.colour = colour;
    }
    
	@Override
	public boolean isEquivalent(Entity e) {
		return e.equals(this);
	}

	@Override
	public boolean equals(Candy c) {
		return false;
	}

	@Override
	public boolean equals(Glazed g) {
		return false;
	}

	// TODO verificar equals
	@Override
	public boolean equals(Wrapped w) {
		return false;
	}

	@Override
	public boolean equals(Stripped s) {
		return s.getColour() == colour;
	}

	@Override
	public boolean equals(Jelly j) {
		return false;
	}

	@Override
	public boolean equals(Empty e) {
		return false;
	}

	@Override
	// TODO GUI
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSwappable(Entity e) {
		return e.canReceive(this);
	}

	@Override
	// TODO retorna true dependiendo del color o si hay combinaciones => si hay
	// combinaciones o no, se fija el tablero
	public boolean canReceive(Candy c) {
		return true;
	}

	@Override
	public boolean canReceive(Glazed g) {
		return false;
	}

	@Override
	public boolean canReceive(Stripped s) {
		return true;
	}

	@Override
	public boolean canReceive(Wrapped w) {
		return true;
	}

	@Override
	// TODO
	public List<Equivalent> getDestroyables(Board b) {
		return null;
	}

}
