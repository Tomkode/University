package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIList;
import Model.Expression.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.*;
public class PrintStmt implements IStmt {
    Exp exp;
    public PrintStmt(Exp e){exp=e;}
    public String toString(){
        return "print(" +exp.toString()+")";
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> output = state.getOutput();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTable, heap);
        output.add(val);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new PrintStmt(exp.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

}
