package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.Expression.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private Exp expression;
    private String variable;
    public ReadFileStmt(Exp exp, String var){
        expression = exp;
        variable = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(!symTable.isDefined(variable)){
            throw new MyException("The variable is not defined!");
        }
        Value variableValue = symTable.lookup(variable);
        if(!variableValue.getType().equals(new IntType())){
            throw new MyException("The variable is not an integer!");
        }
        Value expValue = expression.eval(symTable, heap);
        if(!expValue.getType().equals(new StringType())){
            throw new MyException("The expression is not a string!");
        }
        BufferedReader file = fileTable.lookup((StringValue) expValue);
        try {
            String line = file.readLine();
            IntValue readValue;
            if(line == null){
                readValue = new IntValue(0);
            }
            else{
                readValue = new IntValue(Integer.parseInt(line));
            }
            symTable.update(variable, readValue);
        }
        catch (IOException ex){
            throw new MyException(ex.getMessage());
        }
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new ReadFileStmt(expression.deepcopy(), variable);
    }
    @Override
    public String toString(){
        return "readFile(" + expression.toString() + ", " + variable + ")";
    }
}
