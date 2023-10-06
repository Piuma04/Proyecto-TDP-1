package Logic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import Entities.Entity;
import Entities.Empty;
import Entities.Colour;
import Entities.Candy;
import Entities.Stripped;
import Entities.Wrapped;
import Entities.Glazed;
import Entities.Jelly;

public class LevelGenerator {
    /**
     * reads filename, which is comma separated with the following format</br></br>
     * 
     * (entity),(amount of entities),(moves),(time)</br>
     * (entity),(entity),...</br>
     * (entity),(entity),...</br>
     *  ..., ..., ...</br></br>
     * example:</br>
     * RS,6,40,40</br>
     * R,R,Y,R,R,Y</br>
     * B,B,R,B,B,P</br>
     * B,B,Y,B,B,P</br>
     * Y,Y,R,Y,Y,B</br>
     * B,B,R,B,B,P</br>
     * Y,Y,R,Y,Y,B
     * 
     * @param filename name of the file
     * @param board {@link Board} object to initialize matrix.
     * @return level {@link Level} object initialized.
     */
    public static Level generateLevel(String filename, Board board) {

        List<String> lines = null;
        Level level = null;
        String[] candys = null;

        lines = readFileLines(filename);

        if (lines != null) {
            candys = lines.get(0).split(",");
            level = new Level(createEntity(candys[0], -1, -1), // Entity to compare.
                    Integer.valueOf(candys[1]), // Amount of entities to win. (GOAL)
                    Integer.valueOf(candys[2]), // Amount of Moves.
                    Integer.valueOf(candys[3]), Integer.valueOf(filename.charAt(filename.length()-5))-'0'); // max time in SECONDS.

            for (int r = 0; r < Board.getRows(); r++) {
                candys = lines.get(r + 1).split(",");
                for (int c = 0; c < Board.getColumns(); c++) {
                    String id = candys[c];
                    Block block = board.getBlock(r, c);
                    Entity entity = createEntity(id, r, c);
                    for (int i = 1; i < id.length(); i++)
                        if (id.charAt(i) == 'J')
                            block.pushModifier(new Jelly());
                    board.associateEntity(r, c, entity);
                }
            }
        }
        return level;
    }

    /**
     * given an {@code id} which represents an Entity in plain text, a row {@code r} and a column {@code c}</br>
     * creates an entity with position {@code (r, c)}.
     * 
     * @param id String with the following format</br>
     * first char can be:</br>
     * 'R' for RED</br>
     * 'Y' for YELLOW</br>
     * 'G' for GREEN</br>
     * 'P' for PURPLE</br>
     * 'B' for BLUE</br>
     * 'T' for EMPTY/TRANSPARENT/NONE</br>
     * 'M' for GLAZED/MERENGUE</br></br>
     * second char can be:</br>
     * 'S' for STRIPPED</br>
     * 'W' for WRAPPED</br>
     * 'J' for JELLY</br></br>
     * after that, all chars can be 'J' for extra JELLYS
     * 
     * @param r
     * @param c
     * @return
     */
    private static Entity createEntity(String id, int r, int c) {

        Entity e = null;
        Colour colour = null;
        char type = id.charAt(0);

        switch (type) {
        case 'R':
        case 'Y':
        case 'G':
        case 'P':
        case 'B':

            colour = type == 'R' ? Colour.RED
                    : type == 'Y' ? Colour.YELLOW
                            : type == 'G' ? Colour.GREEN : type == 'P' ? Colour.PURPLE : Colour.BLUE;

            if (id.length() > 1) {
                if (id.charAt(1) == 'V')
                    e = new Stripped(r, c, colour, false);
                else if (id.charAt(1) == 'H')
                    e = new Stripped(r, c, colour, true);
                else if (id.charAt(1) == 'W')
                    e = new Wrapped(r, c, colour);
            }
            if (e == null)
                e = new Candy(r, c, colour);
            break;
        case 'T':
            e = new Empty(r, c);
            break;
        case 'M':
            e = new Glazed(r, c);
            break;
        }
        return e;
    }

    /**
     * given a filename, it returns a list of Strings in which every list is a line of the file.
     * @param filename file name to open and read.
     * @return List of String which every element is a String(line) of the file.
     */
    private static List<String> readFileLines(String filename) {
        List<String> fileText = null;
        try {
            fileText = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println("Could not read file." + Paths.get(filename));
        }
        return fileText;
    }
}
