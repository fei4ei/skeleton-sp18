package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class AttemptFF {

    public static void stringPlay(TETile[][] tiles, int[] pos, String movement, TERenderer ter) {
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

    public static void interactivePlay(TETile[][] tiles, int[] pos, TERenderer ter) {
        int[] currPos = {pos[0], pos[1]};
        int[] lastPos = {pos[0], pos[1]};
        tiles[currPos[0]][currPos[1]] = Tileset.PLAYER;
        ter.renderFrame(tiles);

        char next = ' ';
        while (next != 'q') {
            if (StdDraw.hasNextKeyTyped()) {
                next = StdDraw.nextKeyTyped();
                if (next == 'w') {
                    currPos[1] = (currPos[1] + 1) % 40;
                } else if (next == 's') {
                    currPos[1] = (currPos[1] - 1) % 40;
                } else if (next == 'a') {
                    currPos[0] = (currPos[0] - 1) % 40;
                } else if (next == 'd') {
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
    }

}
