package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class SleepStmt implements IStmt{
    Value number;
    public SleepStmt(Value nb){
        number = nb;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        int nb = ((IntValue)number).getVal();
        if(nb!= 0){
            state.getStk().push(new SleepStmt(new IntValue(nb - 1)));
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new SleepStmt(number.deepcopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(!number.getType().equals(new IntType()))
            throw new MyException("The number provided is not an integer!");
        return typeEnv;
    }
    @Override
    public String toString(){
        return "sleep(" + number + ")";
    }
}
