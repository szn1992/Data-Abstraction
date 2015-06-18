// Zhuonan Sun, Section AD, TA: KIM,HYE IN 
// Project 1 Sound Blaster, PHASE A, 1/13/2014
// This program implements DStack, in an array, for Java double number
// It allows the user to check if the stack is empty, push, pop, and
// peek the stack

import java.util.EmptyStackException;

public class ArrayStack implements DStack {

   // refers to the position of the top in the stack
   private int back;          
   private double[] list;     // refers to the stack

   // creates a new stack
   public ArrayStack() {
      list = new double[10];
      back = -1;
   }
   
   // returns true if the stack is empty
   public boolean isEmpty() {
      return back == -1;      
   }
   
   // adds an item to the top of the stack
   // resizes the stack to twice larger if it is full
   public void push(double d) {
      if(back == list.length - 1) {
         double[] newlist = new double[list.length * 2];
         for(int i = 0; i < list.length; i++) 
            newlist[i] = list[i];
         list = newlist;
      }     
      back ++;
      list[back] = d;
   }
   
   // throws an EmptyStackException if the stack is empty
   // removes and returns the top item of the stack
   public double pop() {
      if(isEmpty())
         throw new EmptyStackException();
      else {
         double top = list[back];
         back --;
         return top;   
      }
   }
   
   // throws an EmptyStackException if the stack is empty
   // returns the top item of the stack
   public double peek() {
      if(isEmpty())
         throw new EmptyStackException();
      else 
         return list[back];
   }
}