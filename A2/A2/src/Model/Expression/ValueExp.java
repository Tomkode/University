package Model.Expression;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.Value.Value;

public class ValueExp implements Exp {
    Value e;
    public ValueExp(Value e){this.e=e;}
    public Value eval(MyIDictionary<String,Value> tbl) throws MyException {return e;}
    public String toString(){return e.toString();}
    @Override
    public Exp deepcopy() {
        return new ValueExp(e.deepcopy());
    }
}
