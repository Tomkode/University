package View;
import Controller.*;
import Model.ADT.MyDictionary;
import Model.ADT.MyIDictionary;
import Model.ADT.TypeEnvDictionary;
import Model.MyException;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Type.Type;

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
            PrgState prg = ctr.getPrgList().get(0);
            MyIDictionary<String, Type> typeEnv = new TypeEnvDictionary();
            exe.typecheck(typeEnv);
            prg.changePrg(exe);
            ctr.allStep(); }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: "+e.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }
}
