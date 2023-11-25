package Model;

import Model.ADT.FileTableDictionary;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIList;
import Model.ADT.MyIStack;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class PrgState{
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    IStmt originalProgram; //optional field, but good to have
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl,
                    MyIList<Value>
                            ot, MyIDictionary<StringValue, BufferedReader> filtbl , IStmt prg){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        fileTable = filtbl;
        originalProgram=prg.deepcopy();//recreate the entire original prg
        stk.push(prg);
    }

    public MyIStack<IStmt> getStk() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOutput(){
        return out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable(){
        return fileTable;
    }
    public void setStk(MyIStack<IStmt> stk) {
        this.exeStack = stk;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOutput(MyIList<Value> output) {
        this.out = output;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable){
        this.fileTable = fileTable;
    }
    public void changePrg(IStmt prg){
        this.originalProgram = prg.deepcopy();
        exeStack.clear();
        symTable.clear();
        out.clear();
        fileTable.clear();
        exeStack.push(originalProgram);
    }

    public String toString(){
        return "ExeStack:\n" + exeStack.toString() + "\nSymTable:\n" + symTable.toString() + "\nOutput:\n" + out.toString() + "\nFileTable:\n" +
               fileTable.toString() + "\n";
    }
}
