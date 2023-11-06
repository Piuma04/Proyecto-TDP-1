package Logic;
import Entities.*;
import Enums.Colour;
public interface CandyFactory {
	public abstract PriorityEntity createWrapped(Block b,Colour color);
	public abstract PriorityEntity createHorizontalStripped(Block b,Colour color);
	public abstract PriorityEntity createVerticalWrapped(Block b,Colour color);
	public abstract Entity createCandy(Block b,Colour color);
	public abstract Entity createGlazed(Block b);
	public abstract Entity createEmptySpace(Block b);
	public abstract Modifier createJelly(Block b);
	public abstract Entity createRandomCandy(Block b);
}
