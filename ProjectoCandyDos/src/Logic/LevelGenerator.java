package Logic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import Entities.Entity;
import Entities.Empty;
import Entities.Colour;
import Entities.Block;
import Entities.Candy;
import Entities.Stripped;
import Entities.Wrapped;
import Entities.Glazed;
import Entities.Jelly;

public class LevelGenerator {
    /**
     * reads filename, which is comma separated with the following format
     * 
     * (String representation of an entity),(Amount of entities to destroy to win),(moves),(time)
     *  (entity),(entity),...
     *  (entity),(entity),...
     *  ..., ..., ...
     *  
     *  example:
     *  RS,6,40,40
     *  R,R,Y,R,R,Y
     *  B,B,R,B,B,P
     *  B,B,Y,B,B,P
     *  Y,Y,R,Y,Y,B
     *  B,B,R,B,B,P
     *  Y,Y,R,Y,Y,B
     * 
     * @param filename
     * @param board
     * @return level {@link Level}
     */
    public static Level generateLevel(String filename, Board board) {
        
        List<String> lines = null;
        Level level = null;
        String[] candys = null;
        
        lines = readFileLines(filename);

        if (lines != null) {
            candys = lines.get(0).split(",");
            level = new Level(
                    createEntity(candys[0], -1, -1), // Entity to compare.
                    Integer.valueOf(candys[1]), // Amount of entities to win. (GOAL) 
                    Integer.valueOf(candys[2]), // Amount of Moves. 
                    Integer.valueOf(candys[3])); // max time in SECONDS.

            for (int r = 0; r < board.getRows(); r++) {
                candys = lines.get(r+1).split(",");
                for (int c = 0; c < board.getColumns(); c++) {
                    String id = candys[r * board.getRows() + c];
                    Block block = board.getBlock(r, c);
                    Entity e = createEntity(id, r, c);
                    for (int i = 1; i < id.length(); i++) if (id.charAt(i) == 'J')
                        block.pushModifier(new Jelly());
                    block.setEntity(e);
                }
            }
        }
        return level;
    }

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

            colour = type=='R' ? Colour.RED :
                     type=='Y' ? Colour.YELLOW :
                     type=='G' ? Colour.GREEN :
                     type=='P' ? Colour.PURPLE :
                                 Colour.BLUE;

            if (id.length() > 1) {
                if (id.charAt(1) == 'S')
                    e = new Stripped(r, c, colour);
                else if (id.charAt(1) == 'W')
                    e = new Wrapped(r, c, colour);
            }
            if (e == null) e = new Candy(r, c, colour);
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
