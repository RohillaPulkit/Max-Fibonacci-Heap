JFLAGS = -g

JC = javac -classpath . Project1/FibonacciHeap/*.java Project1/CircularDoublyLinkedList/*.java 

OUT_DIR =out

JVM= java 

RM = rm -rf

FILE=

.SUFFIXES: .java .class

.java.class:	
	$(JC) $(JFLAGS) $*.java

CLASSES = Project1/FibonacciHeap/Node.java \
	Project1/CircularDoublyLinkedList/Node.java \
	Project1/CircularDoublyLinkedList/LinkedList.java \
	Project1/FibonacciHeap/MaxFibonacciHeap.java \
	Project1/keywordcounter.java \
        
classes: $(CLASSES:.java=.class)

clean:	
	$(RM) $(JCLASSDIR)