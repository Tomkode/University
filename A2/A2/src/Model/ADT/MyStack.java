package Model.ADT;
import Model.ADT.MyIStack;

import java.util.Stack;
public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack; //a field whose type CollectionType is an appropriate
    // generic Java collection type
    public MyStack(){
        stack = new Stack<T>();
    }
    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public void clear() {
        stack.clear();
    }
// generic java library collection

    public String toString(){
        String s = "";
        for(T e : stack){
            s += e.toString() + "\n";
        }
        return s;
    }

}
