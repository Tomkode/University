package View;
import Controller.*;
import Model.MyException;
import Model.PrgState;
import Model.Statement.IStmt;

public class RunExample extends Command {
    private Controller ctr;
    private IStmt exe;
    public RunExample(String key,IStmt executed ,Controller ctr){
        super(key, executed.toString());
        this.ctr=ctr;
        exe = executed;

    }
    @Override
    public void execute() {
        try{
            PrgState prg = ctr.getCrtPrg();
            prg.changePrg(exe);
            ctr.allStep(); }
        catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }
}
