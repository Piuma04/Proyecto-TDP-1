package Entities;

<<<<<<< HEAD
public class Stripped extends Entity{
=======
import java.util.List;

import Logic.Board;

public class Stripped extends Entity {
>>>>>>> branch 'master' of https://github.com/Piuma04/Proyecto-TDP-1.git

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

<<<<<<< HEAD
=======
	@Override
	// TODO
	public List<Block> getDestroyables(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

>>>>>>> branch 'master' of https://github.com/Piuma04/Proyecto-TDP-1.git
}
