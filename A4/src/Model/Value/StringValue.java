package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{
    private String val;
    public StringValue(String value){
        this.val = value;
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepcopy() {
        return new StringValue(this.val);
    }
    public String getVal(){
        return this.val;
    }
    @Override
    public boolean equals(Object ob){
        return this.val == ( (StringValue) ob).getVal();
    }
    @Override
    public String toString(){
        return this.val;
    }
}
