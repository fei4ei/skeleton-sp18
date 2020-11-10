package hw3.hash;

import java.rmi.NoSuchObjectException;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
//        if (oomages == null) throw new NoSuchObjectException("list is null");
        double N = oomages.size();
        int[] freq = new int[M];
        for (Oomage item: oomages) {
            int i = (item.hashCode() & 0x7FFFFFFF) % M;
            freq[i] += 1;
        }
        for (int i = 0; i < M; i++) {
            if ((freq[i] < N/50) || (freq[i] > N/2.5)) return false;
        } return true;
    }
}
