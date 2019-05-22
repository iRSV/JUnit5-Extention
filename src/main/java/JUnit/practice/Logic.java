package JUnit.practice;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class Logic {

    private final int listLength = 1000;

    public int getListLength() {
        return listLength;
    }

    public ArrayList fillArrayList(){
        File file = new File("/target/src/jmh/benchmark");
        file.mkdirs();
        ArrayList<String> arrayList = new ArrayList<String>();
        for(int i =1; i<=listLength;i++)
            arrayList.add("Some String");
        return arrayList;
    }

    public LinkedList fillLinkedList(){
        LinkedList<String> linkedList = new LinkedList<String>();
        for(int i =1; i<=listLength;i++)
            linkedList.add("Some String");
        return linkedList;
    }
}

