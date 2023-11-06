package CandyFactory;

import java.util.Random;

import Entities.Bomb;
import Entities.Candy;
import Entities.Empty;
import Entities.Entity;
import Entities.Glazed;
import Entities.Jelly;
import Entities.MegaStripped;
import Entities.Modifier;
import Entities.Stripped;
import Entities.Wrapped;
import Enums.Colour;
import Interfaces.GameOverOnly;

public abstract class BaseFactory implements CandyFactory {
    private static final Random colourPicker = new Random();

    @Override
    public Entity createWrapped(int row, int column, Colour color) {
        return new Wrapped(row, column, color);
    }

    @Override
    public Entity createHorizontalStripped(int row, int column, Colour color) {
        return new Stripped(row, column, color, true);
    }

    @Override
    public Entity createVerticalStripped(int row, int column, Colour color) {
        return new Stripped(row, column, color, false);
    }

    @Override public Entity createMegaStripped(int row, int column, Colour color) { return new MegaStripped(row, column, color); }
    @Override public Entity createCandy(int row, int column, Colour color)        { return new Candy(row, column, color); }
    @Override public Entity createGlazed(int row, int column)                     { return new Glazed(row, column); }
    @Override public Entity createEmpty(int row, int column)                      { return new Empty(row, column); }

    @Override public Entity createBomb(int row, int column, GameOverOnly game, int seconds) {
        return new Bomb(row, column, game, seconds);
    }

    @Override public Modifier createJelly(int row, int column)                    { return new Jelly(row, column); }

    @Override public abstract Entity createRandom(int row, int column);

    protected Colour randomColour() {
        Colour[] colores = { Colour.BLUE, Colour.GREEN, Colour.PURPLE, Colour.RED, Colour.YELLOW };
        return colores[colourPicker.nextInt(0, colores.length)];
    }
}
