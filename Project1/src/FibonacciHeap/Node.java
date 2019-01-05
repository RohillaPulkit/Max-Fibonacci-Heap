package FibonacciHeap;

public class Node {

    private int degree;
    private int data;
    private boolean childCut;
    private Node child, leftSibling, rightSibling, parent;

    //Constructors

    Node(int data){

        this.data = data;
    }

    //Setters

    void setDegree(int degree) {
        this.degree = degree;
    }

    void setData(int data) {
        this.data = data;
    }

    void setChildCut(boolean childCut) {
        this.childCut = childCut;
    }

    void setChild(Node child) {
        this.child = child;
    }

    void setLeftSibling(Node leftSibling) {
        this.leftSibling = leftSibling;
    }

    void setRightSibling(Node rightSibling) {
        this.rightSibling = rightSibling;
    }

    void setParent(Node parent) {
        this.parent = parent;
    }

    //Getters

    int getDegree() {
        return degree;
    }

    public int getData() {
        return data;
    }

    boolean isChildCut() {
        return childCut;
    }

    Node getChild() {
        return child;
    }

    Node getLeftSibling() {
        return leftSibling;
    }

    Node getRightSibling() {
        return rightSibling;
    }

    Node getParent() {
        return parent;
    }

}
