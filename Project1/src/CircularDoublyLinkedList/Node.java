package CircularDoublyLinkedList;

public class Node {

    private FibonacciHeap.Node rootNode;
    private Node next, prev;

    //Constructors
    Node(FibonacciHeap.Node rootNode, Node next, Node prev){

        this.rootNode = rootNode;
        this.next = next;
        this.prev = prev;
    }

    //Setters
    void setNext(Node next) {
        this.next = next;
    }

    void setPrev(Node prev) {
        this.prev = prev;
    }

    //Getters
    public FibonacciHeap.Node getRootNode() {
        return rootNode;
    }

    public Node getNext() {
        return next;
    }

    Node getPrev() {
        return prev;
    }
}
