package org.at.nfa.regexp;

/**
 * Created with IntelliJ IDEA.
 * User: Sashko
 * Date: 4/24/15
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Converter {

    public Node concat(Node s, Node t) {
        for(Transition transition : s.getTransitions().keySet()) {
            if(s.getTransitions().get(transition).isFinish()) {
                s.getTransitions().put(transition, t);
            }
        }
        return s;
    }

    public Node or(Node sStart, Node tStart) {
        Node finish = Node.build();
        finish.setFinish(true);

        Node s = getFirstTransition(sStart);
        s.getTransitions().put(new Transition(), finish);

        Node t = getFirstTransition(tStart);
        t.getTransitions().put(new Transition(), finish);

        Node start = Node.build();
        start.getTransitions().put(new Transition(), s);
        start.getTransitions().put(new Transition(), t);

        return start;
    }

    public Node star(Node node) {
        node.setStart(false);

        Node start = Node.build();
        start.setStart(true);
        Node finish = Node.build();
        finish.setFinish(true);
        start.getTransitions().put(new Transition(), finish);
        start.getTransitions().put(new Transition(), node);

        //Replace Finish Node
        for(Transition transition : node.getTransitions().keySet()) {
            if(node.getTransitions().get(transition).isFinish()) {
              //  s.getTransitions().put(transition, t);
                node.getTransitions().get(transition).setFinish(false);
                node.getTransitions().get(transition).getTransitions().put(new Transition(), finish);
                node.getTransitions().get(transition).getTransitions().put(new Transition(), node);
            }
        }

        return start;
    }

    private Node getFirstTransition(Node node) {
        for(Transition transition : node.getTransitions().keySet()) {
            return node.getTransitions().get(transition);
        }
        return null;
    }
}
