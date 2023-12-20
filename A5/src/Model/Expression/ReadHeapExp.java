package Model.Expression;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.Type.RefType;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExp implements Exp{
    private Exp expression;
    public ReadHeapExp(Exp expression){
        this.expression = expression;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Value v = expression.eval(tbl, heap);
        if(v.getType() instanceof RefType)
        {
            RefValue ref = (RefValue) v;
            int address = ref.getAddr();
            return heap.lookup(address);
        }
        else
            throw new MyException("Expression is not a reference!");
    }

    @Override
    public Exp deepcopy() {
        return new ReadHeapExp(expression.deepcopy());
    }
    @Override
    public String toString(){
        return "rH(" + expression.toString() + ")";
    }
}
