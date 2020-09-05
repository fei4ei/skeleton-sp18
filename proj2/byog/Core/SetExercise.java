package byog.Core;

import java.util.*;

public class SetExercise {


    //Get elements from the Set, and put it into a list
    public static ArrayList<String> GetElement(HashSet<String> StringSet) {
        ArrayList<String> myList = new ArrayList<String>();
        for (String myString : StringSet) {
            System.out.println(myString);
            myList.add(myString);
        } return myList;
    }

    public static String RandomElement(HashSet<String> StringSet) {
        int size = StringSet.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(String myString : StringSet) {
            if (i == item) {
                return myString;
            }
            i++;
        } return null;
    }

    public static void main(String[] args) {
        HashSet<String> StringSet = new HashSet<>();
        StringSet.add("Hi");
        StringSet.add("bag");
        StringSet.add("cat");
        StringSet.add("you");
        StringSet.add("hop");
        StringSet.add("beep");
        System.out.println(RandomElement(StringSet));
        System.out.println(RandomElement(StringSet));
        System.out.println(RandomElement(StringSet));
        System.out.println(RandomElement(StringSet));
    }
}
