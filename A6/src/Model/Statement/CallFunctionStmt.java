package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.MyDictionary;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIProcTable;
import Model.Expression.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CallFunctionStmt implements IStmt{
    String functionName;
    List<Exp> arguments;
    public CallFunctionStmt(String fname, List<Exp> args){
        functionName = fname;
        arguments = args;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIProcTable<String, Pair<List<String>, IStmt> > procTable = state.getProcTable();
        Pair<List<String>, IStmt> funcInfo = procTable.lookup(functionName);
        List<String> variables = funcInfo.getKey();
        IStmt functionBody = funcInfo.getValue();
        IStmt functionProgram = funcInfo.getValue();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Integer,Value> heapTable = state.getHeap();
        List<Value> evaluatedVariables = new ArrayList<Value>();
        for( Exp exp : arguments){
            Value expValue = exp.eval(symTable, heapTable);
            evaluatedVariables.add(expValue);
        }
        MyIDictionary<String, Value> newSymTable = new MyDictionary();
        for(int i = 0 ; i < variables.size(); i++){
            String key = variables.get(i);
            Value value = evaluatedVariables.get(i);
            newSymTable.update(key,value);
        }
        state.getSymTablesStack().push(newSymTable);
        state.getStk().push(new ReturnStmt());
        state.getStk().push(functionBody);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new CallFunctionStmt(functionName, arguments);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
    @Override
    public String toString(){
        String result = functionName + "(";
        for(Exp exp : arguments){
            result += exp.toString() + ", ";
        }
        result += ")";
        return result;
    }
}
