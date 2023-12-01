package Model.Statement;

import Model.MyException;
import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepcopy();
}
