package Model.ADT;

import Model.MyException;
import Model.Statement.IStmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.Value.Value;
import javafx.util.Pair;

public class MyProcTable implements MyIProcTable<String, Pair<List<String>, IStmt>  >{
    Map<String, Pair<List<String>, IStmt> > dict;
    public MyProcTable(){
        dict = new HashMap<>();
    }
    @Override
    public boolean isDefined(String id) {
        return dict.get(id) != null;
    }

    @Override
    public Pair<List<String>, IStmt> lookup(String id) throws MyException {
        if(isDefined(id))
            return dict.get(id);
        else
            throw new MyException("Variable not defined!");
    }

    @Override
    public void update(String id, Pair<List<String>, IStmt> val) throws MyException{
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
    public void setContent(Map<String, Pair<List<String>, IStmt>> newDict){
        dict = (HashMap<String, Pair<List<String>, IStmt>>) newDict;
    }
    public Map<String, Pair<List<String>, IStmt>> getContent(){
        return dict;
    }

    @Override
    public MyIDictionary<String, Pair<List<String>, IStmt>> clone() {
//        MyIDictionary<String,Value> newDict = new MyDictionary();
//        for(String key : dict.keySet()){
//            try {
//                newDict.update(key, dict.get(key).deepcopy());
//            } catch (MyException e) {
//                throw new RuntimeException(e);
//            }
//        }
        //return newDict;
        return null;
    }


}
