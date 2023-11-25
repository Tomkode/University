package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.StringType;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt{
    private Exp expression;
    public OpenRFileStmt(Exp exp){
        expression = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value value;
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        value = expression.eval(symTable);
        if(! value.getType().equals(new StringType())){
            throw new MyException("The expression provided is not a string!");
        }
        StringValue strValue = (StringValue) value;
        try {
            BufferedReader file = new BufferedReader(new FileReader(strValue.getVal()));
            fileTable.update(strValue, file); // this will throw an exception if the file is already opened
        }
        catch(FileNotFoundException ex){
            throw new MyException(ex.getMessage());
        }
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new OpenRFileStmt(expression.deepcopy());
    }
    @Override
    public String toString(){
        return "openRFile(" + expression.toString() + ")";
    }
}
