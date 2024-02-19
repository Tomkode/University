package gui;

import Model.MyException;
import Model.Type.*;
import Repository.Repository;
import Controller.*;
import Model.ADT.*;
import Model.Expression.*;
import Model.PrgState;
import Model.Statement.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class SelectController {
    @FXML
    ListView<String> ProgramsView;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML
    public void initialize(){
        //this.ProgramsView.getItems().addAll(examples.stream().map(Object::toString).toList());
        ArrayList<IStmt> examples = new ArrayList<>();
        examples.add( new CompStmt( new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new BoolValue(false))), new PrintStmt(new
                        VarExp("v"))))   );
        /* int v;
         * v=false;
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
        ArrayList<Exp> sumlist = new ArrayList<>();
        sumlist.add(new ArithExp("*", new VarExp("v"), new ValueExp(new IntValue(10))));
        sumlist.add(new VarExp("w"));
        ArrayList<Exp> productlist = new ArrayList<>();
        productlist.add(new VarExp("v"));
        productlist.add(new VarExp("w"));
        ArrayList<Exp> sumlist2 = new ArrayList<>();
        sumlist2.add(new VarExp("v"));
        sumlist2.add(new VarExp("w"));
        examples.add(new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("w", new IntType()) ,
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))) ,
                                new CompStmt( new AssignStmt("w", new ValueExp(new IntValue(5))) ,
                                        new CompStmt(new CallFunctionStmt("sum" , sumlist)  ,
                                                new CompStmt(new PrintStmt(new VarExp("v")) ,
                                                        new CompStmt( new ForkStmt(new CallFunctionStmt("product", productlist)),
                                                                new ForkStmt(new CallFunctionStmt("sum", sumlist2))))))))));
        /*
        procedure sum(a,b) v=a+b;print(v)
procedure product(a,b) v=a*b;print(v)
and the main program is
v=2;w=5;call sum(v*10,w);print(v);
fork(call product(v,w);
fork(call sum(v,w)))
         */
        examples.add(new CompStmt( new VarDeclStmt("v", new IntType())  ,
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10)))  ,
                        new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))),
                                new CompStmt(new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("v")))) )   ,
                                new CompStmt(new SleepStmt(new IntValue(10))   , new PrintStmt(new ArithExp("*", new ValueExp(new IntValue(10)), new VarExp("v"))) )))));
        /*
        v=10;
(fork(v=v-1;v=v-1;print(v)); sleep(10);print(v*10)
         */
        ProgramsView.setItems(FXCollections.observableArrayList(examples.stream().map(Object::toString).toList()));
        ProgramsView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        int index = ProgramsView.getSelectionModel().getSelectedIndex();
                        if (index < 0)
                            return;
                        MyIStack<IStmt> stk = new MyStack<>();
                        MyIDictionary<String, Value> symTable = new MyDictionary();
                        MyIDictionary<StringValue, BufferedReader> fileTable = new FileTableDictionary();
                        MyIList<Value> out = new MyList<>();
                        MyIStack<MyIDictionary<String, Value>> symTablesStack = new MyStack<>();
                        IHeap<Integer, Value> heap = new HeapTable();
                        symTablesStack.push(symTable);
                        MyIProcTable<String, Pair<List<String>, IStmt>> procTable = new MyProcTable();
                        try {
                            procTable.update("sum", new Pair<>(List.of("a", "b"), new CompStmt(new VarDeclStmt("v", new IntType()),
                                    new CompStmt(new AssignStmt("v", new ArithExp("+", new VarExp("a"), new VarExp("b"))),
                                            new PrintStmt(new VarExp("v"))))));
                        } catch (MyException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            procTable.update("product", new Pair<>(List.of("a", "b"), new CompStmt(new VarDeclStmt("v", new IntType()),
                                    new CompStmt(new AssignStmt("v", new ArithExp("*", new VarExp("a"), new VarExp("b"))),
                                            new PrintStmt(new VarExp("v"))))));
                        } catch (MyException e) {
                            throw new RuntimeException(e);
                        }
                        PrgState state = new PrgState(stk, symTablesStack, out, fileTable, heap, procTable, examples.get(index));
                        IRepository repository = new Repository("Files/output.txt");
                        Controller controller = new Controller(repository);
                        controller.add(state);
                        try{
                            MyIDictionary<String, Type> typeEnv = new TypeEnvDictionary();
                            examples.get(index).typecheck(typeEnv);
                            mainController.setController(controller);
                        } catch (Exception interpreterError) {
                            interpreterError.getStackTrace();
                            Alert alert = new Alert(Alert.AlertType.ERROR, interpreterError.getMessage(), ButtonType.OK);
                            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            alert.showAndWait();
                        }
                    }
                }
            }
        });


    }
}
