// Zhuonan Sun, Section AD, TA: KIM,HYE IN 
// Project 1 Sound Blaster, PHASE A, 1/13/2014
// This program implements DStack, in a linked list, for Java double number
// It allows the user to check if the stack is empty, push, pop, and
// peek the stack

import java.util.EmptyStackException;

public class ListStack implements DStack {
   private Node front; // refers to the top position of the stack
   
   // creates an empty stack
   public ListStack() {
      front = null;
   }
   
   // returns true if the stack is empty
   public boolean isEmpty() {
      return front == null;
   }
   
   // adds an item to the top of the stack
   public void push(double d) {
      Node temp = front;
      front = new Node(d, temp);
   }
   
   // throws an EmptyStackException if the stack is empty
   // removes and returns the top item of the stack
   public double pop() {
      if(isEmpty())
         throw new EmptyStackException();
      else {
         double x = front.data;
         front = front.next;
         return x;
      }
   }
   
   // throws an EmptyStackException if the stack is empty
   // returns the top item of the stack
   public double peek() {
      if(isEmpty())
         throw new EmptyStackException();
      else
         return front.data;
   }
   
   // inner Node class used in linked list
   private class Node {
      private double data;    // refers to data of the node
      private Node next;      // refers to next node in the list
      
      // creates a new Node with given data
      public Node(double data) {
         this(data, null);
      }   
      
      // creates a new Node with given data and next Node
      public Node(double data, Node next) {
         this.data = data;
         this.next = next;
      }
   }
}