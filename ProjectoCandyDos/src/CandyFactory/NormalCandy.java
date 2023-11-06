package CandyFactory;

import Entities.Candy;
import Entities.Entity;
public class NormalCandy extends BaseFactory {

    @Override
    public Entity createRandom(int row, int column) { return new Candy(row, column, randomColour());  }

}
