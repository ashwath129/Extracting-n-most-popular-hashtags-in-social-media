
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;


/*

Author: Ashwath Venkataraman 

*/

public class FibonacciMaxHeap {
	
	HashMap<String,Node> hMap;
	int heapSize = 0;
	Node max = null; //Max Pointer
	Queue<Node> queueList = new LinkedList<Node>();
	
	/**
	 *Method to insert a node into the max fibonnaci heap with the value and hashtag key
	 */
	public Node insert(int value, String key) {
		Node node = new Node(value,key);
		max = mergeLists(max,node); //COmbine with tree root list
		++heapSize; // increase heapSize since we are inserting
		return node;
	}
	
	/**
	 * Returns the maximum element in the heap by dequeuing and doing a pairwise combine
	 */
	public Node removeMax() {
		
		List<Node> treeTab = new ArrayList<Node>(); //List table of the tree that has degrees of the sub trees for combine
		List<Node> nodeToVisit = new ArrayList<Node>(); //Nodes to be visited
		
		if (isEmpty())
		{
			throw new NoSuchElementException("Empty heap");
		}
		--heapSize; // decrease heapSize by since we are removing 
		Node maximumEl = max;
		if (max.next == max) 
		{ 
			max = null; //This is to remove maximum element from root list, if maximum is the only element
		} 
		else 
		{ //else we give max to the next node
			max.prev.next = max.next;
			max.next.prev = max.prev;
			max = max.next;
		}

		//Parent of the children of removed element which is max is changed to null
		if (maximumEl.child != null) 
		{
			Node current = maximumEl.child;
			do {
				current.parent = null;
				current = current.next;
			} while (current != maximumEl.child);
		}

		//Remove max children merged to the root list
		max = mergeLists(max,maximumEl.child);
		if (max == null) return maximumEl; // If the list becomes empty,return maximum element

        //Loop through the nodes and add current to the visited list
		for(Node current = max; nodeToVisit.isEmpty() || nodeToVisit.get(0) != current; current = current.next)
			nodeToVisit.add(current);

        //pairwise combine
		for (Node current : nodeToVisit) 
		{
			while (true) {
				while (current.degree >= treeTab.size()){
					treeTab.add(null); //Keep adding to subtree list
				}
                //Here we check degree of the trees until two of the same degree are found, we keep traversing
				if (treeTab.get(current.degree) == null) {
					treeTab.set(current.degree,current);
					break;
				}

				Node currTree = treeTab.get(current.degree);
				treeTab.set(current.degree, null); 
				Node min = (currTree.data < current.data) ? currTree : current;
				Node max = (currTree.data < current.data) ? current : currTree;
				min.next.prev = min.prev;
				min.prev.next = min.next;
				min.next = min.prev = min;
				max.child = mergeLists(max.child, min);
				min.parent = max;

				min.childCutValue = false;
				++max.degree;
				current = max;
			}
			if (current.data >= max.data)
			{
				max = current;
			}
		}
		return maximumEl;
	}

	/**
	 * Method that does the increase key operation in a Max Fibonacci heap for a node
	 */
	public void increaseKey(Node heapNode, int increaseValue) {
		heapNode.data = heapNode.data + increaseValue;
		if (heapNode.parent != null && heapNode.data >= heapNode.parent.data)
		{ //If new value is greater , then we cut it and add it to root list
			cut_Child(heapNode);
		}
		if (heapNode.data >= max.data)
		{ //If new value is greater than the max value,we change pointers
			max = heapNode;
		}
	}
		
	/**
	 * Method to combine lists
	 */
	private static Node mergeLists(Node a,Node b) {
		if (a == null && b == null) 
		{ 
			return null;
		} 
		else if (a != null && b == null) 
		{ 
			return a;
		} 
		else if (a == null && b != null) 
		{
			return b;
		} 
		else { 
			Node next_a = a.next; 
			a.next = b.next;
			a.next.prev = a;
			b.next = next_a;
			b.next.prev = b;
			if(a.data > b.data){ return a; }
			else 
				return b;
		}
	}
	
	
	/**
	 * Method to print output in file
	*/
	public void printInFile(int n,String opFile) throws Exception{
        FileWriter fwriter = new FileWriter(new File(opFile),true);
		PrintWriter pwriter = new PrintWriter(fwriter);		
		for (int i = 0; i < n; i++) {			
			Node currentMax = removeMax(); //Get the max frequency elem and write it to file
			pwriter.write(currentMax.key);
			if (i != n - 1) { pwriter.write(","); }
			queueList.add(currentMax);
		}
        pwriter.println();
		pwriter.flush();
		pwriter.close();
		updateResMap(queueList);
		
    }
	
	public void updateResMap(Queue<Node> queueList){
		while (!queueList.isEmpty()) 
		{
			Node elem = (Node) queueList.remove();
			Node result = insert(elem.data,elem.key);
			hMap.get(elem.key).next = result;
		}
	}
	
	/**
	 * Method to print output in console if output file param is not given
	*/
	public void printInConsole(int n) throws Exception{
	 for (int i = 0; i < n; i++) 
	 {
			Node currentMax = removeMax(); //Get the max frequency element and print
		    System.out.print(currentMax.key);
			if (i != n - 1) { System.out.print(","); }	
			queueList.add(currentMax);
	 }
        System.out.print("\n");
		updateResMap(queueList);	
	}
	
	/**
	 * Filter max elements in the heap
	 */
	public void removeMaxFreq(int n,String opFile) throws Exception {
		if(opFile.equals(null) || opFile.equals(""))
		{
			printInConsole(n);
		}
		else 
			printInFile(n,opFile);
	}
	
	/**
	 * Method for childCut functionality
	 */
	private void cut_Child(Node heapNode) {
		heapNode.childCutValue = false;
		if (heapNode.parent == null)
			return;

		if (heapNode.next != heapNode) 
		{ //Remove node from the sibling
			heapNode.next.prev = heapNode.prev;
			heapNode.prev.next = heapNode.next;
		}

		if (heapNode.parent.child == heapNode) 
		{ // this is to update child pointer of the parent
			if (heapNode.next != heapNode) {
				heapNode.parent.child = heapNode.next;
			}else {
				heapNode.parent.child = null;
			}
		}
		--heapNode.parent.degree; // reduce degree ofcourse
		heapNode.prev = heapNode.next = heapNode; //adding the cut node to rootlist
		max = mergeLists(max, heapNode);
		//We keep cutting up above recursively and mark the childcut values to true
		if (heapNode.parent.childCutValue)
			cut_Child(heapNode.parent);
		else
			heapNode.parent.childCutValue = true; //change childCut val to true
		heapNode.parent = null;
	}
	/**
	 * Utility Method to check if heap is empty
	 */
	public boolean isEmpty() {
		return max == null;
	}
	
	/**
	 * Utility Method to return maximum value in heap
	 */
	public Node max() {
		if (isEmpty())
			throw new NoSuchElementException("Empty Heap");
		return max;
	}

}