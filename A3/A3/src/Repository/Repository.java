package Repository;

import Model.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Repository implements IRepository{
    private ArrayList<PrgState> prgList;
    private String logFilePath;
    public Repository(String logPath){
        logFilePath = logPath;
        prgList = new ArrayList<PrgState>();
        System.out.println("Current absolute path is: " + Paths.get(".").toAbsolutePath().normalize().toString());
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println("This is a new execution of the project -----------------------------------\n\n");
            logFile.flush();
            logFile.close();
        }
        catch(IOException ex){
            System.out.println("IOException occurred!\n" + ex.getMessage());
        }
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

    @Override
    public void logPrgStateExec() throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(getCrtPrg().toString());
            logFile.println("-------------------------------------------");
            logFile.close();
        }
        catch (IOException ex){
            throw new MyException("IOException occurred!\n" + ex.getMessage());
        }
    }
}
