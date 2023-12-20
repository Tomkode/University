package Model.Statement;

import Model.ADT.MyIStack;
import Model.ADT.MyStack;
import Model.MyException;
import Model.PrgState;

public class ForkStmt implements IStmt{
    private IStmt statement;
    public ForkStmt(IStmt statement){
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>();
        return new PrgState(newStack, state.getSymTable().clone(), state.getOutput(), state.getFileTable(), state.getHeap(), this.statement);
    }
    @Override
    public String toString(){
        return "fork(" + this.statement.toString() + ")";
    }
    @Override
    public IStmt deepcopy() {
        return new ForkStmt(statement.deepcopy());
    }
}
