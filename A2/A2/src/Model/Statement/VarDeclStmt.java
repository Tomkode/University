package Model.Statement;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;
    public VarDeclStmt(String name, Type type){
        this.name=name;
        this.type=type;
    }
    public String toString(){
        return type.toString() + " " + name;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new MyException("Variable " + name + " already declared!");
        }
        else {
            symTable.update(name, type.defaultValue());
        }
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new VarDeclStmt(name, type);
    }
}
