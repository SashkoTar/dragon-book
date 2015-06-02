package org.at.cig.nfa;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 5/18/15
 * Time: 9:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Runner {

    public static void main(String [] args) {
        int [] a = {1};
        Runner runner = new Runner();
        runner.increment(a);
        System.out.println(a[a.length - 1]);
    }

    public void increment(int[] i) {
        i[i.length - 1]++;
    }

}
