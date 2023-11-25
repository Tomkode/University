package Model.ADT;
import Model.MyException;
import Model.Value.Value;

import java.util.Hashtable;
public class MyDictionary implements MyIDictionary<String, Value> {
    private Hashtable<String,Value> dict;
    public MyDictionary(){
        dict = new Hashtable<String,Value>();
    }
    @Override
    public boolean isDefined(String id) {
        return dict.get(id) != null;
    }

    @Override
    public Value lookup(String id) throws MyException {
        if(isDefined(id))
            return dict.get(id);
        else
            throw new MyException("Variable not defined!");
    }

    @Override
    public void update(String id, Value val) throws MyException{
        if(!isDefined(id))
            dict.put(id, val);
        else
            dict.replace(id, val);
    }

    public String toString(){
        String s = "";
        for(String key : dict.keySet()){
            s += key + " -> " + dict.get(key).toString() + "\n";
        }
        return s;
    }

    @Override
    public void clear() {
        dict.clear();
    }

    @Override
    public void remove(String id) throws MyException {
        if(!isDefined(id))
            throw new MyException("Variable not defined!");
        else
            dict.remove(id);
    }
}
