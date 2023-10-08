package Interfaces;

/**
 * Interface for logic entities with position and image properties.
 */
public interface LogicEntity {
	/**
	 * Gets the row position of the entity.
	 *
	 * @return The row position.
	 */
	public int getRow();

	/**
     * Gets the column position of the entity.
     *
     * @return The column position.
     */
	public int getColumn();

	/**
     * Gets the image representing the entity.
     *
     * @return The image path
     */
	public String getImage();
	
	/**
     * Gets the size of the entity's picture.
     *
     * @return The picture size.
     */ 
	//TODO
	public int getPicSize();
}
