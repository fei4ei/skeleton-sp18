package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestRoomBuilder {

    public static void main(String[] args) {
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

        int[] pos = {3,3};
        RoomBuilder.RoomBuilder(Tiles,5,5,pos);
        RoomBuilder.WallBuilder(Tiles, WIDTH, HEIGHT);
        ter.renderFrame(Tiles);

    }
}
