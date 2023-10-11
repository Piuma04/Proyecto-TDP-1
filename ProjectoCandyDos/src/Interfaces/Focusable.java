package Interfaces;

/**
 * Interface for objects that can gain and lose focus.
 */
public interface Focusable {

    
	public void focus();

	/**
	 * Defocuses the object, removing focus from it.
	 */
	public void defocus();
}
