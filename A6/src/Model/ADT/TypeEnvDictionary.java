package Model.ADT;
import Model.MyException;
import Model.Type.*;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TypeEnvDictionary implements MyIDictionary<String, Type>{
    private Map<String, Type> dict;
    public TypeEnvDictionary(){
        dict = new HashMap<String,Type>();
    }
    @Override
    public boolean isDefined(String id) {
        return dict.get(id) != null;
    }

    @Override
    public Type lookup(String id) throws MyException {
        if(isDefined(id))
            return dict.get(id);
        else
            throw new MyException("Variable not defined!");
    }

    @Override
    public void update(String id, Type val) throws MyException{
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
    public void setContent(Map<String, Type> newDict){
        dict = (HashMap<String, Type>) newDict;
    }
    public Map<String, Type> getContent(){
        return dict;
    }

    @Override
    public MyIDictionary<String, Type> clone() {
        MyIDictionary<String,Type> newDict = new TypeEnvDictionary();
        for(String key : dict.keySet()){
            try {
                newDict.update(key, dict.get(key));
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        }
        return newDict;
    }

    @Override
    public Set<String> keySet() {
        return null;
    }
}
