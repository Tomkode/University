package Model.Expression;

import Model.ADT.MyIDictionary;
import Model.MyException;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class RelExp implements Exp{
    private Exp expression1;
    private Exp expression2;
    private String operator;
    public RelExp(String op, Exp e1, Exp e2){
        expression1 = e1;
        expression2 = e2;
        operator = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl) throws MyException {
        Value value1, value2;
        value1 = expression1.eval(tbl);
        value2 = expression2.eval(tbl);
        if(! value1.getType().equals(new IntType()))
            throw new MyException("Operand 1 isn't an integer");
        if(! value2.getType().equals(new IntType()))
            throw new MyException("Operand 2 isn't an integer");
        int intValue1, intValue2;
        intValue1 = ((IntValue) value1).getVal();
        intValue2 = ((IntValue) value2).getVal();
        switch(operator){
            case "<":
            {
                return new BoolValue(intValue1 < intValue2);
            }
            case "<=":
            {
                return new BoolValue(intValue1 <= intValue2);
            }
            case "==":
            {
                return new BoolValue(intValue1 == intValue2);
            }
            case "!=":
            {
                return new BoolValue(intValue1 != intValue2);
            }
            case ">":
            {
                return new BoolValue(intValue1 > intValue2);
            }
            case ">=":
            {
                return new BoolValue(intValue1 >= intValue2);
            }
            default:
                throw new MyException("Invalid operator!");
        }
    }

    @Override
    public Exp deepcopy() {
        return new RelExp(operator, expression1.deepcopy(), expression2.deepcopy());
    }
    @Override
    public String toString(){
        return "(" + expression1.toString() + operator + expression2.toString() + ")";
    }
}
