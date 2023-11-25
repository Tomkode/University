package Model.Expression;

import Model.*;
import Model.ADT.MyIDictionary;
import Model.Value.*;
import Model.Type.*;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide
    public ArithExp(int op, Exp e1,Exp e2){
        this.e1=e1;
        this.e2=e2;
        this.op=op;
    }
    public String toString(){
        if(op==1) return e1.toString()+"+"+e2.toString();
        if(op==2) return e1.toString()+"-"+e2.toString();
        if(op==3) return e1.toString()+"*"+e2.toString();
        if(op==4) return e1.toString()+"/"+e2.toString();
        return "";
    }
    public Value eval(MyIDictionary<String,Value> tbl) throws MyException {
        Value v1,v2;
        v1= e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op==1) return new IntValue(n1+n2);
                if (op ==2) return new IntValue(n1-n2);
                if(op==3) return new IntValue(n1*n2);
                if(op==4)
                    if(n2==0) throw new MyException("division by zero");
                    else return new IntValue(n1/n2);
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
        return null;
    }

    @Override
    public Exp deepcopy() {
        return new ArithExp(op, e1.deepcopy(), e2.deepcopy());
    }
}
