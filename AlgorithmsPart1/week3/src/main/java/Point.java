import java.util.Comparator;

/**
 * **********************************************************************
 * Name:
 * Email:
 * <p/>
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 * <p/>
 * Description: An immutable data type for points in the plane.
 * <p/>
 * ***********************************************************************
 */

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            double p1Slope = slopeTo(p1);
            double p2Slope = slopeTo(p2);
            if (p1Slope < p2Slope)
                return -1;
            else if (p1Slope > p2Slope)
                return 1;
            return 0;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (isVerticalPoint(that)) {
            return Double.POSITIVE_INFINITY;
        }
        return ((double)(that.y - this.y)) / ((double)(that.x - this.x));
    }

    private boolean isVerticalPoint(Point that) {
        return that.x - this.x == 0;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y)
            return -1;
        else if (this.y > that.y)
            return 1;
        else
            return this.x - that.x;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}