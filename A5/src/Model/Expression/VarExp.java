package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.Value.Value;

public class VarExp implements Exp {
    String id;
    public VarExp(String id){
        this.id=id;
    }
    public String toString(){
        return id;
    }
    public Value eval(MyIDictionary<String,Value> tbl, IHeap<Integer, Value> heap) throws MyException
    {
        return tbl.lookup(id);
    }

    @Override
    public Exp deepcopy() {
        return new VarExp(id);
    }
}
