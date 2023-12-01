package Model.Statement;

import Model.MyException;
import Model.ADT.MyIStack;
import Model.PrgState;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;
    public CompStmt(IStmt stmt1, IStmt stmt2){
        first=stmt1;
        snd=stmt2;
    }
    @Override
    public String toString() {
        return "("+first.toString() + ";" + snd.toString()+")";
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        stk.push(snd);
        stk.push(first);
        return state;}

    @Override
    public IStmt deepcopy() {
        return new CompStmt(first.deepcopy(), snd.deepcopy());
    }
}


