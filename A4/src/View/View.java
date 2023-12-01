package View;
import Model.Statement.*;
import Model.Expression.*;
import Model.ADT.*;
import Model.Type.*;
import Model.Value.*;
import Model.*;
import Controller.*;
import Repository.*;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    private ArrayList<IStmt> examples;
    private int current;
    private Controller cont;
    public View(Controller cont){
        this.cont = cont;
        current = -1;
        examples = new ArrayList<>();
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
    }
    private void displayMenu(){
        System.out.println("1. Display the list of examples. ");
        System.out.println("2. Select a program. ");
        System.out.println("3. Run the program. ");
        System.out.println("4. Exit. ");

    }
    private void readAndProcessOperation(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter operation: ");
        int operation = Integer.parseInt(input.nextLine());
        switch (operation){
            case 1:
                for(IStmt prg : examples){
                    System.out.println(prg.toString());
                    System.out.println();
                }
                break;
            case 2:
                System.out.println("Enter the program's index: ");
                this.current = Integer.parseInt(input.nextLine());
                try{
                    PrgState current = cont.getCrtPrg();
                    current.changePrg(examples.get(this.current - 1));
                }
                catch (MyException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                if(current == -1)
                    System.out.println("No program is selected! ");
                else{
                    try{
                        cont.allStep();
                        this.current = -1;
                    }
                    catch (MyException e){
                        System.out.println(e.getMessage());
                    }

                }
                break;
            case 4:System.exit(0);
            default:
                System.out.println("Invalid operation. ");
                break;
        }
    }
    public void run(){
        while(true){
            displayMenu();
            readAndProcessOperation();
        }

    }
}
