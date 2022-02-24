import java.util.ArrayList;

public class min_heap<E> {
    ArrayList<E> heap;

    min_heap(){
        heap = new ArrayList<>();
    }

    public void insert(E val, int priority){
        if(heap.size()==0) heap.add(val);
        int index = 0; 
        if(index*2+1>heap.size()){
            
        }
        while((index*2+2)>heap.size()){
            index++;
        }
        int iter = index*2+3-heap.size();
        while(iter-->0) heap.add(null);
        heap.set(index*2+1, val);
    }
}

class node<E>{
    int priority;
    E val;

    node(){
        priority = 0;
        val = null;
    }

    node(E value, int priority){
        this.priority = priority;
        this.val = value;
    }
}