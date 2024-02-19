package Model.ADT;
import Model.ADT.MyIStack;
import Model.Value.Value;

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

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public T top() {
        return stack.peek();
    }

    @Override
    public MyIStack<T> clone() {
        MyIStack<T> newStack = new MyStack<>();
        for(T elem : stack){
            if(elem instanceof MyDictionary )
                newStack.push((T)((MyDictionary)elem).clone());
            else
                newStack.push(elem);
        }
        return newStack;
    }
// generic java library collection

    public String toString(){
        Stack<T> auxiliaryStack = (Stack<T>) this.stack.clone();
        String s = "";
        while(!auxiliaryStack.isEmpty()){
            T top = auxiliaryStack.pop();
            s += top.toString() + "\n";
        }
        return s;
    }

}
