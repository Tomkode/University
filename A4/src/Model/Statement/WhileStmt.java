package Model.Statement;

import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStmt implements IStmt{
    private Exp expression;
    private IStmt statement;
    public WhileStmt(Exp expression, IStmt statement){
        this.expression = expression;
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symtbl = state.getSymTable();
        MyIStack<IStmt> stack = state.getStk();
        IHeap<Integer, Value> heap = state.getHeap();
        Value conditionValue = expression.eval(symtbl, heap);
        if(conditionValue.getType().equals(new BoolType())){
            if(((BoolValue)conditionValue).getVal()){
                stack.push(this);
                stack.push(this.statement);
            }
        }
        else
            throw new MyException("Condition is not a boolean");
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(this.expression.deepcopy(), this.statement.deepcopy());
    }
    @Override
    public String toString(){
        return "while(" + this.expression.toString() + "){" + this.statement.toString() + "}";
    }
}
