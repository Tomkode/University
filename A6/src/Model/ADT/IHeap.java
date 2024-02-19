package Model.ADT;

import Model.MyException;

import java.util.Map;

public interface IHeap<T, E> {
    boolean isDefined(T id);

    E lookup(T id);

    void put(E val) throws MyException;

    void clear();
    void remove(T id) throws MyException;
    T getKeyByValue(E value);

    void replace(T key, E value);
    void setContent(Map<T, E> newHeap);
    Map<T, E> getContent();
}
