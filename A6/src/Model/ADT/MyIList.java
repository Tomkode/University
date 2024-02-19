package Model.ADT;

import java.util.List;

public interface MyIList<T> {
    void add(T v);

    void clear();
    List<T> getList();
}
