package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Expression.Exp;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    private Exp expression;
    public CloseRFileStmt(Exp exp){
        expression = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        Value expressionValue = expression.eval(symTable);
        if(!expressionValue.getType().equals(new StringType())){
            throw new MyException("The expression is not a string!");
        }
        BufferedReader file = fileTable.lookup((StringValue) expressionValue);
        try{
            file.close();
            fileTable.remove((StringValue) expressionValue);
        }
        catch (IOException ex){
            throw new MyException(ex.getMessage());
        }

        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new CloseRFileStmt(expression.deepcopy());
    }
    @Override
    public String toString(){
        return "closeRFile(" + expression.toString() + ")";
    }
}
