package Model.Statement;

import Model.*;
import Model.ADT.IHeap;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIStack;
import Model.Expression.Exp;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }
    public String toString() {
        return id + "=" + exp.toString();
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, heap);
            Type typId = (symTbl.lookup(id)).getType();
            if ((val.getType()).equals(typId))
                symTbl.update(id, val);
            else
                throw new MyException("declared type of variable" + id + " and type of the assigned expression do not match");
        } else {
            throw new MyException("the used variable" + id + " was not declared before");
        }
        /* ... */
        return state;
    }

    @Override
    public IStmt deepcopy() {
        return new AssignStmt(id, exp.deepcopy());
    }
}

