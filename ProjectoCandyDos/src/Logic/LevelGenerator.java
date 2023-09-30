package Logic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Dictionary;

import Entities.Entity;
import Entities.Colour;
import Entities.Block;
import Entities.Candy;
import Entities.Stripped;
import Entities.Wrapped;
import Entities.Glazed;
import Entities.Jelly;

public class LevelGenerator {
    public static void generateLevel(String filename, Board board) {
        String fileText = null;
        String[] candys = null;
        try {
            fileText = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
        }

        if (fileText != null) {
            candys = fileText.split(",");
            for (int r = 0; r < board.getRows(); r++) {
                for (int c = 0; c < board.getColumns(); c++) {
                    //board.getBlock(r, c).setEntity(createEntity(candys[r * board.getRows() + c], r, c));
                    createEntity(board.getBlock(r, c), candys[r * board.getRows() + c], r, c));
                }
            }
        }
    }

    private static void createEntity(Block block, String id, int r, int c) {

        /*
         * { 'R', // RED 'Y', // YELLOW 'G', // GREEN 'P', // PURPLE 'B', // BLUE 'T',
         * // TRANSPARENT 'G', // GLAZED }
         */

        final char[] CANDY_TYPE = {
                'S', // STRIPPED
                'W', // WRAPPED
                'J' // JELLY
        };

        Entity e = null;
        char type = id.charAt(0);
        Colour colour = null;

        switch (type) {
        case 'R':
        case 'Y':
        case 'G':
        case 'P':
        case 'B':

            colour = type=='R' ? Colour.RED :
                     type=='Y' ? Colour.YELLOW :
                     type=='G' ? Colour.GREEN :
                     type=='P' ? Colour.PURPLE :
                                 Colour.BLUE;

            if (id.length() > 1) {
                if (id.charAt(1) == CANDY_TYPE[0])
                    e = new Stripped(r, c, colour);
                else if (id.charAt(1) == CANDY_TYPE[1])
                    e = new Wrapped(r, c, colour);
            }
            if (e == null) e = new Candy(r, c, colour);
            break;
        case 'T':
            e = new Candy(r, c, Colour.TRANSPARENT);
            break;
        case 'M':
             e = new Glazed(r, c, Colour.TRANSPARENT);
            break;
        }
        for (int i = 1; i < id.length(); i++) if (id.charAt(i) == CANDY_TYPE[2])
            block.pushModifier(new Jelly());

    }
}
