package lab9;

public class PrintKeys implements Action{

    @Override
    public void visit(BSTMap.Node T) {
        System.out.println(T.value);
    }
}
