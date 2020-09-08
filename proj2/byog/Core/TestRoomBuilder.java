package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestRoomBuilder {

    @Test
    public static void TestWallBuilder(String[] args) {
        int WIDTH = 10;
        int HEIGHT = 10;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] Tiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                Tiles[x][y] = Tileset.NOTHING;
            }
        }

        int[] pos = {3, 3};
        RoomBuilder.RoomBuilder(Tiles, 5, 5, pos);
        RoomBuilder.WallBuilder(Tiles, WIDTH, HEIGHT);
        ter.renderFrame(Tiles);
    }

    // Testing Roomconnector and Roombuilder
    // 20,20 to 25,25; 25,25 to 20,20; 15,25 to 25,15; 25,15 to 15,25.

    @Test
    public static void TestRoomConnectorBuilder() {
        int WIDTH = 10;
        int HEIGHT = 10;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] Tiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                Tiles[x][y] = Tileset.NOTHING;
            }
        }

        int[] currPos = {25,15};
        int[] lastPos = {25,15};
        RoomBuilder.RoomBuilder(Tiles, 5, 5, currPos);
        currPos[0] = 15;
        currPos[1] = 25;
        RoomBuilder.RoomConnector(Tiles, lastPos, currPos);
        RoomBuilder.RoomBuilder(Tiles, 5, 5, currPos);

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
    }
}
