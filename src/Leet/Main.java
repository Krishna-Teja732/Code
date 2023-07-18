package Leet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HashSet<List<Integer>> set = new HashSet<>();
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);

        set.add(list1);
        set.add(list2);

        System.out.println(set.size());

        list2.add(2);

        set.add(list2);

        System.out.println(set.size());
        set.forEach(System.out::println);
    }
}
