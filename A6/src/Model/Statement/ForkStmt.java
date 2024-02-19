package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.ADT.MyStack;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public class ForkStmt implements IStmt{
    private IStmt statement;
    public ForkStmt(IStmt statement){
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>();
        return new PrgState(newStack, state.getSymTablesStack().clone(), state.getOutput(), state.getFileTable(), state.getHeap(), state.getProcTable(), statement);
    }
    @Override
    public String toString(){
        return "fork(" + this.statement.toString() + ")";
    }
    @Override
    public IStmt deepcopy() {
        return new ForkStmt(statement.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return statement.typecheck(typeEnv);
    }
}
