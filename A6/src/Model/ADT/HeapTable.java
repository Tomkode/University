package Model.ADT;

import Model.MyException;
import Model.Value.RefValue;
import Model.Value.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HeapTable implements IHeap<Integer, Value>{
    private Map<Integer, Value> dict;
    private int freeLocation;
    public HeapTable(){
        dict = new HashMap<Integer, Value>();
        freeLocation = 1;
    }
    @Override
    public boolean isDefined(Integer id) {
        return dict.containsKey(id);
    }

    @Override
    public Value lookup(Integer id){
        return dict.get(id);
    }

    @Override
    public void put(Value val) throws MyException {
        dict.put(freeLocation, val);
        freeLocation++;
    }


    @Override
    public void clear() {
        dict.clear();
    }

    @Override
    public void remove(Integer id) throws MyException {
        if(!isDefined(id))
            throw new MyException("Variable not defined!");
        else
            dict.remove(id);
    }

    @Override
    public Integer getKeyByValue(Value value) {
        for(Integer key : dict.keySet()){
            if(value == dict.get(key))
                return key;
        }
        return 0;
    }

    @Override
    public void replace(Integer key, Value value) {
        dict.put(key, value);
    }

    @Override
    public String toString(){
        String s = "";
        for(Integer key : dict.keySet()){
            s += key.toString() + " -> " + dict.get(key).toString() + "\n";
        }
        return s;
    }

    public void setContent(Map<Integer,Value> newDict){
        dict = (HashMap<Integer, Value>) newDict;
    }
    public Map<Integer,Value> getContent(){
        return dict;
    }
}
