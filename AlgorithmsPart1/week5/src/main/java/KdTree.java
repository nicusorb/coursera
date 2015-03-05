public class KdTree {
    private SET<Point2D> point2DSET;

    // construct an empty set of points
    public KdTree() {
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
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return new Point2D(0.5, 0.7);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pointSET = new KdTree();
        pointSET.insert(new Point2D(0.1, 0.1));

        pointSET.draw();
    }
}