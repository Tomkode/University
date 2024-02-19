package Main;// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import Controller.Controller;
import Model.ADT.*;
import Model.Expression.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.Repository;
import gui.GUI;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

//        Controller cont = new Controller(new Repository("Files/output.txt"));
//        MyIStack<IStmt> stk = new MyStack<>();
//        MyIDictionary<String, Value> dict = new MyDictionary();
//        MyIList<Value> out = new MyList<>();
//        MyIDictionary<StringValue, BufferedReader> fileTable = new FileTableDictionary();
//        IHeap<Integer, Value> heap = new HeapTable();
//        PrgState prg = new PrgState(stk, dict, out, fileTable, heap, new NopStmt());
//        cont.setDisplayFlag();
//        cont.add(prg);



      
//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1",examples.get(0),cont));
//        menu.addCommand(new RunExample("2",examples.get(1),cont));
//        menu.addCommand(new RunExample("3",examples.get(2),cont));
//        menu.addCommand(new RunExample("4",examples.get(3),cont));
//        menu.addCommand(new RunExample("5",examples.get(4),cont));
//        menu.addCommand(new RunExample("6",examples.get(5),cont));
//        menu.addCommand(new RunExample("7",examples.get(6),cont));
//        menu.addCommand(new RunExample("8",examples.get(7),cont));
//        menu.addCommand(new RunExample("9",examples.get(8),cont));
//        menu.addCommand(new RunExample("10",examples.get(9),cont));
//        menu.addCommand(new RunExample("11",examples.get(10),cont));
//        menu.addCommand(new RunExample("12",examples.get(11),cont));
//        menu.addCommand(new RunExample("13",examples.get(12),cont));
//        menu.addCommand(new RunExample("14",examples.get(13),cont));
//        menu.show();
        GUI.main(args);
    }
}
