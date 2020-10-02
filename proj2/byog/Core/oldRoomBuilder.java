package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;

import java.util.Random;

public class oldRoomBuilder {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    public static int[] currPos = {6,6};
    public static int[] lastPos = {6,6};

    /**
     *
     * @param tiles
     */
    public static void WallBuilder(TETile[][] tiles, int WIDTH, int HEIGHT) {
        for (int x = 1; x < WIDTH-1; x++) {
            for (int y = 1; y < HEIGHT-1; y++) {
                if (tiles[x][y] == Tileset.FLOOR) {
                    wallbuilder(tiles, x, y);
                }
            }
        }
    }

    private static void wallbuilder(TETile[][] tiles, int x, int y) {
        for (int i = x-1; i<= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (tiles[i][j] == Tileset.NOTHING) {
                    tiles[i][j] = Tileset.WALL;
                }
            }
        }
    }


    /** This method will build one room or hallway of rectangular shape
     *
     * @param tiles
     * @param sideWidth: length of room/hallway along x axis
     * @param sideHeight: length of room/hallway along y axis
     * @param pos: x,y coordinates of the left top corner (assuming x axis points to the right)
     *
     */
    public static void RoomBuilder(TETile[][] tiles, int sideWidth, int sideHeight, int[] pos) {
        // build the floor within the room and/or hallway
        for (int y = 0; y < sideHeight; y++) {
            for (int x = 0; x < sideWidth; x++) {
                /**
                if (pos[0] + x < 1 || pos[0] + x >= WIDTH-1 || pos[1] + y < 1 || pos[1] + y >= HEIGHT-1) {
                    System.out.println("out of bounds");
                    break;
                }
                 */
                tiles[(pos[0] + x) % (WIDTH - 2) + 1][(pos[1] + y) % (HEIGHT - 2) + 1] = Tileset.FLOOR;
            }
        }
    }

    public static void RandomRooms(TETile[][] tiles) {
        int sidewidth = RANDOM.nextInt(5)+1;
        int sideheight = RANDOM.nextInt(5)+1;

        RoomBuilder(tiles, sidewidth, sideheight, currPos);
    }

    public static void RoomConnector(TETile[][] tiles, int[] lastPos, int[] Pos) {
        if (lastPos.equals(Pos)) {
            return;
        }

        int minX = Math.min(lastPos[0], Pos[0]);
        int maxX = Math.max(lastPos[0], Pos[0]);
        int minY = Math.min(lastPos[1], Pos[1]);
        int maxY = Math.max(lastPos[1], Pos[1]);

        // draw a horizontal floor
        for (int x = 0; x < maxX - minX + 1; x++) {
            tiles[(minX + x) % (WIDTH - 2) + 1][Pos[1] % (HEIGHT - 2) + 1] = Tileset.FLOOR;
        }

        //draw a vertical floor
        for (int y = 0; y < maxY - minY + 1; y++) {
            tiles[lastPos[0] % (WIDTH - 2) + 1][(minY + y) % (HEIGHT - 2) + 1] = Tileset.FLOOR;
        }
    }

    // 0: up; 1: down; 2: right; 3: left
    public static int Direction(int[] Pos) {
        if (Pos[0] <= WIDTH/2.0 && Pos[1] <= HEIGHT/2.0) {
            return 0;
        } else if (Pos[0] <= WIDTH/2.0 && Pos[1] > HEIGHT/2.0){
            return 2;
        } else if (Pos[1] > HEIGHT/2.0) {
            return 1;
        } else {
            return 3;
        }
    }

    public static int[] NextPos(int[] Pos) {
        if (Direction(Pos) == 0) {
            return new int[]{Pos[0] + RANDOM.nextInt(2) - 1, Pos[1] + RANDOM.nextInt(5)};
        } else if (Direction(Pos) == 1) {
            return new int[]{Pos[0] + RANDOM.nextInt(2) - 1, Pos[1] - RANDOM.nextInt(5)};
        } else if (Direction(Pos) == 2) {
            return new int[]{Pos[0] + RANDOM.nextInt(5), Pos[1] + RANDOM.nextInt(2) - 1};
        } else if (Direction(Pos) == 3) {
            return new int[]{Pos[0] - RANDOM.nextInt(5), Pos[1] + RANDOM.nextInt(2) - 1};
        } else {
            return null;
        }
    }

    public static int NextCoord(int x, int boundX) {
        int item = x + RANDOM.nextInt(5);
        while (item > boundX - 6 || item < 6) {
            item = x + RANDOM.nextInt(5);
        }
        return item;
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

        for (int i = 0; i < 20; i++) {
            lastPos[0] = currPos[0];
            lastPos[1] = currPos[1];
            // currPos[0] = currPos[0] + RANDOM.nextInt(10);
            // currPos[1] = currPos[1] + RANDOM.nextInt(10);
            currPos = NextPos(currPos);
            RandomRooms(Tiles);
            RoomConnector(Tiles, lastPos, currPos);
        }

        currPos[0] = 8;
        currPos[1] = 32;
        RoomConnector(Tiles, lastPos, currPos);
        for (int i = 0; i < 20; i++) {
            lastPos[0] = currPos[0];
            lastPos[1] = currPos[1];
            currPos = NextPos(currPos);
            RandomRooms(Tiles);
            RoomConnector(Tiles, lastPos, currPos);
        }


        currPos[0] = 52;
        currPos[1] = 11;
        RoomConnector(Tiles, lastPos, currPos);
        for (int i = 0; i < 20; i++) {
            lastPos[0] = currPos[0];
            lastPos[1] = currPos[1];
            currPos = NextPos(currPos);
            RandomRooms(Tiles);
            RoomConnector(Tiles, lastPos, currPos);
        }

        WallBuilder(Tiles, WIDTH, HEIGHT);
        ter.renderFrame(Tiles);
    }
}
