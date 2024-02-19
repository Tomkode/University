package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.Expression.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class WriteHeapStmt implements IStmt{
    private String varname;
    private Exp expression;
    public WriteHeapStmt(String varname, Exp expression){
        this.varname = varname;
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(!symtbl.isDefined(varname))
            throw new MyException("Variable is not defined!");
        Value varValue = symtbl.lookup(varname);
        if(!(varValue.getType() instanceof RefType))
            throw new MyException("The value is not a reference!");
        int varAddress = ((RefValue)varValue).getAddr();
        if(!heap.isDefined(varAddress))
            throw new MyException("The address isn't defined in the heap!");
        Value expValue = expression.eval(symtbl, heap);
        if(!expValue.getType().equals(((RefType)varValue.getType()).getInner()))
            throw new MyException("The expression type and the variable type are not the same!");
        heap.replace(varAddress, expValue);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WriteHeapStmt(varname, expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = expression.typecheck(typeEnv);
        Type typevar = typeEnv.lookup(varname);
        if(typevar instanceof RefType){
            RefType reft = (RefType) typevar;
            if(reft.getInner().equals(typexp))
                return typeEnv;
            else
                throw new MyException("The types of the expression and the variable are not the same!");
        }
        else
            throw new MyException("The variable is not a reference!");
    }

    @Override
    public String toString(){
        return "wH(" + varname + "," + expression.toString() + ")";
    }
}
