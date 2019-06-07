import java.util.*;



public class node {
	public boolean leaf=false;							// true for leaf nodes
	List<Double> keys= new ArrayList<Double>();			// arraylist to store keys in each node
	List<List<String>> values= new ArrayList<List<String>>();		// arraylist of list of string to store values key.get(i) corresponds to values.get(i)'s list of values and hence storing duplicates in list
	List<node> children = new ArrayList<node>();				// list of class 'node' type to store children ---- key.get(i)'s left child is children.get(i)  and key.get(i)'s right child is children.get(i+1)
	public node left=null, right=null;							// left & right for double linked list, null for index nodes
	public node parent=null;									// parent pointer for each node
	//constructor
	public node(){
		leaf= false;
		left=null;
		right=null;
		parent=null;
	}	
}

