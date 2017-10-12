/*
 * Program for reading a quilt design from stdin and printing it on the screen
 * (Java7+)
 * 
 * Written by Rory O'Connor
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Quilt {

    //Store layer data from stdin
    private static ArrayList<Layer> layers = new ArrayList<Layer>();

    /*
     * Creates a new quilt panel with layers read from stdin and shows the gui
     */
    private static void createAndShowGUI() {
        QuiltPanel quiltPanel = new QuiltPanel(layers);

        JFrame frame = new JFrame("Quilt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(quiltPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {
            //only read the line if it starts with a double
            if (scan.hasNextDouble()) {
                Double scale = scan.nextDouble();
                Integer r = scan.nextInt();
                Integer g = scan.nextInt();
                Integer b = scan.nextInt();
                Layer s = new Layer(scale, r, g, b);
                layers.add(s);
            } else {
                scan.nextLine();
            }
        }

        //Schedule a job for the event-dispatching thread:
        //creating and showing the GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

/*
 * Class takes arraylist of Layer data in its constructor and
 * generates its graphical representation
 */
class QuiltPanel extends JPanel {

    //Store window size
    private static final Integer WINDOW_SIZE = 500;

    //store the scaled size of a square (base size 200)
    private static Integer scaledSquareSize = 200;

    //store layer data
    private ArrayList<Layer> layers;

    //store each individual SquareToDraw object to be drawn
    private ArrayList<SquareToDraw> squaresToDraw
            = new ArrayList<SquareToDraw>();

    public QuiltPanel(ArrayList<Layer> layers) {
        this.layers = layers;

        //calculate the total size of the quilt design
        Integer totalSize = calculateQuiltSize();

        //if it is bigger than 80% of the window (400), reduce scale until it fits
        if (totalSize > 400) {
            while (totalSize > 400) {
                scaledSquareSize -= 25;
                totalSize = calculateQuiltSize();
            }
        //if it is smaller than 80% of the window, increase scale
        } else {
            while (totalSize < 400) {
                scaledSquareSize += 25;
                totalSize = calculateQuiltSize();
            }
        }

    }

    /*
     * returns the current total size of the quilt
     */
    private Integer calculateQuiltSize() {
        Integer totalSize = 0;
        for (Layer s : layers) {
            totalSize += (int) (s.scale * (double) scaledSquareSize);
        }
        return totalSize;
    }

    /*
     * set window size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_SIZE, WINDOW_SIZE);
    }

    /*
     * Calls method to populate squaresToDraw arraylist and draws each one
     */
    @Override
    protected void paintComponent(Graphics g) {
        //recursively create rectangles
        createSquare(WINDOW_SIZE / 2, WINDOW_SIZE / 2, 0);

        //sort squares to draw by their index so they are printed in the right order
        Collections.sort(squaresToDraw, new Comparator<SquareToDraw>() {
            @Override
            public int compare(SquareToDraw o1, SquareToDraw o2) {
                return o1.index - o2.index;
            }
        });

        //draw rectangles
        for (int i = 0; i < squaresToDraw.size(); i++) {
            g.setColor(squaresToDraw.get(i).color);
            g.fillRect(squaresToDraw.get(i).rect.x, squaresToDraw.get(i).rect.y,
                    squaresToDraw.get(i).rect.width, squaresToDraw.get(i).rect.width);
        }
    }

    /*
     * Recursive method to generate each rectangle in the pattern
     */
    private void createSquare(Integer x, Integer y, Integer index) {
        //stop recursion when we have reached the end of the layers array
        if (index > layers.size() - 1) {
            return;
        }

        //calculate current square size based on the base square size
        Integer squareSize
                = (int) (layers.get(index).scale * (double) scaledSquareSize);

        SquareToDraw rec = new SquareToDraw(
            new Rectangle(x - squareSize / 2, y - squareSize / 2, squareSize, squareSize), 
            new Color(layers.get(index).r,layers.get(index).g, layers.get(index).b), 
            index
        );

        squaresToDraw.add(rec);
        index++;

        //make 4 recursive calls - one for each corner of the square
        createSquare(x - squareSize / 2, y - squareSize / 2, index);
        createSquare(x + squareSize / 2, y - squareSize / 2, index);
        createSquare(x - squareSize / 2, y + squareSize / 2, index);
        createSquare(x + squareSize / 2, y + squareSize / 2, index);
    }
}

/*
 * Class to store data from stdin as layer objects
 */
class Layer {

    Double scale;
    Integer r;
    Integer g;
    Integer b;

    public Layer(Double scale, Integer r, Integer g, Integer b) {
        this.scale = scale;
        this.r = r;
        this.g = g;
        this.b = b;
    }
}

/*
 * Class to store a rectangle to be drawn with its index
 * this allows the squares to be sorted by their index for printing
 */
 class SquareToDraw {

    Rectangle rect;
    Color color;
    Integer index;

    public SquareToDraw(Rectangle rect, Color color, Integer index){
        this.rect = rect;
        this.color = color;
        this.index = index;
    }
 }
