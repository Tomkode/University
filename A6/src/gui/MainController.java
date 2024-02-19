package gui;

import Controller.Controller;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Model.PrgState;
import Model.ADT.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import javafx.util.Pair;

public class MainController {
    private Controller controller;

    @FXML
    private TableView<Pair<Integer, Value>> heapTable;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;

    @FXML
    private ListView<String> outputList;

    @FXML
    private ListView<String> fileList;

    @FXML
    private ListView<Integer> programStateList;

    @FXML
    private ListView<String> executionStackList;

    @FXML
    private TableView<Pair<String, Value>> symbolTable;

    @FXML
    private TableColumn<Pair<String, Value>, String> symVariableColumn;

    @FXML
    private TableColumn<Pair<String, Value>, String> symValueColumn;

    @FXML
    private TableView<Pair<String, IStmt>> procedureTable;

    @FXML
    private TableColumn<Pair<String, IStmt>, String> procedureHeaderColumn;

    @FXML
    private TableColumn<Pair<String, IStmt>, String> procedureBodyColumn;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    private Button oneStep;

    @FXML
    public void initialize() {
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        procedureHeaderColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        procedureBodyColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        oneStep.setOnAction(actionEvent -> {
            if(controller == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            boolean programStateLeft = false;
            for(PrgState prg : controller.getPrgList())
            {
                if(!prg.getStk().isEmpty())
                    programStateLeft = true;
            }
            if(!programStateLeft){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            try {
                controller.oneStepForAllPrg(controller.getPrgList());
                populate();
            } catch (Exception interpreterError) {
                Alert alert = new Alert(Alert.AlertType.ERROR, interpreterError.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        programStateList.setOnMouseClicked(mouseEvent -> populate());
    }

    private PrgState getCurrentProgramState(){
        if (controller.getPrgList().size() == 0)
            return null;
        int currentId = programStateList.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return controller.getPrgList().get(0);
        return controller.getPrgList().get(currentId);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    private void populate() {
        populateHeap();
        populateProgramStateIdentifiers();
        populateFileTable();
        populateOutput();
        populateSymbolTable();
        populateExecutionStack();
        populateProcedureTable();
    }

    private void populateHeap() {
        IHeap<Integer,Value> heap;
        if (controller.getPrgList().size() > 0)
            heap = controller.getPrgList().get(0).getHeap();
        else heap = new HeapTable();
        List<Pair<Integer, Value>> heapTableList = new ArrayList<>();
        for (Entry<Integer, Value> entry : heap.getContent().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTable.setItems(FXCollections.observableList(heapTableList));
        heapTable.refresh();
    }

    private void populateProgramStateIdentifiers() {
        List<PrgState> programStates = controller.getPrgList();
        var idList = programStates.stream().map(ps -> ps.id).collect(Collectors.toList());
        programStateList.setItems(FXCollections.observableList(idList));
        numberOfProgramStates.setText("" + programStates.size());
    }

    private void populateFileTable() {
        ArrayList<String> files;
        if (controller.getPrgList().size() > 0)
            files = new ArrayList<>(controller.getPrgList().get(0).getFileTable().keySet().stream().map(StringValue::getVal).toList());
        else files = new ArrayList<>();
        fileList.setItems(FXCollections.observableArrayList(files));
    }

    private void populateOutput() {
        MyIList<Value> output;
        if (controller.getPrgList().size() > 0)
            output = controller.getPrgList().get(0).getOutput();
        else output = new Model.ADT.MyList<Value>();
        outputList.setItems(FXCollections.observableList(output.getList().stream().map(Value::toString).toList()));
        outputList.refresh();
    }

    private void populateSymbolTable() {
        PrgState state = getCurrentProgramState();
        List<Pair<String, Value>> symbolTableList = new ArrayList<>();
        if (state != null)
            for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symbolTable.setItems(FXCollections.observableList(symbolTableList));
        symbolTable.refresh();
    }

    private void populateProcedureTable() {
        PrgState state = getCurrentProgramState();
        List<Pair<String, IStmt>> procedureTableList = new ArrayList<>();
        if (state != null)
            for (Entry<String, Pair<List<String>, IStmt>> entry : state.getProcTable().getContent().entrySet())
                procedureTableList.add(new Pair<>(entry.getKey() + entry.getValue().getKey().toString(), entry.getValue().getValue()));
        procedureTable.setItems(FXCollections.observableList(procedureTableList));
        procedureTable.refresh();
    }

    private void populateExecutionStack() {
        PrgState state = getCurrentProgramState();
        List<String> executionStackListAsString = new ArrayList<>();
        if (state != null)
            for(IStmt s : state.getStk().getStack()){
                executionStackListAsString.add(s.toString());
            }
        executionStackList.setItems(FXCollections.observableList(executionStackListAsString));
        executionStackList.refresh();
    }
}