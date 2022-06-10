import java.util.ArrayList;
import java.util.Comparator;

public class Heap<E> {
    ArrayList<E> heap;
    Comparator<E> comparator;

    Heap(Comparator<E> comparator){
        heap = new ArrayList<>();
        this.comparator  = comparator;
    }

    public void insert(E val){
        heap.add(val);
        heapify();
    }

    private void swap(int ind1,int ind2){
        E temp = heap.get(ind1);
        heap.set(ind1, heap.get(ind2));
        heap.set(ind2, temp);
    }

    private void heapify(){
        int last_ind = heap.size()-1, parent_ind = (last_ind-1)/2;
        while(comparator.compare(heap.get(last_ind), heap.get(parent_ind))<0 && last_ind>0){
            swap(parent_ind, last_ind);
            last_ind = parent_ind;
            parent_ind = (last_ind-1)/2;
        }
    }

    // // 0 1 2 3 4 5 6 7
    // // n*2+1 left node
    // // n*2+2 right node
    // private void revHeapify(int root_ind){
    //     if(root_ind>heap.size())
    //         return;
    //     if(root_ind*2+1>heap.size())
    //         return;
    //     if(root_ind*2+2<heap.size()){

    //     }
    //     else{
            
    //     }
    // }

    public E peek(){
        return heap.get(0);
    }

    // public E removeMin(){
    //     E res = heap.get(0);
    //     heap.set(0, heap.get(heap.size()-1));
    //     heap.remove(heap.size()-1);
    //     revHeapify(0);
    //     return res;
    // }


    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        heap.insert(5);
        heap.insert(4);
        heap.insert(7);
        heap.insert(3);
        heap.insert(6);
        heap.insert(9);
        heap.insert(1);
        
        for(Integer num:heap.heap){
            System.out.print(num+" ");
        }
    }
}