import java.util.Scanner;
public class What {
   public static void main(String[]args) {
   }
   
   public static int sortNums(int[] array, int n) {
      int low = 0;
      int mid = 0;
      int high = n - 1;
      if(n > array.length)
         throw new NullPointException();
      
      while(mid <= high) {
         if(array[mid] == 0)
            mid++;
         else if(array[mid] < 0) 
            swap(array[low++], array[mid++]);
         else
            swap(array[mid], array[high--]);
      }  
   }
}