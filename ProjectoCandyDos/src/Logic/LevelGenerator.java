package Logic;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import Combinations.CombinationLogic;
import Combinations.StraightAndL;
import Combinations.Classic;
import Combinations.Straight;
import Combinations.StraightAndT;

import Entities.Entity;
import Entities.Modifier;
import Enums.Colour;

import GUI.Gui;

import CandyFactory.CandyFactory;
import CandyFactory.NormalCandy;

import Interfaces.Equivalent;
import VisualPlayers.Resources;

public class LevelGenerator {
    private static final String levelPath = Resources.getLevelsFolderPath();
    private static final CandyFactory candyFactory = new NormalCandy();

    /**
     * Reads filename, which is comma separated with the following format</br>
     * </br>
     * 
     * (allowed moves)</br>
     * (moves),(time)</br>
     * (entity),(amount of entities),...</br>
     * (entity),(entity),...</br>
     * (entity),(entity),...</br>
     * ..., ..., ...</br>
     * </br>
     * example:</br>
     * S</br>
     * 40,40</br>
     * RS,6, </br>
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
    public static Level generateLevel(String filename, Board board, Game game, Gui gui) {
        List<String> lines = null;
        Level level = null;
        String[] candys = null, obj = null;
        int currentLine = 0;
        char combinationType = 'C';
        lines = readFileLines(levelPath + filename);
        if (lines != null) {
            int aux;
            Equivalent equiAux;
            combinationType = lines.get(currentLine++).charAt(0);
            board.setCombinationLogic(createCombinationLogic(combinationType, board));
            gui.setLevelType("LEVEL TYPE: " + String.valueOf(combinationType));
            candys = lines.get(currentLine++).split(",");
            obj = lines.get(currentLine++).split(",");
            List<Goal> goalListAux = new LinkedList<Goal>();
            for (int i = 0; i < obj.length; i += 2) {
                aux = Integer.valueOf(obj[i + 1]);
                equiAux = createEquivalent(obj[i]);
                goalListAux.add(new Goal(aux, equiAux));
            }
            level = new Level(goalListAux, // Amount of entities to win. (GOAL)
                    Integer.valueOf(candys[0]), // Amount of Moves.
                    Integer.valueOf(candys[1]), Integer.valueOf(filename.charAt(filename.length() - 5)) - '0', // max
                    gui, game);
            // time
            // in
            // SECONDS.
            // Must add first what will be drawn from back to front!. Same for.
            // ADD MODIFIERS TO BLOCKS.
            int temp = currentLine;
            for (int r = 0; r < Board.getRows(); r++) {
                candys = lines.get(currentLine++).split(",");
                for (int c = 0; c < Board.getColumns(); c++) {
                    String id = candys[c];
                    Block block = board.getBlock(r, c);
                    for (int i = 1; i < id.length(); i++)
                        if (id.charAt(i) == 'J') {
                            Modifier jelly = candyFactory.createJelly(r, c);
                            board.addVisualEntity(jelly);
                            block.pushModifier(jelly);
                        }
                }
            }
            currentLine = temp;
            // ADD CANDYS TO BLOCKS.
            for (int r = 0; r < Board.getRows(); r++) {
                candys = lines.get(currentLine++).split(",");
                for (int c = 0; c < Board.getColumns(); c++) {
                    String id = candys[c];
                    Entity entity = createEntity(id, r, c, game);
                    board.associateEntity(r, c, entity);
                }
            }
        }
        return level;
    }

    /**
     * Creates the Equivalent to destroy, that is, the objective
     * 
     * @param id
     * @param r
     * @param c
     * @return
     */
    /**
     * given an {@code id} which represents an Entity in plain text, a row {@code r}
     * and a column {@code c}</br>
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
     * 'M' for GLAZED/MERENGUE</br>
     * </br>
     * second char can be:</br>
     * 'S' for STRIPPED</br>
     * 'W' for WRAPPED</br>
     * 'J' for JELLY</br>
     * </br>
     * 'Z' for MegaStripped</br>
     * </br>
     * 'Q' for BOMB</br>
     * </br>
     * after that, all chars can be 'J' for extra JELLYS
     * 
     * @param r
     * @param c
     * @return
     */
    private static Entity createEntity(String id, int r, int c, Game game) {
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
                    e = candyFactory.createVerticalStripped(r, c, colour);
                else if (id.charAt(1) == 'H')
                    e = candyFactory.createHorizontalStripped(r, c, colour);
                else if (id.charAt(1) == 'W')
                    e = candyFactory.createWrapped(r, c, colour);
                else if (id.charAt(1) == 'S')
                    e = candyFactory.createMegaStripped(r, c, colour);
            }
            if (e == null)
                e = candyFactory.createCandy(r, c, colour);
            break;
        case 'T':
            e = candyFactory.createEmpty(r, c);
            break;
        case 'M':
            e = candyFactory.createGlazed(r, c);
            break;
        case 'Q':
            String temp = String.valueOf(id.toCharArray(), 1, id.length() - 1);
            int seconds = temp.isEmpty() ? 59 : Integer.valueOf(temp);
            e = candyFactory.createBomb(r, c, game, seconds);
            break;
        }
        return e;
    }

    private static Equivalent createEquivalent(String id) {
        Equivalent equivalent = null;
        equivalent = createEntity(id, -1, -1, null); // TODO maybe this isn't the best way to do this
        if (equivalent == null) {
            switch (id.charAt(0)) {
            case 'J':
                equivalent = candyFactory.createJelly(-1, -1);
                break;
            }
        }
        return equivalent;
    }

    /**
     * given a filename, it returns a list of Strings in which every list is a line
     * of the file.
     * 
     * @param filename file name to open and read.
     * @return List of String which every element is a String(line) of the file.
     */
    public static List<String> readFileLines(String filename) {
        List<String> fileText = null;
        try {
            fileText = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println("Could not read file." + Paths.get(filename));
        }
        return fileText;
    }

    public static CombinationLogic createCombinationLogic(char c, Board b) {
        CombinationLogic newCombLogic = null;
        switch (c) {
        case 'S':
            newCombLogic = new Straight(b);
            break;
        case 'T':
            newCombLogic = new StraightAndT(b);
            break;
        case 'L':
            newCombLogic = new StraightAndL(b);
            break;
        default: // 'C'
            newCombLogic = new Classic(b);
            break;
        }
        return newCombLogic;
    }
}
