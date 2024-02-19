package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;

public class ReturnStmt implements IStmt{
    public ReturnStmt(){
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getSymTablesStack().pop();
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new ReturnStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
    @Override
    public String toString(){
        return "return";
    }
}
