package Entities.Interfaces;

import Entities.Candy;
import Entities.Entity;
import Entities.Glazed;
import Entities.Jelly;
import Entities.Stripped;
import Entities.Wrapped;

public interface Equivalent {
	public boolean isEquivalent(Entity e);
	public boolean equals(Candy c);
	public boolean equals(Glazed g);
	public boolean equals(Wrapped w);
	public boolean equals(Stripped s);
	public boolean equals(Jelly j);
}
