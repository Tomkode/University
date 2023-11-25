package Model.ADT;
import Model.MyException;

import java.util.Hashtable;
public class MyDictionary<T,E> implements MyIDictionary<T,E> {
    private Hashtable<T,E> dict;
    public MyDictionary(){
        dict = new Hashtable<T,E>();
    }
    @Override
    public boolean isDefined(Object id) {
        return dict.get((T) id) != null;
    }

    @Override
    public E lookup(Object id) throws MyException {
        if(isDefined(id))
            return dict.get((T) id);
        else
            throw new MyException("Variable not defined!");
    }

    @Override
    public void update(Object id, Object val) {
        if(!isDefined(id))
            dict.put((T) id, (E) val);
        else
            dict.replace((T) id, (E) val);
    }

    public String toString(){
        String s = "";
        for(T key : dict.keySet()){
            s += key.toString() + " -> " + dict.get(key).toString() + "\n";
        }
        return s;
    }

    @Override
    public void clear() {
        dict.clear();
    }
}
