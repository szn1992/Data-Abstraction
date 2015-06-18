1. Discussion board has been a useful tool to me on this project.

2. At the beginning, I tested my stack by creating a dat. file 
where I manually add in short data with some varieties. 
After everything working well, I tested it by using Reverse.java at command prompt

3. The scent of bitter almonds always reminded him of the fate of unrequited love.

4. No

5. the process of resizing follows this formula: 10*(2^t) = n, where n: number of lines, t: times resizings occur
n = 1 million, t = log_2(10^6/10) = 16.6 = 17 times
n = 1 billion, t = log_2(10^9/10) = 26.6 = 27 times
n = 1 trillion, t = log_2(10^12/10) = 36.5 = 37 times

6. push(double d){
      new Queue q;  //create a new queue
      q.enqueue(d); //add items to back of queue
  }
  
  pop double (){
      if isEmpty 
	throw exception
      else{
      	for(i = 0; i < queue.size - 1 ; i++) // put the front to the back until the original back comes to the front
            q.enqueue(q.dequeue());     
      }                                 
      return q.dequeue();                    
  }

7. Although push method would be fine, for pop method, it takes O(N) runtime for queue, 
which could take much larger time than array or list(O(1)) as N grows greater and greater.

8. what I encountered was some message after I compiled changed Reverse.java:

Note: GArrayStack.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.

Then I realized I did not specify the type parameter when I created generic stacks in Reverses.java
After specifying, adding <Double>, there was no longer any warning.

9. Really no much, since GStack and Dstack have similar formats, 
and they are also very similar for the ways they are called in Reverse.java.

10. didn't do it

11. reversing music is really cool stuff to do, so I enjoyed making this program, although generics is
a confusing idea to understand at the beginning.

12. None
