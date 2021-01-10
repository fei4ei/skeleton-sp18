import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by hug, 4/9/2018. Basic tests for A* on the tiny graph.
 * This graph is so small you can draw it out by hand and visually inspect the results!
 */
public class TestRouterSmall {
    private static final String OSM_DB_PATH_TINY = "../library-sp18/data/berkeley-2018-small.osm.xml";
    private static GraphDB graphTiny;
    private static boolean initialized = false;

    @Before
    public void setUp() throws Exception {
        if (initialized) {
            return;
        }
        graphTiny = new GraphDB(OSM_DB_PATH_TINY);
        initialized = true;
    }

    @Test
    public void test22to66() {
        List<Long> actual = Router.shortestPath(graphTiny, -122.2525, 37.8689, -122.2537, 37.8685);
        System.out.println(actual);
    }
}
