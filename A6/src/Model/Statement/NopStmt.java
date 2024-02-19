package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
