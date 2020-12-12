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

        double[] pngres = pngRes();
        for (int i = 0; i < pngres.length; i++) {
            System.out.println("res at png level " + i + ": " + pngres[i]);
        }

        double userres = userRes(params);
        System.out.println("user res: " + userres);

        String[][] render_grid = new String[][]{{pngFinder(0, 0)}};
        results.put("render_grid", render_grid);
        return results;
    }

    private double[] pngRes() {
        // a factor of 2 better image resolution as the level goes higher
        double resl1 = Math.abs(-122.2998046875 + 122.2119140625) / 256; // level 1: d0_x0_y0.png
        double resl2 = Math.abs(-122.2998046875 + 122.255859375) / 256; // level 2: d1_x0_y0.png
        double resl3 = Math.abs(-122.2998046875 + 122.27783203125) / 256; // level 3: d2_x0_y0.png
        double resl4 = Math.abs(-122.2998046875 + 122.288818359375) / 256; // level 4: d3_x0_y0.png
        double resl5 = Math.abs(-122.2998046875 + 122.2943115234375) / 256; // level 5: d4_x0_y0.png
        double resl6 = Math.abs(-122.2998046875 + 122.29705810546875) / 256; // level 6: d5_x0_y0.png
        double resl7 = Math.abs(-122.2998046875 + 122.29843139648438) / 256; // level 7: d6_x0_y0.png
        double resl8 = Math.abs(-122.2998046875 + 122.29911804199219) / 256; // level 8: d7_x0_y0.png
        return new double[]{resl1, resl2, resl3, resl4, resl5, resl6, resl7, resl8};
    }

    private double userRes(Map<String, Double> params) {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double resolution = Math.abs(lrlon - ullon) / w;
        return resolution;
    }

    private String pngFinder(int x, int y) {
        StringBuilder sb = new StringBuilder("d");
        int level = 0;
        sb.append(level);
        sb.append("_x");
        sb.append(x);
        sb.append("_y");
        sb.append(y);
        sb.append(".png");
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, Double> input = new HashMap<>();
        input.put("lrlon", -122.24053369025242);
        input.put("ullon", -122.24163047377972);
        input.put("w", 892.0);
        input.put("h", 875.0);
        input.put("ullat", 37.87655856892288);
        input.put("lrlat", 37.87548268822065);
        System.out.println("input is: " + input);
        Rasterer rasterer = new Rasterer();
        Map<String, Object> queryResult = rasterer.getMapRaster(input);
        String[][] result = (String[][]) queryResult.get("render_grid");
        System.out.println("query result is: " + result[0][0]);

    }

}
