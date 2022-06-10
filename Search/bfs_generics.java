package Search;
import java.util.*;

public class bfs_generics {
    public static void main(String[] args) {
        Node<int[]> n1 = new Node<>(new int[]{1,2,3});
        Node<int[]> n2 = new Node<>(new int[]{2,2,3});
        Node<int[]> n3 = new Node<>(new int[]{3,2,3});
        Node<int[]> n4 = new Node<>(new int[]{4,2,3});
     
        n1.neighbours.add(n2);
        n1.neighbours.add(n3);
        n2.neighbours.add(n4);
        n4.neighbours.add(n1);
        BFS<int[]> bfs = new BFS<>(new Comparator<int[]>() {
          public int compare(int[] o, int[] b){
            return o[0]-b[0];
          }
        });
        System.out.println((bfs.BFSt(n1, new int[]{2,3,4}).data));
    }
}

class Node<T>{
    T data;
    boolean status;
    Node(T data){
        this.data = data;
        this.status = false;
    }
    Node(T data, boolean status){
        this.data = data;
        this.status = status;
    }
    List<Node<T>> neighbours = new LinkedList<>();
}

class BFS<T>{
    Comparator<T> comparatorObj;

    BFS(Comparator<T> obj){
        comparatorObj = obj;
    }

    public Node<T> BFSt(Node<T> startNode,T searchVal){
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(startNode);
        Node<T> current = null;
        while(!queue.isEmpty()){
            current = queue.poll();
            current.status = true;
            if(comparatorObj.compare(current.data,searchVal)==0){
                return current;
            }
            Iterator<Node<T>> itr = current.neighbours.iterator();
            while(itr.hasNext()){
                Node<T> temp = itr.next();
                if(!temp.status) queue.add(temp);
            }
        }
        return current;
    }
}
