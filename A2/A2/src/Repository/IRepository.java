package Repository;

import Model.MyException;
import Model.PrgState;

public interface IRepository {
    PrgState getCrtPrg() throws MyException;
    void add(PrgState prg);
}
