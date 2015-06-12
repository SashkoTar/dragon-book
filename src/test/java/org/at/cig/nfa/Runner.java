package org.at.cig.nfa;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 5/18/15
 * Time: 9:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Runner {

    public static void main2(String[] args) {
        List<Integer> ints = new ArrayList<Integer>();
        ints.add(1);
        ints.add(2);
        List<? extends Number> nums = ints;
      //  nums.add(3.14);  // compile-time error
        assert ints.toString().equals("[1, 2, 3.14]");  // uh oh!

    }
    public static void main3(String[] args) {
        List<Number> nums = new ArrayList<Number>();
        List<Integer> ints = Arrays.asList(1, 2);
        List<Double> dbls = Arrays.asList(2.78, 3.14);
        nums.addAll(ints);
        nums.addAll(dbls);
        assert nums.toString().equals("[1, 2, 2.78, 3.14]");
    }


    public static void main(String[] args) {
        List<Object> nums = new ArrayList<Object>();
        List<Integer> ints = Arrays.asList(1, 2);
        List<Double> dbls = Arrays.asList(2.78, 3.14);
        List<String> sts = Arrays.asList("str1");
        nums.addAll(ints);
        nums.addAll(dbls);
        nums.addAll(sts);
        nums.add("str2");
        assert nums.toString().equals("[1, 2, 2.78, 3.14, str1]");
    }
}
