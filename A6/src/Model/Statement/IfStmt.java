package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Value.*;
import Model.Type.*;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;
    public IfStmt(Exp e, IStmt t, IStmt el) {exp=e; thenS=t;elseS=el;}
    public String toString(){ return "(IF("+ exp.toString()+") THEN(" +thenS.toString()+")ELSE("+elseS.toString()+"))";}
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getStk();
        MyIDictionary<String,Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        Value cond = exp.eval(symTable, heap);
        if(cond.getType().equals(new IntType())){
            throw new MyException("Condition is not a boolean!");
        }
        else{
            BoolValue v = (BoolValue) cond;
            if(v.getVal()){
                exeStack.push(thenS);
            }
            else{
                exeStack.push(elseS);
            }
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new IfStmt(exp.deepcopy(), thenS.deepcopy(), elseS.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
