

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

/*

Author: Ashwath Venkataraman 

*/

class Node {
	int degree = 0;
	boolean childCutValue = false; //Initialize childCut field to false
	int data;
	String key;
	Node next;
	Node prev;
	Node parent;
	Node child;
	Node(int index, String val) {		
		data = index;
		key = val;
		next = prev = this;
	}
}


//Main Method
public class hashtagcounter {
	public static void main(String[] args) throws Exception {
		File filename = new File(args[0]);
	    String opFile = "";
		try{
		opFile = args[1];
		FileWriter writer = new FileWriter(new File(opFile));
		writer.write("");
		writer.flush();
		writer.close();
		}
		catch(Exception e){System.out.println("No output file argument...Printing to console...\n");}

        if(opFile != null && opFile.isEmpty()){
			
			opFile = "";
			
		}
		
		FibonacciMaxHeap fibo = new FibonacciMaxHeap();
		fibo.hMap = new HashMap<String, Node>();
		String str;
		FileInputStream fstream = new FileInputStream(filename);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fstream));
		FileReader fileReader = new FileReader(filename);
        
		//Parse file according to input requirements
		while ((str = bufferedReader.readLine()) != null && !str.equals("STOP") && !str.equals("stop")) 
		{
			String[] inpArray = str.split(" "); // Split the spaces in each line to separate #words and their frequencies
			if (str.indexOf('#') != -1) //Find the "#"
			{
				inpArray[0] = inpArray[0].substring(1); //get the word
				int freq = Integer.parseInt(inpArray[1]); //get the freq
				if (fibo.hMap.containsKey(inpArray[0])) 
				{ 
			        //IncreaseKey
					fibo.increaseKey(fibo.hMap.get(inpArray[0]).next,freq);
			    } 
			    else 
			    {
					//InsertNode
					Node insert = fibo.insert(freq, inpArray[0]); 
					Node p = new Node(-1,null);
					p.next = insert;
					fibo.hMap.put(inpArray[0],p);
			    }
			} 
			else 
			{			
				Integer getMaxNum = Integer.parseInt(inpArray[0]); //Remove max frequency nodes
				fibo.removeMaxFreq(getMaxNum,opFile);
			}
		}
		bufferedReader.close();

	} 

}