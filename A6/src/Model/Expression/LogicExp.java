package Model.Expression;

import Model.ADT.IHeap;
import Model.MyException;
import Model.ADT.MyIDictionary;
import Model.Type.*;
import Model.Value.*;
import Model.Value.Value;

import java.util.Objects;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    String op;
    public LogicExp(String op, Exp e1,Exp e2){
        this.e1=e1;
        this.e2=e2;
        this.op=op;
        //op = 1 -> and ; op = 2 -> or
    }
    public String toString(){
        return e1.toString()+op+e2.toString();
    }
    public Value eval(MyIDictionary<String,Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        Value v1,v2;
        v1= e1.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (Objects.equals(op, "&&")) return new BoolValue(n1 && n2);
                if (Objects.equals(op, "||")) return new BoolValue(n1 || n2);

            }else
                throw new MyException("second operand is not a boolean");
        }else
            throw new MyException("first operand is not a boolean");
        return null;
    }

    @Override
    public Exp deepcopy() {
        return new LogicExp(op, e1.deepcopy(),e2.deepcopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new MyException("second operand is not a boolean");
        }else
            throw new MyException("first operand is not a boolean");
    }
}
