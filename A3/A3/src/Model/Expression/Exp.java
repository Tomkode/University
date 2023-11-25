package Model.Expression;

import Model.MyException;
import Model.ADT.MyIDictionary;
import Model.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl) throws MyException;
    Exp deepcopy();
}
