# Extracting-n-most-popular-hashtags-in-social-media

In this project we implement a system to find or extract the ‘n’ most popular hashtags from social media platforms. Max Fibonacci heap is used as the priority queue data structure to achieve this. Max Fibonacci heap here provides better bounds for the increase key functionality – it has an amortized complexity of O(1). This system is implemented in Java and has two files ‘hashtagcounter.java” that has the node structure and the main module to execute the program and ‘FibonacciMaxHeap.java’ that has all the operations of a max Fibonacci heap. On execution we are required to give the input file as argument. If an second output file argument is given, then output is written to an output file else it is printed in the console. 


 To realize this implementation two main data structures are used – Max Fibonacci heap and hash tables. The program starts by accepting the input file argument. If a second output file argument is specified then the program writes the output to the name of the file specified. 

 The Fibonacci heap and hash table is then initialized. The input file is parsed line by line to get the respective hashtag(#) and the key. The hash map is checked to see if the word exists. If the word doesn’t exist, the insert() function is called to insert the record as a new node. If the word exists already then the increaseKey() function is called and the respective removal of the node from its parent if the value is maximum.  

 The removeMaxFreq() function is then called to call the Fibonacci heap functions explained in the previous section and print the respective output either in the console or output file according to the input given.

Instructions to run:

you can run the make file to generate the .class files or, javac hashtagcounter.java

java hashtagcounter <input_file.txt> <output_file.txt>
