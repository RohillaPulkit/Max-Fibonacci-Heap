package CircularDoublyLinkedList;

public class LinkedList {

    private Node start, end;
    public int size;

    //Constructors
    public LinkedList(){
        this.start = null;
        this.end = null;
        this.size = 0;
    }

    //Getters
    public Node getStart() {
        return start;
    }

    //Operations

    //Insert the node into the linked list
    public void insert(FibonacciHeap.Node node){

        Node newNode = new Node(node , null, null);

        if (start == null){

            newNode.setNext(newNode);
            newNode.setPrev(newNode);
            start = newNode;
            end = newNode;
        }
        else
        {
            newNode.setPrev(end);
            end.setNext(newNode);

            newNode.setNext(start);
            start.setPrev(newNode);

            start = newNode;
        }

        size++;
    }

    //Delete the node from the linked list
    public void delete(FibonacciHeap.Node node){

        if(start == null) return;

        Node current = start;

        while (current != null && current.getRootNode() != node){

            current = current.getNext();
        }

        if(current == null) return;

        if(current == start){

            start = start.getNext();
        }

        if (current == end){

            end = end.getPrev();
        }

        Node prev = current.getPrev();
        Node next = current.getNext();

        prev.setNext(next);
        next.setPrev(prev);

        size--;
    }
}
