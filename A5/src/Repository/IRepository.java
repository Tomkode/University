package Repository;

import Model.MyException;
import Model.PrgState;

import java.util.List;

public interface IRepository {
    void add(PrgState prg);
    void logPrgStateExec(PrgState prg) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> lst);
}
