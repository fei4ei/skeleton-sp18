package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /** Add a hexagon of side length. Only part of the hexagon with x between [0, WIDTH)
     * and y between [0, HEIGHT) will be depicted
     * @param sideLength: number of tiles for each side.
     * @param cornerX and cornerY: X and Y axis of the tile at the left top corner.
     */
    public static void addHexagon(TETile[][] tiles, int sideLength, int cornerX, int cornerY, TETile t) {
        // top half. assuming x axis points to the right and y axis points downward
        for (int y = 0; y < sideLength; y++) {
            for (int x = 0; x < sideLength + 2*y; x++) {
                if (cornerX - y + x < 0 || cornerX - y + x >= WIDTH || cornerY + y < 0 || cornerY + y >= HEIGHT) {
                    continue;
                }
                tiles[cornerX - y + x][cornerY + y] = t;
            }
        }
        /** (x,y) of the bottom half's leftmost tile
        int bottomX = cornerX - (sideLength-1);
        int bottomY = cornerY + sideLength;
         */

        //(x,y) of the bottom left bottom corner
        // int bottomX = cornerX;
        int bottomY = cornerY + 2*sideLength - 1;
        // bottom half
        for (int y = 0; y < sideLength; y++) {
            for (int x = 0; x < sideLength + 2*y; x++) {
                if (cornerX - y + x < 0 || cornerX - y + x >= WIDTH || bottomY - y < 0 || bottomY - y >= HEIGHT) {
                    continue;
                }
                tiles[cornerX - y + x][bottomY - y] = t;
            }
        }

        /**
        // top half. layer 0
        for (int i = 0; i < sideLength; i++) {
            tiles[cornerX+i][cornerY] = drawWall();
        }

        // layer 1
        for (int i = 0; i < sideLength + 2; i++) {
            tiles[cornerX - 1 + i][cornerY + 1] = drawWall();
        }
         */
    }

    public static void CatanMaker(TETile[][] tiles) {
        int cornerX1 = WIDTH/2 - 1;
        int cornerY1 = 10;
        for (int y = 0; y < 5; y++) {
            addHexagon(tiles, 3, cornerX1, cornerY1+y*6, Tileset.WALL);
        }
        for (int y = 0; y < 4; y++) {
            addHexagon(tiles, 3, cornerX1-5, cornerY1+3+y*6, randomTile());
        }
        for (int y = 0; y < 4; y++) {
            addHexagon(tiles, 3, cornerX1+5, cornerY1+3+y*6, randomTile());
        }
        for (int y = 0; y < 3; y++) {
            addHexagon(tiles, 3, cornerX1-10, cornerY1+6+y*6, randomTile());
        }
        for (int y = 0; y < 3; y++) {
            addHexagon(tiles, 3, cornerX1+10, cornerY1+6+y*6, randomTile());
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5); // return int number between 0-2
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            case 3: return Tileset.TREE;
            case 4: return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }

    public static TETile drawWall() {
        return Tileset.WALL;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] Tiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                Tiles[x][y] = Tileset.NOTHING;
            }
        }

        /**
        addHexagon(Tiles, 4, 10, 10, Tileset.WALL);
        addHexagon(Tiles, 10, 25, 25, Tileset.FLOWER);
        addHexagon(Tiles, 5, 41, 41, Tileset.GRASS);
         */
        CatanMaker(Tiles);

        ter.renderFrame(Tiles);
    }
}
