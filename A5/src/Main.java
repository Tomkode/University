// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
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
import View.*;

import java.awt.color.CMMException;
import java.io.BufferedReader;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.Ref;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Controller cont = new Controller(new Repository("Files/output.txt"));
        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> dict = new MyDictionary();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new FileTableDictionary();
        IHeap<Integer, Value> heap = new HeapTable();
        PrgState prg = new PrgState(stk, dict, out, fileTable, heap, new NopStmt());
        cont.setDisplayFlag();
        cont.add(prg);

        ArrayList<IStmt> examples = new ArrayList<>();
        examples.add( new CompStmt( new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))))   );
        /* int v;
         * v=2;
         * Print(v)*/


        examples.add( new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+",new ValueExp(new IntValue(2)),new
                                ArithExp("*",new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp("+",new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))))  );
        /* int a;
         * int b;
         * a = 2 + 3 * 5;
         * b=a+1
         * print(b)*/

        examples.add( new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                VarExp("v"))))))  );
        /* bool a;
         * int v;
         * a=true;
         * (If a Then v=2 Else v=3);
         * Print(v)*/



        examples.add( new CompStmt( new VarDeclStmt("v", new IntType()), new CompStmt(
                new AssignStmt("v", new ValueExp(new IntValue(2))), new CompStmt(
                new VarDeclStmt("v", new IntType()), new PrintStmt(new VarExp("v"))
        )
        ) )  );

        /* int v;
        v = 2;
        int v;
        Print(v);
         */
        // ex 4 must throw exception

        examples.add(new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
                        new CompStmt(new VarDeclStmt("b", new BoolType()),
                                new CompStmt(new VarDeclStmt("c", new BoolType()),
                                        new CompStmt(new VarDeclStmt("d", new BoolType()),
                                                new CompStmt(new AssignStmt("b", new ValueExp(new BoolValue(true))),
                                                        new CompStmt(new AssignStmt("c", new ValueExp(new BoolValue(false))),
                                                                new CompStmt(new AssignStmt("d", new LogicExp("&&", new VarExp("b"), new VarExp("c"))),
                                                                        new CompStmt(new IfStmt(new VarExp("d"), new AssignStmt("a", new ArithExp("/",new ArithExp("*", new VarExp("a"), new ValueExp(new IntValue(15))) , new ValueExp(new IntValue(2)))), new AssignStmt("a", new ArithExp("*", new ArithExp("/", new VarExp("a"), new ValueExp(new IntValue(2))) , new ValueExp(new IntValue(10)))) ),
                                                                                new PrintStmt(new VarExp("a"))) ))))))))    );

        /*
        int a;
        a = 5;
        bool b;
        bool c;
        bool d;
        b = true;
        c = false;
        d = b && c;
        if (d) then a = a * 15 / 2 else a = a / 2 * 10;
        print(a);
         */

        examples.add( new CompStmt(  new VarDeclStmt("a", new BoolType()) ,
                new CompStmt(new AssignStmt("a", new RelExp("==", new ValueExp(new IntValue(0)), new ValueExp( new IntValue(0)))) ,
                        new PrintStmt(new VarExp("a"))) )  );
        /*
        bool a;
        a = 0 == 0;
        print(a);
         */
        examples.add(new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("Files/test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf")))))))))));
        /*
        string varf;
    varf="test.in";
    openRFile(varf);
    int varc;
    readFile(varf,varc);print(varc);
    readFile(varf,varc);print(varc)
    closeRFile(varf)
         */

        examples.add(new CompStmt(new VarDeclStmt("v", new IntType()) ,
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt( new WhileStmt( new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))))) ,
                               new PrintStmt(new VarExp("v")) ))));


        /*int v; v=4; (while (v>0) print(v);v=v-1);print(v)*/

        examples.add(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a"))))))));
        /*Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)*/


        examples.add( new CompStmt( new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp("+", new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5))))))))));

        /*
        Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
         */

        examples.add(new CompStmt( new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt( new NewStmt("v", new ValueExp(new IntValue(20))),
                         new CompStmt( new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                 new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                         new PrintStmt(new ArithExp("+", new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)))))))));


        /*
        Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5)
         */



        examples.add(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt( new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))));
        /*
        Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
         */
        examples.add(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt( new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt( new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new VarDeclStmt("b", new RefType(new RefType(new RefType(new IntType())))),
                                                new CompStmt(new NewStmt("b", new VarExp("a")),
                                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                                                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))
                                        )))));
        /*
        Ref int v;new(v,20);Ref Ref int a; new(a,v); Ref Ref Ref int b; new(b,a);new(v,30);new(a,v);print(rH(rH(a)))
         */



        examples.add(new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt( new ForkStmt( new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt( new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")) ,
                                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))))))  ,
                                                new CompStmt( new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a"))))))))));
        /*
        int v; Ref int a; v=10;new(a,22);
fork(wH(a,30);v=32;print(v);print(rH(a)));
print(v);print(rH(a))
         */

      
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",examples.get(0),cont));
        menu.addCommand(new RunExample("2",examples.get(1),cont));
        menu.addCommand(new RunExample("3",examples.get(2),cont));
        menu.addCommand(new RunExample("4",examples.get(3),cont));
        menu.addCommand(new RunExample("5",examples.get(4),cont));
        menu.addCommand(new RunExample("6",examples.get(5),cont));
        menu.addCommand(new RunExample("7",examples.get(6),cont));
        menu.addCommand(new RunExample("8",examples.get(7),cont));
        menu.addCommand(new RunExample("9",examples.get(8),cont));
        menu.addCommand(new RunExample("10",examples.get(9),cont));
        menu.addCommand(new RunExample("11",examples.get(10),cont));
        menu.addCommand(new RunExample("12",examples.get(11),cont));
        menu.addCommand(new RunExample("13",examples.get(12),cont));
        menu.addCommand(new RunExample("14",examples.get(13),cont));
        menu.show();

    }
}
