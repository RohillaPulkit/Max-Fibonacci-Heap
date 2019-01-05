package FibonacciHeap;

import CircularDoublyLinkedList.LinkedList;

import java.util.Hashtable;

public class MaxFibonacciHeap {

    private LinkedList maxTrees;
    private Node max;

    //Constructor

    public MaxFibonacciHeap(){

        maxTrees = new LinkedList();
        max = null;
    }

    //Operations

    //Insert new node into the max trees
    public Node insert(int data){

        Node newNode = new Node(data);
        maxTrees.insert(newNode);

        if (max == null || data > max.getData()) max = newNode;

        return newNode;
    }

    //Removes max node from the tree
    public Node removeMax(){

        if(max == null || maxTrees.size == 0) return null;

        Node currentMax = max;

        //Extract Subtrees and add it along root
        extractSubtrees(max);

        //Remove Max
        maxTrees.delete(max);

        //Start Pairwise Combine
        pairWiseCombine();

        //Update Max after deletion
        updateMax();

        return currentMax;
    }

    //Increases the value of the node
    public void increaseKey(Node node, int newData){

        Node parent = node.getParent();

        int updatedData = node.getData() + newData;
        node.setData(updatedData);

        if (parent != null && node.getData() > parent.getData()){

            //Update left and right siblings
            extractNode(node);

            cascadeCut(parent);
        }

        if (updatedData > max.getData()) max = node;
    }

    //Private Methods

    //Initiates pair wise combine of the same degree trees
    private void pairWiseCombine(){

        if (maxTrees.size > 1){

            Hashtable<Integer, CircularDoublyLinkedList.Node> degreeTable = new Hashtable<>();

            CircularDoublyLinkedList.Node current = maxTrees.getStart();
            CircularDoublyLinkedList.Node next = current.getNext();

            degreeTable.put(current.getRootNode().getDegree(), current);

            int counter = maxTrees.size;

            while (counter > 1){

                int nextDegree = next.getRootNode().getDegree();

                combineHelper(degreeTable, nextDegree, next);

                next = next.getNext();

                counter--;
            }
        }
    }

    //Helper function for pair wise combine
    private void combineHelper(Hashtable<Integer, CircularDoublyLinkedList.Node> degreeTable, Integer degree, CircularDoublyLinkedList.Node node){

        if (degreeTable.containsKey(degree)){

            CircularDoublyLinkedList.Node tableTree = degreeTable.get(degree);

            Boolean isTableTreeLarger = (tableTree.getRootNode().getData() > node.getRootNode().getData());

            CircularDoublyLinkedList.Node firstTree =  isTableTreeLarger ? tableTree : node;
            CircularDoublyLinkedList.Node secondTree = isTableTreeLarger ? node : tableTree;

            Node parentNode = firstTree.getRootNode();
            Node currentChild = parentNode.getChild();
            Node childNode = secondTree.getRootNode();

            int newDegree = parentNode.getDegree() + 1;
            parentNode.setDegree(newDegree);

            if (currentChild != null){

                Node rightSibling = currentChild.getRightSibling();

                currentChild.setRightSibling(childNode);
                childNode.setLeftSibling(currentChild);
                childNode.setRightSibling(rightSibling);

                rightSibling.setLeftSibling(childNode);

            }
            else
            {
                childNode.setRightSibling(childNode);
                childNode.setLeftSibling(childNode);
            }

            parentNode.setChild(childNode);
            childNode.setParent(parentNode);

            degreeTable.remove(degree);

            maxTrees.delete(childNode);

            combineHelper(degreeTable, parentNode.getDegree(), firstTree);
        }
        else
        {
            degreeTable.put(degree, node);
        }

    }

    //Initiate cascade cut for the node or sets child cut flag to true
    private void cascadeCut(Node node){

        if (node.isChildCut()){

            Node parent = node.getParent();

            extractNode(node);

            cascadeCut(parent);
        }
        else
        {
            boolean isChildCut = node.getParent() != null;
            node.setChildCut(isChildCut); //Update Child Cut for Non root nodes
        }

        updateDegree(node);
        updateDegree(node.getParent());
    }

    //Extracts node from its parent and inserts it along the root
    private void extractNode(Node node){

        Node parent = node.getParent();
        //Update left and right siblings
        Node rightSibling = node.getRightSibling();
        Node leftSibling = node.getLeftSibling();

        rightSibling.setLeftSibling(leftSibling);
        leftSibling.setRightSibling(rightSibling);

        if (parent.getChild() == node){

            if (rightSibling != node){

                rightSibling.setParent(parent);
                parent.setChild(rightSibling);
            }
            else{

                parent.setChild(null);
            }

        }

        //Reset Fields
        node.setParent(null);
        node.setRightSibling(null);
        node.setLeftSibling(null);
        node.setChildCut(false);

        maxTrees.insert(node);

    }

    //Extracts subtrees of the node and inserts them along the root
    private void extractSubtrees(Node node){

        Node currentNode = node.getChild();
        Boolean isSameNode = false;

        while(currentNode != null && !isSameNode){

            Node rightSiblingNode = currentNode.getRightSibling();
            Node leftSiblingNode = currentNode.getLeftSibling();

            leftSiblingNode.setRightSibling(rightSiblingNode);
            rightSiblingNode.setLeftSibling(leftSiblingNode);

            currentNode.setParent(null);
            currentNode.setRightSibling(null);
            currentNode.setLeftSibling(null);
            currentNode.setChildCut(false);

            maxTrees.insert(currentNode);

            if (currentNode == rightSiblingNode) isSameNode = true;

            currentNode = rightSiblingNode;
        }
    }

    //Updates max pointer of the heap
    private void updateMax(){

        CircularDoublyLinkedList.Node current = maxTrees.getStart();
        CircularDoublyLinkedList.Node next = current.getNext();

        max = current.getRootNode();

        while (next != current){

            max = (max.getData() > next.getRootNode().getData()) ? max : next.getRootNode();

            next = next.getNext();
        }
    }

    //Updates degree of the parent node after cascade cut
    private void updateDegree(Node parent){

        if (parent != null){

            Node currentChild = parent.getChild();
            int maxDegree = -1;
            boolean isSameNode = false;

            while (currentChild != null && !isSameNode){

                maxDegree = Math.max(maxDegree, currentChild.getDegree());
                currentChild = currentChild.getRightSibling();

                if(currentChild == parent.getChild()) isSameNode = true;
            }

            parent.setDegree(maxDegree + 1);

            updateDegree(parent.getParent());
        }

    }

}
