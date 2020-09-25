package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;
import java.util.Arrays;

import static byog.Core.RoomBuilder1.WallBuilder;
import static byog.Core.RoomBuilder1.PointConnector;
import static byog.Core.RoomBuilder1.RoomBuilder;
import static byog.Core.RoomBuilder1.randomX;
import static byog.Core.RoomBuilder1.randomY;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static int width = 54;
    private static int height = 34;


    public String CharsInput() {
        //TODO: Read n letters of player input
        StringBuilder returnStr = new StringBuilder();
        while (true) { // cannot use for loop here
            if (!StdDraw.hasNextKeyTyped()) {
                break;
            }
            char next = StdDraw.nextKeyTyped(); // nextKeyTyped() returns the key as a char
            returnStr.append(next);
        }
        return returnStr.toString();
    }

    public static void interactivePlay(TETile[][] tiles, int[] pos, String movement, TERenderer ter) {
        // create a player
        int[] currPos = {pos[0], pos[1]};
        int[] lastPos = {pos[0], pos[1]};
        tiles[currPos[0]][currPos[1]] = Tileset.PLAYER;
        for (int i = 0; i < movement.length(); i++) {
            if (movement.charAt(i) == 'w') {
                currPos[1] = (currPos[1] + 1) % 40;
            } else if (movement.charAt(i) == 's') {
                currPos[1] = (currPos[1] - 1) % 40;
            } else if (movement.charAt(i) == 'a') {
                currPos[0] = (currPos[0] - 1) % 40;
            } else if (movement.charAt(i) == 'd') {
                currPos[0] = (currPos[0] + 1) % 40;
            }
            tiles[currPos[0]][currPos[1]] = Tileset.PLAYER;
            tiles[lastPos[0]][lastPos[1]] = Tileset.FLOOR;
            ter.renderFrame(tiles);
            StdDraw.pause(1000);
            lastPos[0] = currPos[0];
            lastPos[1] = currPos[1];
        }
    }


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
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
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        // char first = input.charAt(0);
        // char last = input.charAt(input.length()-1);
        // substring [beginning, ending)
        String seednum = input.substring(1,input.length()-1);
        int seedNUM = Integer.parseInt(seednum);

        RoomBuilder1 rb = new RoomBuilder1(seedNUM);
        Random RANDOM = new Random(seedNUM);
        int num = 20;
        int[] x = randomX(width, num);
        int[] y = randomY(height, num);

        for (int i = 1; i < num; i++) {
            int[] lastPos = {x[i - 1], y[i - 1]};
            int[] currPos = {x[i], y[i]};
            PointConnector(rb.Tiles, lastPos, currPos);
            int sideH = RANDOM.nextInt(5)+1;
            int sideW = RANDOM.nextInt(5)+1;
            RoomBuilder(rb.Tiles, sideH, sideW, currPos);
        }

        WallBuilder(rb.Tiles, WIDTH, HEIGHT);
        rb.ter.renderFrame(rb.Tiles);
        return rb.Tiles;
    }

    public static void main(String[] args) {
        Game game = new Game();
        TETile[][] world = game.playWithInputString("N2366S");

    }
}
