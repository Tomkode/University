package Model.Statement;

import Model.MyException;
import Model.PrgState;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new NopStmt();
    }
}
