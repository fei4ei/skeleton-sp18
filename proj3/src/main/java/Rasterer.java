import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        // System.out.println(params);
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double h = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        boolean query_success = false;

        double[] pngres = pngRes(); // generate an array of resolutions for different levels of png images
        for (int i = 0; i < pngres.length; i++) {
            System.out.println("res at png level " + (i+1) + ": " + pngres[i]);
        }

        double userres = userRes(ullon, lrlon, w); // calculate the resolution in the user query
        System.out.println("user res: " + userres);

        int level = levelFinder(userres, pngres); // calculate the level of the png images needed for the user query
        System.out.println("level: " + level);

        int x1 = x1Num(ullon, level);
        int x2 = x2Num(lrlon, level);
        int y1 = y1Num(ullat, level);
        int y2 = y2Num(lrlat, level);

//        String[][] render_grid = new String[][]{{pngFinder(0, 0, level)}};
        String[][] render_grid = gridGenerator(x1, x2, y1, y2, level);
        query_success = true;

        double raster_ul_lon = MapServer.ROOT_ULLON +
                Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) * x1 / Math.pow(2, level-1);
        double raster_lr_lon = MapServer.ROOT_ULLON +
                Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) * (x2+1) / Math.pow(2, level-1);
        double raster_ul_lat = MapServer.ROOT_ULLAT -
                Math.abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) * y1 / Math.pow(2, level-1);
        double raster_lr_lat = MapServer.ROOT_ULLAT -
                Math.abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) * (y2+1) / Math.pow(2, level-1);

        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", level-1);
        results.put("query_success", query_success);
        return results;
    }

    private double[] pngRes() {
        // a factor of 2 better image resolution as the level goes higher
//        double resl1 = Math.abs(-122.2998046875 + 122.2119140625) / 256; // level 1: d0_x0_y0.png
//        double resl2 = Math.abs(-122.2998046875 + 122.255859375) / 256; // level 2: d1_x0_y0.png
//        double resl3 = Math.abs(-122.2998046875 + 122.27783203125) / 256; // level 3: d2_x0_y0.png
//        double resl4 = Math.abs(-122.2998046875 + 122.288818359375) / 256; // level 4: d3_x0_y0.png
//        double resl5 = Math.abs(-122.2998046875 + 122.2943115234375) / 256; // level 5: d4_x0_y0.png
//        double resl6 = Math.abs(-122.2998046875 + 122.29705810546875) / 256; // level 6: d5_x0_y0.png
//        double resl7 = Math.abs(-122.2998046875 + 122.29843139648438) / 256; // level 7: d6_x0_y0.png
//        double resl8 = Math.abs(-122.2998046875 + 122.29911804199219) / 256; // level 8: d7_x0_y0.png

        double resl1 = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) / MapServer.TILE_SIZE;
        double resl2 = resl1/2.0;
        double resl3 = resl2/2.0;
        double resl4 = resl3/2.0;
        double resl5 = resl4/2.0;
        double resl6 = resl5/2.0;
        double resl7 = resl6/2.0;
        double resl8 = resl7/2.0;

        return new double[]{resl1, resl2, resl3, resl4, resl5, resl6, resl7, resl8};
//        res at png level 1: 3.4332275390625E-4
//        res at png level 2: 1.71661376953125E-4
//        res at png level 3: 8.58306884765625E-5
//        res at png level 4: 4.291534423828125E-5
//        res at png level 5: 2.1457672119140625E-5
//        res at png level 6: 1.0728836059570312E-5
//        res at png level 7: 5.364418029785156E-6
//        res at png level 8: 2.682209014892578E-6
    }

    private double userRes(double ullon, double lrlon, double w) {
        double resolution = Math.abs(lrlon - ullon) / w;
        return resolution;
    }

    private int levelFinder(double userres, double[] pngres) {
        int pngLevel = 0;
        if (userres < pngres[pngres.length-1]) {
            pngLevel = pngres.length;
        } else {
            for (int i = 0; i < pngres.length; i++) {
                if (userres < pngres[i]) {
                    continue;
                }
                pngLevel = i + 1;// level from 1 to 8 while files names from d0 to d7
                break;
            }
        }
        return pngLevel;
    }

    private static int x1Num(double ullon, int level) {
        if (ullon <= MapServer.ROOT_ULLON) { // user requested urlon is outside the MapServer-provided images
            System.out.println("x1 num: 0");
            return 0;
        }
        double width_root = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON);
        double x1_user = Math.abs(MapServer.ROOT_ULLON - ullon);
        int result = (int) ((x1_user/width_root)*Math.pow(2, level-1)); // x1_user/width_root / 0.25 for level 3
        System.out.println("root width: " + width_root + "; x1_user: " + x1_user + "; x1 num: " + result);
        return result;
    }

    private static int x2Num(double lrlon, int level) {
        if (lrlon >= MapServer.ROOT_LRLON) { // user requested urlon is outside the MapServer-provided images
            int x2 = (int) (Math.pow(2, level-1) - 1);
            System.out.println("x2 number: " + x2);
            return x2;
        }
        double width_root = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON);
        double x2_user = Math.abs(MapServer.ROOT_ULLON - lrlon);
        int result = (int) ((x2_user/width_root)*Math.pow(2, level-1)); // x2_user/width_root / 0.25 for level 3
        System.out.println("root width: " + width_root + "; x2_user: " + x2_user + "; x2 num: " + result);
        return result;
    }

    private static int y1Num(double ullat, int level) {
        if (ullat >= MapServer.ROOT_ULLAT) { // user requested urlon is outside the MapServer-provided images
            System.out.println("y1 num: 0");
            return 0;
        }
        double height_root = Math.abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        double y1_user = Math.abs(MapServer.ROOT_ULLAT - ullat);
        int result = (int) ((y1_user/height_root)*Math.pow(2, level-1)); // y1_user/height_root / 0.25 for level 3
        System.out.println("root height: " + height_root + "; y1_user: " + y1_user + "; y1 num: " + result);
        return result;
    }

    private static int y2Num(double lrlat, int level) {
        if (lrlat <= MapServer.ROOT_LRLAT) { // user requested urlon is outside the MapServer-provided images
            int y2 = (int) (Math.pow(2, level-1) - 1);
            System.out.println("y2 number: " + y2);
            return y2;
        }
        double height_root = Math.abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT);
        double y2_user = Math.abs(MapServer.ROOT_ULLAT - lrlat);
        int result = (int) ((y2_user/height_root)*Math.pow(2, level-1)); // y1_user/height_root / 0.25 for level 3
        System.out.println("root height: " + height_root + "; y2_user: " + y2_user + "; y2 num: " + result);
        return result;
    }

    private static String[][] gridGenerator(int x1, int x2, int y1, int y2, int level) {
        String[][] render_grid = new String[y2-y1+1][x2-x1+1];
        for (int i = 0; i <= y2-y1; i++) {
            for (int j = 0; j <= x2-x1; j++) {
                render_grid[i][j] = pngFinder(j+x1, i+y1, level);
            }
        }
        return render_grid;
    }

    private static void GridPrinter(String[][] render_grid) {
        System.out.print("[");
        for (int i = 0; i < render_grid.length; i++) {
            System.out.print("[");
            for (int j = 0; j < render_grid[i].length; j++) {
                System.out.print(render_grid[i][j] + ";");
            }
            System.out.print("]");
        }
        System.out.print("]");
    }

    private static String pngFinder(int x, int y, int level) {
        StringBuilder sb = new StringBuilder("d");
        sb.append(level-1); // level 1: d0
        sb.append("_x");
        sb.append(x);
        sb.append("_y");
        sb.append(y);
        sb.append(".png");
//        System.out.println(sb.toString());
        return sb.toString();
    }

    public static void main(String[] args) {

        Map<String, Double> input = new HashMap<>();

        // local test input: test.html
//        input.put("lrlon", -122.24053369025242); // this set of parameters from test.html
//        input.put("ullon", -122.24163047377972);
//        input.put("w", 892.0);
//        input.put("h", 875.0);
//        input.put("ullat", 37.87655856892288);
//        input.put("lrlat", 37.87548268822065);
//        System.out.println("input is: " + input);

        // local test input: test1234.html
        input.put("lrlon", -122.20908713544797); // this set of parameters from test.html
        input.put("ullon", -122.3027284165759);
        input.put("w", 305.0);
        input.put("h", 300.0);
        input.put("ullat", 37.88708748276975);
        input.put("lrlat", 37.848731523430196);
        System.out.println(MapServer.ROOT_ULLON + "; " + MapServer.ROOT_LRLON + "; ");
        System.out.println(MapServer.ROOT_ULLAT + "; " + MapServer.ROOT_LRLAT + "; ");
        System.out.println("input is: " + input);

        // create a Rasterer object
        Rasterer rasterer = new Rasterer();
        Map<String, Object> queryResult = rasterer.getMapRaster(input);

        // test x1Num
//        int x1 = x1Num(-122.24163047377972,8);
//        int x2 = x2Num(-122.24053369025242, 8);
//        int y1 = y1Num(37.87655856892288, 8);
//        int y2 = y2Num(37.87548268822065, 8);

        // test render_grid
        String[][] result = (String[][]) queryResult.get("render_grid");
        System.out.println("query result is: ");
        GridPrinter(result);
    }
}
