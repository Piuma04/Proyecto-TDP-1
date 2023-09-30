package Entities;

public interface Equivalent {
	public boolean isEquivalent(Entity e);
	public boolean equals(Candy c);
	public boolean equals(Glazed g);
	public boolean equals(Wrapped w);
	public boolean equals(Stripped s);
	public boolean equals(Jelly j);
}
