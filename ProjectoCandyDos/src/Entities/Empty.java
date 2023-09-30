package Entities;

public class Empty extends Entity {
	/* Constructor */
	// TODO
	public Empty() {
		// Como inicializa?
	}

	/* Methods */
	@Override
	public boolean isEquivalent(Entity e) {
		return false;
	}

	@Override
	public boolean equals(Candy c) {
		return false;
	}

	@Override
	public boolean equals(Glazed g) {
		return false;
	}

	@Override
	public boolean equals(Wrapped w) {
		return false;
	}

	@Override
	public boolean equals(Stripped s) {
		return false;
	}

	@Override
	public boolean equals(Jelly j) {
		return false;
	}

	@Override
	// TODO GUI
	public String getImage() {
		return null;
	}

	@Override
	public boolean isSwappable(Entity e) {
		return false;
	}

	@Override
	public boolean canReceive(Candy c) {
		return false;
	}

	@Override
	public boolean canReceive(Glazed g) {
		return false;
	}

	@Override
	public boolean canReceive(Stripped s) {
		return false;
	}

	@Override
	public boolean canReceive(Wrapped w) {
		return false;
	}

	public boolean isEmpty() {
		return true;
	}

}
