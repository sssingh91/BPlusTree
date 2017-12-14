import java.util.*;
import java.util.*;
import java.io.File;

public class bplustree {
	private static int order;
	private static node root;
	private static int ch1, ch2 ;	
		
	// initialize with order 'm'
	public void initialise(int m)
	{
		order=m;
		ch1= (int)m/2-1;				// calculating 'ch1' for split node index during node split L= 0 to ch1 & R= ch2 to order, equivalent to ceil function
		ch2= ch1+1;						// calculating 'ch2' for split node index
	}
	
	
	public void insert(double key, String value)
	{
		insert_r(key, value, root);					//calling insert_r function to insert in tree rooted at 'root'
	}
	
	
	
	public String search(double key)
	{
		String s=search_r(key, root);				//calling search_r function to insert in tree rooted at 'root'
		return s;
	}

	
	// function for search with single key
	
	public String search_r(double key,  node r)
	{
		String s="";				// storing all values in String
		if(r.leaf==false){			// when root is not leaf
			int j=0;
			while(j<r.keys.size() && r.keys.get(j)< key) j++;		// traversing keys in node 
			if(j<r.keys.size() && r.keys.get(j)== key)				// since r.keys.get(j) == key, its right child (r.children.get(j+1)) is to be checked as it contains keys >= r.keys.get(i)
				r=inssearch(key, r.children.get(j+1));
			else
				r=inssearch(key, r.children.get(j));				//since r.keys.get(j) > key, its left child (r.children.get(j)) is to be checked as it contains keys < r.keys.get(i)
		}
		
		//  r is leaf node either after returning from 'inssearch' func or root was leaf
			int i=0;
			while(i<r.keys.size() && r.keys.get(i)< key) i++;
			if( i<r.keys.size() && r.keys.get(i) == key){			// if key found, storing the list of all the values
				for(int y=0; y< r.values.get(i).size(); y++){
					s+= r.values.get(i).get(y)+",";
				}	
			}
			else{													// else return null as key not found
				return "Null";
			}
			
			return s.substring(0,s.length()-1) ;
	}
	
	
	

	public String search(double key1, double key2)				// search range of keys
	{
		String s="";
		node r=root;											
		if(r.leaf==false){										// root is not leaf
			int j=0;
			while(j<r.keys.size() && r.keys.get(j)< key1) j++;	// traversing keys in node 
			if(j<r.keys.size() && r.keys.get(j)== key1)
				r=inssearch(key1, r.children.get(j+1));		// since r.keys.get(j) == key, its right child (r.children.get(j+1)) is to be checked as it contains keys >= r.keys.get(i)
			else
				r=inssearch(key1, r.children.get(j));		//since r.keys.get(j) == key, its left child (r.children.get(j)) is to be checked as it contains keys < r.keys.get(i)
		}
		
		// here r is leaf node either after returning from 'inssearch' func or root was leaf
			int i=0;
			while(i<r.keys.size() && r.keys.get(i)< key1) i++;
			double a= i<r.keys.size() ? r.keys.get(i): r.right.keys.get(0);		// checking corner case when i==r.keys.size()
			node h= i<r.keys.size() ? r: r.right;
			i=i<r.keys.size() ? i:0;
			r=h;
			node p=r;
			// traversing and storing values using doubly linkedlist of 'node'
			while(a<= key2){
				
				for(int y=0; y< r.values.get(i).size(); y++){			 
					s+="("+ r.keys.get(i)+ ","+r.values.get(i).get(y) + "),";		// storing key and values in string
				}
				if(i+1 >= r.keys.size() && r.right==null){
					break;
				}
				node k= i+1<r.keys.size() ? r: r.right;								//checking right node when all keys in node are checked
				i=i+1<r.keys.size() ? i+1:0;								
				r=k;			
				a= r.keys.get(i);
			}
		return s.length()<=0? "Null" : s.substring(0, s.length()-1);
	}
	
		
	
	public void insert_r(double key, String val, node r)
	{
		if(r==null)			// calling during 'first' insert when root==null   & initializing tree
		{
			node k= new node();		 // allocating memory to node
			k.leaf=true;
			k.parent=null;
			k.keys.add(key);			//adding key at '0' index of list
			List<String> o= new ArrayList<String>();
			o.add(val);						
			k.values.add(o);					//adding List<values for a key> at '0' index of list<List<>>
			root=k;								// root is initialized
			return;			
		}
		if(r.leaf==false)   				// when root is not leaf, exploring child nodes
		{
			while(r.leaf== false){  		// can be if		
				r= inssearch( key,r);   	// returns corresponding leaf node where key can be inserted
			}
		}
		// here r is leaf node either after returning from 'inssearch' func or root was leaf
		if(r.leaf==true)  
		{
			int i=0;
			while(i<r.keys.size() && r.keys.get(i)< key) i++;		// for finding index where key can be added
			if(i<r.keys.size() && r.keys.get(i)== key){			// when key already existed (duplicate case) , add value to the list  
				r.values.get(i).add(val);
			}
			else{												// when key was not found (non-duplicate case) , create a list and add value to the list
			r.keys.add(i,key);			
			List<String> o= new ArrayList<String>();
			o.add(val);
			r.values.add(i,o);
			}
			//after insert if number of keys in nodes < 'order' return as no need of splitting
			if(r.keys.size()<=order-1){
				return;
				}
			else{		//after insert if number of keys in nodes == order split the node/leaf
			node carryup= splitleaf(r);		// calling 'splitleaf' function
			}	
		}
		return;
	}
	
	
	
	// adding the key and left & right children to non-leaf node in case of split
	public node addontononleaf(double key, node l, node r, node par)  // can be void return
	 
	{
		if(par==null)						// when splitting root, root.parent is null, therefore creating new root
		{
			node temp= new node();			// allocating memory
			temp.leaf=false;
			temp.left=temp.right=temp.parent=null;
			temp.keys.add(key);
			temp.children.add(l);				// adding to nodes children at index '0'
			temp.children.add(r);				// adding to nodes children at index '1'
			root=temp;
			l.parent=r.parent=root;				// assigning parent pointer of left and right children to present node/root
			return temp;
		}
		else
		{										// when index node is not root, adding key after split
			int i=0;
			while(i<par.keys.size() && par.keys.get(i)<= key) i++;   // adding the key to the parent node at appropriate index
			par.keys.add(i,key);									
			par.children.remove(i);									// removing overflow child (which was split)
			par.children.add(i,l);   								// adding left child after split
			par.children.add(i+1,r);								// adding right children after split
			l.parent=r.parent=par;									// assigning parent pointer of left and right children to present node/root
			if(par.keys.size()<=order-1){							// if index node size < order after adding key	return						
				return par;
			}
			else{
				node k = splitnonleaf(par);							// if index node size == order after adding key, split index node by calling 'splitnonleaf' func
				return k;
			}
		}
	}
	
	
	
	
	// called when splitting index node as node does not contain values & left, right pointers are null
	public node splitnonleaf(node r){				// can be void return
		node a= new node();							// splitting index node into node 'a' & 'b'
		node b= new node();
		a.right=b.left=b.right= a.left= null;
		a.leaf=b.leaf=false;
		a.parent=b.parent= r.parent;  				// redundant
		
		int s= r.keys.size();
		a.keys= new ArrayList<>(r.keys.subList(0, ch2));		// assigning left 'keys' of index node to a 
		b.keys= new ArrayList<>(r.keys.subList(ch2+1, s));		// assigning left 'keys' of index node to a 
		
		a.children=new ArrayList<>(r.children.subList(0, ch2+1));		// assigning corresponding left children List to a.children
		b.children=new ArrayList<>(r.children.subList(ch2+1,s+1));		// assigning corresponding right children List to b.children
		for(int n=0; n<a.children.size(); n++){							// changing parent pointers of all the children nodes of node 'a' to 'a'
			a.children.get(n).parent=a;
		}
		for(int n=0; n<b.children.size(); n++){							// changing parent pointers of all the children nodes of node 'b' to 'b'
			b.children.get(n).parent=b;
		}
		node g=r.parent;
		double x=r.keys.get(ch2);
		
		node kk = addontononleaf(x, a,b, g);						// add the key after split to parent
		return kk;
	}

	
	
	// called when splitting leaf node as node contains values & doubly linked list to be maintained
	public node splitleaf(node r){							// can be void
		node a= new node();
		node b= new node();
		
		// manages doubly linked list pointer
		a.right= b;
		b.left=a;
		b.right= r.right;
		a.left=r.left;
		if(r.left!=null){
			r.left.right= a;
		}
		if(r.right!=null){
			r.right.left= b;
		}
		a.leaf=b.leaf=true;
		a.parent=b.parent= r.parent;  /// redundant
		int s= r.keys.size();
		a.keys= new ArrayList<>(r.keys.subList(0, ch2));			// assigning left 'keys' of leaf node to a 
		b.keys= new ArrayList<>(r.keys.subList(ch2, s));			// assigning right 'keys' of leaf node to b 
		a.values= new ArrayList<>(r.values.subList(0, ch2));		// assigning left 'values' of leaf node to a 
		b.values= new ArrayList<>(r.values.subList(ch2, s));		// assigning left 'values' of leaf node to a 
		node g= r.parent;
		r=null;
		node kk = addontononleaf(b.keys.get(0), a,b, g);		//  add the key after split to parent
		
		return kk;
	}


// for traversing tree from root to leaf for appropriate leaf-node
public node inssearch(double key,  node r)		
{
	if(r.leaf==false){
		int j=0;
		while(j<r.keys.size() && r.keys.get(j)< key) j++;			// traversing node
		if(j<r.keys.size() && r.keys.get(j)== key)
			r=inssearch(key, r.children.get(j+1));		// since r.keys.get(j) == key, its right child (r.children.get(j+1)) is to be checked as it contains keys >= r.keys.get(i)
		else
			r=inssearch(key, r.children.get(j));		// since r.keys.get(j) > key, its left child (r.children.get(j)) is to be checked as it contains keys < r.keys.get(i)
	}
		return r;										//return leaf-node
}


}
