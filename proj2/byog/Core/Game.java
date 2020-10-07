package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class Game {
    /* Feel free to change the width and height. */
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    int indexS;
    int indexColon;

    public Game() {
        indexS = -1;
        indexColon = -1;
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        plotWelcomePage(WIDTH, HEIGHT);

    }

    public static void plotWelcomePage(int W, int H) {
        StdDraw.setCanvasSize(W * 15, H * 15);
        StdDraw.setXscale(0, W);
        StdDraw.setYscale(0, H);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        Font title_font = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(title_font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(W/2.0, H - 5, "CS61B: The GAME");

        Font options_font = new Font("Arial", Font.PLAIN, 30);
        StdDraw.setFont(options_font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(W/2.0 - 2, H/2.0 + 3, "New Game (N)");
        StdDraw.text(W/2.0 - 2, H/2.0, "Load Game (L)");
        StdDraw.text(W/2.0 - 2, H/2.0 - 3, "Quit (Q)");

        StdDraw.show();
        StdDraw.pause(1000);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        input = input.toLowerCase();
        World_n_Player wp = processInitialInput(input);
        TETile[][] world = wp.world;
        int[] pp = wp.PlayerPos;
        updateIndexColon(input);
        movePlayer(world, pp, input.substring(indexS+1, indexColon));
        if (indexColon < input.length() && indexColon > indexS) {
            saveWorld(world);
            // System.exit(0);
        }
        return world;
    }

    private class World_n_Player {
        int[] PlayerPos = {-1, -1};
        TETile[][] world;

        World_n_Player(WorldBuilder wb) {
            world = wb.Tiles;
            PlayerPos = wb.playerPos;
        }

        World_n_Player(TETile[][] tiles) {
            world = tiles;
            PlayerPos = findPlayerPos(tiles);
        }
    }

    private World_n_Player processInitialInput(String input) {
        char first = input.charAt(0);
        // int indexS = -1;
        if (first == 'n') {
            for (int i = 1; i < input.length(); i++) {
                if (input.charAt(i) == 's') {
                    indexS = i;
                    break;
                }
            }
            if (indexS == -1) { // did not find S following N
                return null;
            }

            String seednum = input.substring(1, indexS);  // substring [beginning, ending)
            // int seedNUM = Integer.parseInt(seednum);
            long seedNUM = Long.parseLong(seednum);
            WorldBuilder rb = new WorldBuilder(seedNUM);
            // movePlayer(rb.Tiles, rb.playerPos, input.substring(indexS+1, input.length()-1));
            return new World_n_Player(rb);

        } else if (first == 'l') {
            indexS = 0;
            TETile[][] loadedWorld = loadWorld();
            // int[] playerPos = findPlayerPos(loadedWorld);
            // movePlayer(loadedWorld, playerPos, input.substring(1, input.length()-1));
            return new World_n_Player(loadedWorld);

        } else if (first == 'q') {
            System.exit(0);
            return null; // need updates

        } else {
            System.out.println("invalid input");
            return null;
        }
    }

    private TETile[][] processFirstInput(String input) {
        char first = input.charAt(0);

        if (first == 'N') {
            for (int i = 1; i < input.length(); i++) {
                if (input.charAt(i) == 'S') {
                    indexS = i;
                    break;
                }
            }
            if (indexS == -1) { // did not find S following N
                return null;
            }

            String seednum = input.substring(1, indexS);  // substring [beginning, ending)
            // int seedNUM = Integer.parseInt(seednum);
            long seedNUM = Long.parseLong(seednum);
            WorldBuilder rb = new WorldBuilder(seedNUM);
            // movePlayer(rb.Tiles, rb.playerPos, input.substring(indexS+1, input.length()-1));
            return rb.Tiles;

        } else if (first == 'L') {
            TETile[][] loadedWorld = loadWorld();
            indexS = 0;
            // int[] playerPos = findPlayerPos(loadedWorld);
            // movePlayer(loadedWorld, playerPos, input.substring(1, input.length()-1));
            return loadedWorld;

        } else if (first == 'Q') {
            System.exit(0);
            return null; // need updates

        } else {
            return null;
        }
    }

    // pos is the current position of the player
    public static void movePlayer (TETile[][] tiles, int[] pos, String movement) {
        // create a player
        int[] currPos = {pos[0], pos[1]};
        int[] lastPos = {pos[0], pos[1]};
        if (!tiles[currPos[0]][currPos[1]].equals(Tileset.PLAYER)) {
            System.out.println("Player is not found.");
            return;
        }
        for (int i = 0; i < movement.length(); i++) {
            moveOneStep(tiles, currPos, movement.charAt(i));
            // currPos is modified by moveOneStep(); so no need to assign a newPos variable
            if (!Arrays.equals(currPos, lastPos)) {
                // currPos[0] = newPos[0];
                // currPos[1] = newPos[1];
                tiles[currPos[0]][currPos[1]] = Tileset.PLAYER;
                tiles[lastPos[0]][lastPos[1]] = Tileset.FLOOR;
                lastPos[0] = currPos[0];
                lastPos[1] = currPos[1];
            }
        }
    }

    private static int[] moveOneStep(TETile[][] tiles, int[] pos, char onestep) {
        switch (onestep) {
            case 'w':
                if (tiles[pos[0]][pos[1]+1].equals(Tileset.FLOOR)) {
                    pos[1] = (pos[1] + 1);
                }
                break;
            case 's':
                if (tiles[pos[0]][pos[1]-1].equals(Tileset.FLOOR)) {
                    pos[1] = pos[1] - 1;
                }
                break;
            case 'a':
                if (tiles[pos[0]-1][pos[1]].equals(Tileset.FLOOR)) {
                    pos[0] = pos[0] - 1;
                }
                break;
            case 'd':
                if (tiles[pos[0]+1][pos[1]].equals(Tileset.FLOOR)) {
                    pos[0] = pos[0] + 1;
                }
                break;
            /**
            case 'q':
                saveWorld(tiles);
                System.exit(0);
                break;
             */
            default:
        }
        return pos;
    }

    // There must be a better way to do this. Maybe save the PlayPos + Tiles as a new class
    private static int[] findPlayerPos(TETile[][] Tiles) {
        int x = -1;
        int y = -1;
        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                if (Tiles[i][j].equals(Tileset.PLAYER)) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        return new int[]{x,y};
    }

    private void updateIndexColon(String input) {
        indexColon = input.length();
        for (int i = indexS + 1; i < input.length() - 1; i++) {
            if (input.charAt(i) == ':' && input.charAt(i+1) == 'q') {
                indexColon = i;
                break;
            }
        }
    }

    // saveWorld and loadWorld needed to be updated to handle World_n_Player type, rather than TETile[][]
    private static void saveWorld(TETile[][] w) {
        File f = new File("./history.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    /* Save and load TETile[][] object (e.g., WorldBuilder instance.Tiles);
    No need to save/load the whole WorldBuilder class.
     */
    private static TETile[][] loadWorld() {
        File f = new File("./history.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                TETile[][] loadWorld = (TETile[][]) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return null; // need to revise
    }

    // for each class, use the main function to debug
    public static void main(String[] args) {
        /** test plotWelcomePage()
        // plotWelcomePage(60, 40);
         */

        /** test findPlayerPos() and movePlayer() for new games
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Game game = new Game();
        TETile[][] Tiles = processFirstInput("N45S");
        int[] actual = {3,13};
        int[] found = findPlayerPos(Tiles);
        System.out.println(found[0] + ", " + found[1]);
        boolean testNew = Arrays.equals(actual, found);
         // note that actual.equals(found) will be false
        System.out.println(testNew);
        movePlayer(Tiles, actual, "dwww");
        ter.renderFrame(Tiles);
        */

        /** test findPlayerPos() and movePlayer() after loading and after creating a new world
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Game game = new Game();
        TETile[][] Tiles = game.processFirstInput("N45S");
        saveWorld(Tiles);

        TETile[][] loadedWORLD = loadWorld();
        int[] loaded = findPlayerPos(loadedWORLD);
        System.out.println(loaded[0] + ", " + loaded[1]);
        int[] actual = {3,13};
        boolean testLoaded = Arrays.equals(actual, loaded);

        movePlayer(loadedWORLD, loaded, "dsss");
        ter.renderFrame(loadedWORLD);
         */

        /** Test the world using a world with one tile of player and the rest floor.
        TETile[][] testTiles = new TETile[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                testTiles[i][j] = Tileset.FLOOR;
            }
        }
        testTiles[0][2] = Tileset.PLAYER;
        saveWorld(testTiles);
        ter.renderFrame(Tiles);

        TETile[][] fromloaded = loadWorld();
        int[] tp = findPlayerPos(fromloaded);
        System.out.println(tp[0] + ", " + tp[1]);
        movePlayer(fromloaded, tp, "dwww");
        ter.renderFrame(fromloaded);
         */

        /** test moveOneStep
        WorldBuilder rb = new WorldBuilder(45);
        System.out.println(rb.playerPos[0]);
        System.out.println(rb.playerPos[1]);
        moveOneStep(rb.Tiles, rb.playerPos, 's');
        System.out.println(rb.playerPos[0]);
        System.out.println(rb.playerPos[1]);
         */

        /** test saveWorld and loadWorld
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] Tiles = processFirstInput("N45S");
        ter.renderFrame(rb.Tiles);
        saveWorld(Tiles);
        TETile[][] loadedWORLD = loadWorld();
        ter.renderFrame(loadedWORLD);
         */

        /** test characters other than numbers as seeds
        // long n = Long.parseLong("1ac2");
         */

         //test playWithInputString without :q
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Game game = new Game();
        // game.playWithInputString("N45Sdsss:q");
        // TETile[][] loadedWorld = loadWorld();
        // TETile[][] loaded_world = game.playWithInputString("Laaww"); // "L" is a good special case
        TETile[][] loaded_world = game.playWithInputString("Laaww");
        ter.renderFrame(loaded_world);


        /** // test updateIndexColon
        Game game = new Game();
        System.out.println(game.indexColon);
        game.updateIndexColon("N43S:q");
        System.out.println(game. indexColon);
         */
    }
}