package Interfaces;

import Entities.Candy;

import Entities.Empty;
import Entities.Entity;
import Entities.Glazed;
import Entities.Jelly;
import Entities.Stripped;
import Entities.Wrapped;


/**
 * Represents objects that can be compared for equivalence.
 */
public interface Equivalent extends LogicEntity {

	/**
	 * Checks wether this object is equivlent to another Entity
	 * 
	 * @param e Entity to compare
	 * @return true if this object is equivalent, false otherwise
	 */
	public boolean isEquivalent(Entity e);

	/**
	 * Compares this Candy object with another Candy object for equality.
	 *
	 * @param c The Candy object to compare with.
	 * @return true if the two Candy objects are equal, false otherwise.
	 */
	public boolean equals(Candy c);

	/**
	 * Checks if this Entity is equal to a Glazed object.
	 *
	 * @param g The Glazed object to compare with.
	 * @return true if this Entity is equal to the Glazed object, false otherwise.
	 */
	public boolean equals(Glazed g);

	/**
	 * Checks if this Entity is equal to a Wrapped object.
	 *
	 * @param w The Wrapped object to compare with.
	 * @return true if this Entity is equal to the Wrapped object, false otherwise.
	 */
	public boolean equals(Wrapped w);

	/**
	 * Checks if this Entity is equal to a Stripped object.
	 *
	 * @param s The Stripped object to compare with.
	 * @return true if this Entity is equal to the Stripped object, false otherwise.
	 */
	public boolean equals(Stripped s);

	/**
	 * Checks if this Entity is equal to a Jelly object.
	 *
	 * @param j The Jelly object to compare with.
	 * @return true if this Entity is equal to the Jelly object, false otherwise.
	 */
	public boolean equals(Jelly j);

	/**
	 * Checks if this Entity is equal to an Empty object.
	 *
	 * @param e The Empty object to compare with.
	 * @return true if this Entity is equal to the Empty object, false otherwise.
	 */
	public boolean equals(Empty e);
}
