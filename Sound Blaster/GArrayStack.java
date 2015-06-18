// Zhuonan Sun, Section AD, TA: KIM,HYE IN 
// Project 1 Sound Blaster, PHASE B, 1/16/2014
// This program implements GStack, in an array, for Java generics
// It allows the user to check if the stack is empty, push, pop, and
// peek the stack

import java.util.EmptyStackException;

public class GArrayStack<T> implements GStack<T> {

   // refers to the position of the top in the stack
   private int back;
   private T[] list;    // refers to the stack

   // creates a new stack
   @SuppressWarnings("unchecked")
   public GArrayStack() {
      list = (T[])new Object[10];
      back = -1;
   }
   
   // returns true if the stack is empty
   public boolean isEmpty() {
      return back == -1;     
   }
   
   // adds an item to the top of the stack
   // resizes the stack to twice larger if it is full
   @SuppressWarnings("unchecked")
   public void push(T d) {
      if(back == list.length - 1) {
         T[] newlist = (T[])new Object[list.length * 2];
         for(int i = 0; i < list.length; i++) 
            newlist[i] = list[i];
         list = newlist;
      }     
      back ++;
      list[back] = d;
   }
   
   // throws an EmptyStackException if the stack is empty
   // removes and returns the top item of the stack
   public T pop() {
      if(isEmpty())
         throw new EmptyStackException();
      else {
         T top = list[back];
         back --;
         return top;   
      }
   }
      
   // throws an EmptyStackException if the stack is empty
   // returns the top item of the stack
   public T peek() {
      if(isEmpty())
         throw new EmptyStackException();
      else 
         return list[back];
   }
}