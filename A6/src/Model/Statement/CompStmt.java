package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Type.Type;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;
    public CompStmt(IStmt stmt1, IStmt stmt2){
        first=stmt1;
        snd=stmt2;
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;}
    @Override
    public String toString() {
        return "("+first.toString() + ";" + snd.toString()+")";
    }

    @Override
    public IStmt deepcopy() {
        return new CompStmt(first.deepcopy(), snd.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return snd.typecheck(first.typecheck(typeEnv));
    }
}


