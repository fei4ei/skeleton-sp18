package lab9;

import java.util.TreeSet;

public class AddToSet implements Action {
    TreeSet mySet = new TreeSet<>();

    @Override
    public void visit(BSTMap.Node T) {
        mySet.add(T.key);
    }
}
