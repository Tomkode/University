package Model.ADT;

import Model.MyException;

public interface MyIDictionary<T, E> {
    boolean isDefined(T id);

    E lookup(T id) throws MyException;

    void update(T id, E val) throws MyException;

    void clear();
    void remove(T id) throws MyException;
}
