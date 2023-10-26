package Interfaces;

import Entities.Bomb;
import Entities.Candy;
import Entities.Empty;
import Entities.Glazed;
import Entities.Jelly;
import Entities.MegaStripped;
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
	public boolean isEquivalent(Equivalent e);

	/**
	 * Compares this Candy object with another Candy object for equality.
	 *
	 * @param c The Candy object to compare with.
	 * @return true if the two Candy objects are equal, false otherwise.
	 */
	public boolean isEqual(Candy c);

	/**
	 * Checks if this Entity is equal to a Glazed object.
	 *
	 * @param g The Glazed object to compare with.
	 * @return true if this Entity is equal to the Glazed object, false otherwise.
	 */
	public boolean isEqual(Glazed g);

	/**
	 * Checks if this Entity is equal to a Wrapped object.
	 *
	 * @param w The Wrapped object to compare with.
	 * @return true if this Entity is equal to the Wrapped object, false otherwise.
	 */
	public boolean isEqual(Wrapped w);

	/**
	 * Checks if this Entity is equal to a Stripped object.
	 *
	 * @param s The Stripped object to compare with.
	 * @return true if this Entity is equal to the Stripped object, false otherwise.
	 */
	public boolean isEqual(Stripped s);

	/**
	 * Checks if this Entity is equal to a Jelly object.
	 *
	 * @param j The Jelly object to compare with.
	 * @return true if this Entity is equal to the Jelly object, false otherwise.
	 */
	public boolean isEqual(Jelly j);

	/**
	 * Checks if this Entity is equal to an Empty object.
	 *
	 * @param e The Empty object to compare with.
	 * @return true if this Entity is equal to the Empty object, false otherwise.
	 */
	public boolean isEqual(Empty e);
	/**
	 * Checks if this Entity is equal to an MegaStripped object.
	 *
	 * @param e The MegaStripped object to compare with.
	 * @return true if this Entity is equal to the MegaStripped object, false otherwise.
	 */
	public boolean isEqual(MegaStripped e);
	
	/**
	 * Checks if this Entity is equal to a Bomb
	 * 
	 * @param b the Bomb object to compare with
	 * @return true if equals false otherwise
	 */
	public boolean isEqual(Bomb b);
	
	public int getScore();
}
