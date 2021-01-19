import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        int maxlength = 0;
        for (String str : asciis) {
            maxlength = Math.max(str.length(), maxlength);
        }
        String[] sorted = asciis.clone();
        for (int i = maxlength; i >= 0; i--) {
            sortHelperLSD(sorted, i);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // int[] charval = new int[asciis.length];
        Map<Integer, LinkedList<String>> track = new HashMap<>();
        for (int i = 0; i < asciis.length; i++) {
            int intval = (index < asciis[i].length()) ? (int) asciis[i].charAt(index) : -1;

            // charval[j] = (int) temp;
            if (!track.containsKey(intval)) {
                LinkedList<String> mylist = new LinkedList<String>();
                mylist.add(asciis[i]);
                track.put(intval, mylist);
            } else {
                track.get(intval).add(asciis[i]);
            }
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int val : track.keySet()) {
            max = max > val ? max : val;
            min = min < val ? min : val;
        }

        int[] counts = new int[max - min + 1];
        for (int val : track.keySet()) {
            for (String str : track.get(val)) {
                counts[val - min]++;
            }
        }

        int k = 0;
        for (int i = min; i < max + 1; i += 1) {
            for (int j = 0; j < counts[i - min]; j += 1, k += 1) {
                asciis[k] = track.get(i).get(j);
            }
        }

        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] arg) {
        String[] mys = new String[4];
        mys[0] = "z0";
        mys[1] = "sad";
        mys[2] = "a";
        mys[3] = "same";
        System.out.println(Arrays.toString(mys));
        String[] sorted = sort(mys);
        System.out.println(Arrays.toString(sorted));
        //sortHelperLSD(mys, 2);
        // System.out.println(Arrays.toString(mys));
    }
}
