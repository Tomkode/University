package Model.Value;

import Model.Type.*;

public class BoolValue implements Value {
    boolean val;
    public BoolValue(boolean v){val=v;}
    public boolean getVal() {return val;}
    public String toString() {
        if (val)
            return "true";
        else
            return "false";
    }
    public Type getType() { return new BoolType();}

    @Override
    public Value deepcopy() {
        return new BoolValue(val);
    }
}
