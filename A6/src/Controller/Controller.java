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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Objects;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;
    private boolean displayFlag;

    public Controller(IRepository repo) {
        this.repo = repo;
        displayFlag = false;
    }

    private void displayPrgState(PrgState prg) throws MyException {
        if (displayFlag) {
            //System.out.println(prg.toString());
            this.repo.logPrgStateExec(prg);


        }
    }
    public List<PrgState> getPrgList(){
        return repo.getPrgList();
    }

    public void setDisplayFlag() {
        displayFlag = true;
    }

    public void clearDisplayFlag() {
        displayFlag = false;
    }

    public void add(PrgState prg) {
        repo.add(prg);
    }

    public void allStep() {
        //executor = Executors.newFixedThreadPool(2);
//remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0) {
            // garbage collector here
            for(PrgState prg : prgList) {
                if(prg != null)
                    prg.getHeap().setContent(GarbageCollector(
                            getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap()),
                            prg.getHeap().getContent()));
            }
            //TODO: optimize the garbage collector
            oneStepForAllPrg(prgList);
            prgList.forEach(prg -> {
                try {
                    repo.logPrgStateExec(prg);
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
            });
//remove the completed programs
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
//HERE the repository still contains at least one Completed Prg
// and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
//setPrgList of repository in order to change the repository

// update the repository state
    repo.setPrgList(prgList);
}
    public void oneStepForAllPrg(List<PrgState> prgList) {
//before the execution, print the PrgState List into the log file
        executor = Executors.newFixedThreadPool(2);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });
//RUN concurrently one step for each of the existing PrgStates
//-----------------------------------------------------------------------
//prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());
//start the execution of the callables
//it returns the list of new created PrgStates (namely threads)
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception ex) {
                            //here you can treat the possible
                            // exceptions thrown by statements
                            // execution, namely the green part
                            // from previous allStep method}
                            System.out.println(ex.getMessage());
                            return null;
                        }
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            //add the new created threads to the list of existing threads
            prgList.addAll(newPrgList);
            //------------------------------------------------------------------------------
            //after the execution, print the PrgState List into the log file
//            prgList.forEach(prg -> {
//                try {
//                    repo.logPrgStateExec(prg);
//                } catch (MyException e) {
//                    throw new RuntimeException(e);
//                }
//            });
        }
        //Save the current programs in the repositor
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repo.setPrgList(prgList);
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
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }
}
