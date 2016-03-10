import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

public class PointSET {
    private SET<Point2D> point2DSET;

    // construct an empty set of points
    public PointSET() {
        point2DSET = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return point2DSET.isEmpty();
    }

    // number of points in the set
    public int size() {
        return point2DSET.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        point2DSET.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return point2DSET.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point2D : point2DSET) {
            StdDraw.point(point2D.x(), point2D.y());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> pointsInRectangle = new LinkedList<>();
        for (Point2D p : point2DSET) {
            if (rect.contains(p))
                pointsInRectangle.add(p);
        }
        return pointsInRectangle;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D nearestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (Point2D point2D : point2DSET) {
            double distance = p.distanceTo(point2D);
            if (distance < minDistance) {
                minDistance = distance;
                nearestPoint = point2D;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        pointSET.insert(new Point2D(0.1, 0.1));

        pointSET.draw();
    }
}