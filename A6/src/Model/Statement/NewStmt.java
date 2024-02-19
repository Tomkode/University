package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.Expression.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class NewStmt implements IStmt{
    private String var_name;
    private Exp expression;
    public NewStmt(String var_name, Exp expression){
        this.var_name = var_name;
        this.expression = expression;

    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        Value var_value = symTable.lookup(var_name);
        Value expValue = expression.eval(symTable, heap);
        try {
            if (!(var_value.getType().equals(new RefType(expValue.getType()))))
                throw new MyException("Types don't match!");
            heap.put(expValue); // place the value in the heap
            int address = heap.getKeyByValue(expValue); // get the address of the value in the heap
            symTable.update(var_name, new RefValue(address, expValue.getType())); // update the symbol table
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new NewStmt(var_name, expression.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString(){
        return "new(" + var_name + ", " + expression.toString() + ")";
    }
}
