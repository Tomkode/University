package Model.ADT;
import Model.MyException;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MyDictionary implements MyIDictionary<String, Value> {
    private Map<String,Value> dict;
    public MyDictionary(){
        dict = new HashMap<String,Value>();
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
    public void setContent(Map<String, Value> newDict){
        dict = (HashMap<String, Value>) newDict;
    }
    public Map<String, Value> getContent(){
        return dict;
    }

    @Override
    public MyIDictionary<String, Value> clone() {
        MyDictionary newDict = new MyDictionary();
        for(String key : dict.keySet()){
            try {
                newDict.update(key, dict.get(key).deepcopy());
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        }
        return newDict;
    }
}
