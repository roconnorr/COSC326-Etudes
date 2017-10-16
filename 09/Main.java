
import java.math.*;
import java.util.*;

public class Main {
 public static int toPlace = 0;
 public static Point startPiece;

 public static void main(String[] args) {
   
  Scanner scan = new Scanner(System.in);
  try{
  while (scan.hasNextLine()) {
	  String input = scan.nextLine();
	  if(!Character.isDigit(input.charAt(0))){
	  }else{		  
    int width = Integer.parseInt(input.split("\\s+")[0]); 
	int length = Integer.parseInt(input.split("\\s+")[1]);
	    System.out.println(width +" " + length);  
    if(width == 1 && length % 4  == 0){
     System.out.println((length/4));
    } else if(length ==1 && width % 4 == 0){
      System.out.println((width/4));
    }
    else if (((width * length) % 4 != 0) || (width * length == 0)) {
      System.out.println("0");
    } else {
      System.out.println(answer(width, length));
    
	}
  }
  }
 }catch(NoSuchElementException e){
 }
 }
   

 public static BigInteger answer(int width, int length) {
  int numCarpet = 0;
  Shape shape = new Shape();
  HashMap <String, Point> carpets = new HashMap<String, Point>();
  ArrayList<Point> list = new ArrayList<Point>();
  Carpet start;
  if (length < 4) {
   start = new Carpet(length, width);
  }else {
   start = new Carpet(4, width);
  }
  Point first = new Point(start, 0);
  startPiece = first;
  list.add(first);
  carpets.put(start.strVal(), first);
  while (!list.isEmpty()) {
   Point currPoint = list.remove(0);
   Carpet current = currPoint.carpet;
   int[] startPoint = current.getEmpty();
   for (int i = 0; i < 19; i++) {
    Carpet newCarpet = shape.place(current, startPoint, shape.shape[i]);
    if (newCarpet != null) {
     String key = newCarpet.strVal();
     if (carpets.containsKey(key)) {
      Point newPoint = carpets.get(key);
      currPoint.addNeighbour(newPoint);
     }else {
      numCarpet ++;
      Point newPoint = new Point(newCarpet, numCarpet);
      list.add(newPoint);
      carpets.put(key, newPoint);
      currPoint.addNeighbour(newPoint);
     }
    }
   }
  }
  toPlace = (width * length) / 4;
  BigInteger [][] table = new BigInteger[numCarpet+1][toPlace + 1];
  Point[] carpetsArray = new Point[numCarpet + 1];
  for (Point val : carpets.values()) {
   carpetsArray[val.num] = val;
  }
  for(int i = 0; i < numCarpet + 1; i++) {
   for (int j = 0; j < toPlace + 1; j++) {
    table[i][j] = new BigInteger("0");
   }
  }
  table[0][0] = new BigInteger("1");
  int row = 0;
  while (row < toPlace) {
   for (int i = 0; i < numCarpet; i++) {
    Point current = carpetsArray[i];
    for (Point n: current.neighbours) {
     table[n.num][row+1] = table[n.num][row+1].add(table[current.num][row]);
    }
   }
   row++;
  }
  return table[0][row];
 }

}












