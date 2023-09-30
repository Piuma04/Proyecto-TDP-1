package Entities;


import java.util.List;

import Logic.Board;

public class Stripped extends Entity {

    /* Constructor */
    public Stripped(int posRow, int posColumn, Colour colour) {
        this.posRow = posRow;
        this.posColumn = posColumn;
        this.colour = colour;
    }
    
	@Override
	public boolean isEquivalent(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Candy c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Glazed g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Wrapped w) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Stripped s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Jelly j) {
		// TODO Auto-generated method stub
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
	public boolean canReceive(Candy c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReceive(Glazed g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReceive(Stripped s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReceive(Wrapped w) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	// TODO
	public List<Block> getDestroyables(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

}
