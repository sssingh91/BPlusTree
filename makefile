
JCC = javac

JFLAGS = -g

default: treesearch.class bplustree.class node.class

treesearch.class: treesearch.java bplustree.java node.java
	$(JCC) $(JFLAGS) treesearch.java bplustree.java node.java

bplustree.class: bplustree.java node.java
	$(JCC) $(JFLAGS) bplustree.java node.java

node.class: node.java
	$(JCC) $(JFLAGS) node.java

clean: 
	$(RM) *.class






