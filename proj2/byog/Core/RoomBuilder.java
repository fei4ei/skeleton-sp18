package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static edu.princeton.cs.algs4.StdDraw.setCanvasSize;

public class RoomBuilder {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    // public static HashSet FloorsCoord = new HashSet();
    public static int[] currPos = {20,20};
    public static int[] lastPos = {25,25};



    /** This method will build one room or hallway of rectangular shape
     *
     * @param tiles
     * @param sideWidth: length of room/hallway along x axis
     * @param sideHeight: length of room/hallway along y axis
     * @param pos: x,y coordinates of the left top corner (assuming x axis points to the right)
     *
     */
    private static void RoomBuilder(TETile[][] tiles, int sideWidth, int sideHeight, int[] pos) {
        // build the floor within the room and/or hallway
        for (int y = 1; y < sideHeight-1; y++) {
            for (int x = 1; x < sideWidth-1; x++) {
                if (pos[0] + x < 0 || pos[0] + x >= WIDTH || pos[1] + y < 0 || pos[1] + y >= HEIGHT) {
                    continue;
                }
                tiles[pos[0] + x][pos[1] + y] = Tileset.FLOOR;
            }
        }

        // build the wall surrounding the room and/or hallway.
        // TODO: ensure x,y coordiates of walls are between 0-WIDTH for x and 0-HEIGHT for y
        for (int x = 0; x < sideWidth; x++) {
            if (tiles[pos[0] + x][pos[1]] == Tileset.FLOOR) {
                continue;
            }
            tiles[pos[0] + x][pos[1]] = Tileset.WALL;
        }
        for (int x = 0; x < sideWidth; x++) {
            if (tiles[pos[0] + x][pos[1] + sideHeight - 1] == Tileset.FLOOR) {
                continue;
            }
            tiles[pos[0] + x][pos[1] + sideHeight - 1] = Tileset.WALL;
        }

        for (int y = 0; y < sideHeight; y++) {
            if (tiles[pos[0]][pos[1] + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[pos[0]][pos[1] + y] = Tileset.WALL;
        }

        for (int y = 0; y < sideHeight; y++) {
            if (tiles[pos[0] + sideWidth - 1][pos[1] + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[pos[0] + sideWidth - 1][pos[1] + y] = Tileset.WALL;
        }
    }

    public static void RandomRooms(TETile[][] tiles) {
        int sidewidth = RANDOM.nextInt(7)+3;
        int sideheight = RANDOM.nextInt(7)+3;

        RoomBuilder(tiles, sidewidth, sideheight, currPos);
    }

    private static void RoomConnector(TETile[][] tiles, int[] lastPos, int[] Pos) {
        if (lastPos.equals(Pos)) {
            return;
        }

        int minX = Math.min(lastPos[0], Pos[0]);
        int maxX = Math.max(lastPos[0], Pos[0]);
        int minY = Math.min(lastPos[1], Pos[1]);
        int maxY = Math.max(lastPos[1], Pos[1]);

        // draw a horizontal floor, sandwiched by walls
        for (int x = 0; x < maxX - minX + 1; x++) {
            tiles[minX + x + 1][Pos[1]] = Tileset.FLOOR;
        }
        if (tiles[minX][Pos[1]]!=Tileset.FLOOR) {
            tiles[minX][Pos[1]] = Tileset.WALL;
        }
        if (tiles[minX + maxX - minX + 2][Pos[1]]!=Tileset.FLOOR) {
            tiles[minX + maxX - minX + 2][Pos[1]] = Tileset.WALL;
        }

        for (int x = -1; x < maxX - minX + 2; x++) {
            if (tiles[minX + x + 1][Pos[1] - 1] == Tileset.FLOOR) {
                continue;
            }
            tiles[minX + x + 1][Pos[1] - 1] = Tileset.WALL;
        }
        for (int x = -1; x < maxX - minX + 2; x++) {
            if (tiles[minX + x + 1][Pos[1] + 1] == Tileset.FLOOR) {
                continue;
            }
            tiles[minX + x + 1][Pos[1] + 1] = Tileset.WALL;
        }


        //draw a vertical floor, sandwiched by walls
        for (int y = 0; y < maxY - minY + 1; y++) {
            if (tiles[lastPos[0] + 1][minY + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[lastPos[0] + 1][minY + y] = Tileset.FLOOR;
        }

        if (tiles[lastPos[0] + 1][minY - 1] != Tileset.FLOOR) {
            tiles[lastPos[0] + 1][minY - 1] = Tileset.WALL;
        }
        if (tiles[lastPos[0] + 1][minY + maxY - minY + 1] != Tileset.FLOOR) {
            tiles[lastPos[0] + 1][minY + maxY - minY + 1] = Tileset.WALL;
        }

        for (int y = -1; y < maxY - minY + 2; y++) {
            if (tiles[lastPos[0]][minY + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[lastPos[0]][minY + y] = Tileset.WALL;
        }

        for (int y = -1; y < maxY - minY + 2; y++) {
            if (tiles[lastPos[0] + 2][minY + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[lastPos[0] + 2][minY + y] = Tileset.WALL;
        }
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

        // Testing Roomconnector and Roombuilder
        // 20,20 to 25,25; 25,25 to 20,20; 15,25 to 25,15; 25,15 to 15,25.
        /**
        currPos[0] = 25;
        currPos[1] = 15;
        RoomBuilder(Tiles, 5, 5, currPos);
        currPos[0] = 15;
        currPos[1] = 25;
        lastPos[0] = 25;
        lastPos[1] = 15;
        RoomConnector(Tiles, lastPos, currPos);
        RoomBuilder(Tiles, 5, 5, currPos);
        */

        for (int i = 0; i < 20; i++) {
            lastPos[0] = currPos[0];
            lastPos[1] = currPos[1];
            currPos[0] = RANDOM.nextInt(55);
            currPos[1] = RANDOM.nextInt(35);
            RandomRooms(Tiles);
            RoomConnector(Tiles, lastPos, currPos);
        }

        // Testing RoomConnector: from {10,10} to {20, 20}, from {20, 20} to {10, 10},
        // from {10, 20} to {20, 10}, and from {20, 10} to {10, 20}.

        /**
        int[] lastPos = {30,7};
        int[] Pos = {7, 30};
        RoomConnector(Tiles, lastPos, Pos);

        //{3,40} will crash the program. Why?
        int[] lastPos1 = {3,34};
        int[] Pos1 = {34, 3};
        RoomConnector(Tiles, lastPos1, Pos1);

        int[] lastPos2 = {10,10};
        int[] Pos2 = {30, 30};
        RoomConnector(Tiles, lastPos2, Pos2);

        int[] lastPos3 = {25,25};
        int[] Pos3 = {15, 15};
        RoomConnector(Tiles, lastPos3, Pos3);

        int[] lastPos4 = {37,5};
        int[] Pos4 = {37, 25};
        RoomConnector(Tiles, lastPos4, Pos4);

        int[] lastPos5 = {5,35};
        int[] Pos5 = {30, 35};
        RoomConnector(Tiles, lastPos5, Pos5);
         */

        ter.renderFrame(Tiles);
    }
}
