package Model.ADT;

import Model.Statement.IStmt;

import java.util.Stack;

public interface MyIStack<T> {
    public T pop();
    public void push(T v);
    public boolean isEmpty();

    void clear();

    Stack<T> getStack();
    T top();
    MyIStack<T> clone();
}

