package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements Exp {
    Value e;
    public ValueExp(Value e){this.e=e;}
    public Value eval(MyIDictionary<String,Value> tbl, IHeap<Integer, Value> heap) throws MyException {return e;}
    public String toString(){return e.toString();}
    @Override
    public Exp deepcopy() {
        return new ValueExp(e.deepcopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
