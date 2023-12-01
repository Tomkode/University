package Controller;

import Model.ADT.MyIStack;
import Model.ADT.IHeap;
import Model.MyException;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Objects;

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
            prg.getHeap().setContent(GarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap()),
                    prg.getHeap().getContent()));
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
    public Map<Integer, Value> GarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e-> {
                    return symTableAddr.contains(e.getKey());
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, IHeap<Integer, Value> heap){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .flatMap(v-> Stream.iterate( ((RefValue) v).getAddr(), Objects::nonNull, currentAddr ->  {
                    Value val = heap.lookup(currentAddr);
                    return (val instanceof RefValue) ? ((RefValue) val).getAddr() : null;
                }) )
                .distinct()
                .collect(Collectors.toList());
    }
}
