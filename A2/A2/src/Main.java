// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import Controller.Controller;
import Model.ADT.*;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Statement.NopStmt;
import Model.Value.Value;
import Repository.Repository;
import View.View;

public class Main {
    public static void main(String[] args) {

        Controller cont = new Controller(new Repository());
        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> dict = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        PrgState prg = new PrgState(stk, dict, out, new NopStmt());
        cont.setDisplayFlag();
        cont.add(prg);
        View view = new View(cont);

        view.run();


    }
}
