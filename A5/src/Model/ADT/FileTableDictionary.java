package Model.ADT;

import Model.MyException;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.Map;

public class FileTableDictionary implements MyIDictionary<StringValue, BufferedReader>{
    private Hashtable<StringValue, BufferedReader> dict;

    public FileTableDictionary(){
        dict = new Hashtable<StringValue, BufferedReader>();
    }
    @Override
    public boolean isDefined(StringValue id) {
        return dict.get(id) != null;
    }

    @Override
    public BufferedReader lookup(StringValue id) throws MyException {
        if(isDefined(id))
            return dict.get(id);
        else
            throw new MyException("File is not opened!");
    }
    @Override
    public void update(StringValue id, BufferedReader val) throws MyException{
        if(!isDefined(id))
            dict.put(id, val);
        else
            throw new MyException("The specified file is already opened!");
    }

    @Override
    public void clear() {
        this.dict.clear();
    }

    @Override
    public void remove(StringValue id) throws MyException {
        if(!isDefined(id))
            throw new MyException("File is not opened!");
        else
            dict.remove(id);
    }

    @Override
    public Map<StringValue, BufferedReader> getContent() {
        return dict;
    }

    @Override
    public MyIDictionary<StringValue, BufferedReader> clone() {
        return null;
    }

    public String toString(){
        String s = "";
        for(StringValue key : dict.keySet()){
            s += key.getVal() + " -> " + dict.get(key).toString() + "\n";
        }
        return s;
    }
}
