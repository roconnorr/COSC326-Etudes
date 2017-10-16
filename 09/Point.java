 import java.util.*;

 public class Point  {
    public Carpet carpet;
    public ArrayList<Point> neighbours;
    public int num;
    
    public Point (Carpet carpet, int num)  {
        this.carpet = carpet;
        neighbours = new ArrayList<Point>();
        this.num = num;
    }
    
    public void addNeighbour (Point neighbour)  {
        if (!neighbours.contains(neighbour)) {
            this.neighbours.add(neighbour);
        }
    }
    
    public String toString()  {
     return this.carpet.toString();
    }
    
    
}