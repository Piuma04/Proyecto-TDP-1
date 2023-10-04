package Interfaces;

/**
 * Interface for objects that can gain and lose focus.
 */
public interface Focusable {

	/**
	 * Attempts to focus on this object.
	 *
	 * @return true if the object successfully gains focus, false otherwise.
	 */
	public boolean focus();

	/**
	 * Defocuses the object, removing focus from it.
	 */
	public void defocus();
}
