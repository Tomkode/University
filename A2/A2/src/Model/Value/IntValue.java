package Model.Value;

import Model.Type.*;

public class IntValue implements Value {
    int val;
    public IntValue(int v){val=v;}
    public int getVal() {return val;}
    public String toString() {
        return Integer.toString(val);
    }
    public Type getType() { return new IntType();}

    @Override
    public Value deepcopy() {
        return new IntValue(val);
    }
}
