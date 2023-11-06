package CandyFactory;

import java.util.Random;

import Entities.Entity;

public class SweetCandy extends BaseFactory {

    private static final Random candyPicker = new Random();
    private double p;

    /**
     * @param probabilityMegastripped integer between 0 and 100 representing probability of creation.
     */
    public SweetCandy(int probabilityMegastripped) {
        p = probabilityMegastripped <= 0 ? 0 : probabilityMegastripped / 100; 
    }

    @Override
    public Entity createRandom(int row, int column) {
        Entity candy = null;
        if (candyPicker.nextDouble() < p)
            candy = createMegaStripped(row, column, randomColour());
        else
            candy = createCandy(row, column, randomColour());
        return candy;
    }
    
}
