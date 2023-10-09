package Interfaces;

import Entities.Candy;
import Entities.Glazed;
import Entities.Stripped;
import Entities.Wrapped;

/**
 * Interface for objects that can be swapped and receive various types of
 * candies.
 */
public interface Swappable {

	/**
	 * Checks if this object can be swapped with the given entity.
	 *
	 * @param e The entity to swap with.
	 * @return true if swappable, false otherwise.
	 */
	public boolean isSwappable(Swappable e);

	/**
	 * Checks if this object can receive a general candy.
	 *
	 * @param c The candy to receive.
	 * @return true if it can receive, false otherwise.
	 */
	public boolean canReceive(Candy c);

	/**
	 * Checks if this object can receive a glazed candy.
	 *
	 * @param g The glazed candy to receive.
	 * @return true if it can receive, false otherwise.
	 */
	public boolean canReceive(Glazed g);

	/**
	 * Checks if this object can receive a stripped candy.
	 *
	 * @param s The stripped candy to receive.
	 * @return true if it can receive, false otherwise.
	 */
	public boolean canReceive(Stripped s);

	/**
	 * Checks if this object can receive a wrapped candy.
	 *
	 * @param w The wrapped candy to receive.
	 * @return true if it can receive, false otherwise.
	 */
	public boolean canReceive(Wrapped w);
}