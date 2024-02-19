package Model.ADT;

import Model.MyException;

import java.util.Map;
import java.util.Set;

public interface MyIProcTable<T, E> {

    boolean isDefined(T id);

    E lookup(T id) throws MyException;

    void update(T id, E val) throws MyException;

    void clear();
    void remove(T id) throws MyException;
    Map<T, E> getContent();
    MyIDictionary<T, E> clone();


}
