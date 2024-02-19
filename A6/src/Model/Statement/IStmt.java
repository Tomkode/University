package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepcopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException;
}
