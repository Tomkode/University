package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type{
    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
    @Override
    public boolean equals(Object ob){
        return ob instanceof StringType;
    }
    public String toString(){
        return "string";
    }
}
