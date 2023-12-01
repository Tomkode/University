package Model.Expression;

import Model.ADT.IHeap;
import Model.MyException;
import Model.ADT.MyIDictionary;
import Model.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl, IHeap<Integer, Value> heap) throws MyException;
    Exp deepcopy();
}
