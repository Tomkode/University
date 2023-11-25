package Repository;

import Model.MyException;
import Model.PrgState;

import java.util.ArrayList;

public class Repository implements IRepository{
    private ArrayList<PrgState> prgList;
    public Repository(){
        prgList = new ArrayList<PrgState>();
    }
    @Override
    public PrgState getCrtPrg() throws MyException {
        int s = prgList.size();
        if(s == 0)
            throw new MyException("No program to execute!");
        return prgList.get(s - 1);
    }

    @Override
    public void add(PrgState prg) {
        prgList.add(prg);
    }
}
