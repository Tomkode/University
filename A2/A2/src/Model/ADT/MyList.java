package Model.ADT;
import Model.ADT.MyIList;

import java.util.ArrayList;
public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;
    public MyList(){
        list = new ArrayList<T>();
    }
    public void add(T v){
        list.add(v);
    }

    @Override
    public void clear() {
        list.clear();
    }

    public String toString(){
        String s = "";
        for(T e : list){
            s += e.toString() + "\n";
        }
        return s;
    }

}
