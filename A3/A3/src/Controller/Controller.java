package Controller;

import Model.ADT.MyIStack;
import Model.MyException;
import Model.PrgState;
import Model.Statement.IStmt;
import Repository.IRepository;

import java.io.*;

public class Controller {
    private IRepository repo;
    private boolean displayFlag;
    public Controller(IRepository repo){
        this.repo = repo;
        displayFlag = false;
    }
    private void displayPrgState(PrgState prg) throws MyException {
        if(displayFlag){
            //System.out.println(prg.toString());
                this.repo.logPrgStateExec();


        }
    }
    public void setDisplayFlag(){
        displayFlag = true;
    }
    public void clearDisplayFlag(){
        displayFlag = false;
    }
    public void add(PrgState prg){
        repo.add(prg);
    }
    public PrgState getCrtPrg() throws MyException{
        return repo.getCrtPrg();
    }
    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        if(stk.isEmpty())
            throw new MyException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }
    public void allStep() throws MyException{
        PrgState prg = repo.getCrtPrg(); // repo is the controller field of type MyRepoInterface
        displayPrgState(prg);
//here you can display the prg state
        while (!prg.getStk().isEmpty()){
            oneStep(prg);
//here you can display the prg state
            displayPrgState(prg);
        }
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter("Files/output.txt", true)));
            logFile.println("----------------Another Execution------------------");
            logFile.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
