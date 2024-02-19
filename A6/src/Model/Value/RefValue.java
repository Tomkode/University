package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value{
    int address;
    Type locationType;
    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }
    public int getAddr() {return address;}
    public Type getType() { return new RefType(locationType);}

    @Override
    public Value deepcopy() {
        return new RefValue(address, locationType);
    }
    @Override
    public boolean equals(Object ob){
        return this.address == ( (RefValue) ob).getAddr();
    }
    @Override
    public String toString(){
        return "(" + Integer.toString(address) + "," + locationType.toString() + ")";
    }
}
