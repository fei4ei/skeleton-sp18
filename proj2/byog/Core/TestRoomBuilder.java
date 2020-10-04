package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestRoomBuilder {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    @Test
    public void testPlotWelcomePage() {
        Game.plotWelcomePage(60, 40);
    }

    @Test
    public void testrandomX() {
        WorldBuilder rb = new WorldBuilder(1000);
        int[] actual = rb.randomX(20, 10);
        System.out.println(Arrays.toString(actual));
        assertTrue(actual[0] <= actual[1]);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] Tiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                Tiles[x][y] = Tileset.GRASS;
            }
        }

        /*
        // test for PointerConnector
        int[] currPos = {3,7};
        int[] lastPos = {7,3};
        RoomBuilder.PointConnector(Tiles, lastPos, currPos);

        int[] currPos1 = {18,14};
        int[] lastPos1 = {14,18};
        RoomBuilder.PointConnector(Tiles, lastPos1, currPos1);
         */

        int[] pos = {9,9};
        String dir = "wddssa";
        // AttemptFF.stringPlay(Tiles, pos, dir, ter);
        AttemptFF.interactivePlay(Tiles, pos, ter);
        ter.renderFrame(Tiles);
    }

}
