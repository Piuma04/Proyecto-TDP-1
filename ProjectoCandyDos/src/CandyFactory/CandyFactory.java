package CandyFactory;
import Entities.*;
import Enums.Colour;
import Interfaces.GameOverOnly;
public interface CandyFactory {
    public Entity createWrapped(int row, int column, Colour color);
    public Entity createHorizontalStripped(int row, int column, Colour color);
    public Entity createVerticalStripped(int row, int column, Colour color);

    public Entity createMegaStripped(int row, int column, Colour color);
    public Entity createCandy(int row, int column, Colour color);
    public Entity createGlazed(int row, int column);
    public Entity createEmpty(int row, int column);

    public Entity createBomb(int row, int column, GameOverOnly game, int seconds);

    public Modifier createJelly(int row, int column);

    public abstract Entity createRandom(int row, int column);
}
