import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree_v2 {
    private Node root;
    private int nbOfNodes = 0;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() {
        return nbOfNodes;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        put(p);
        nbOfNodes++;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        return get(p, true) != null;
    }

    private void put(Point2D key) {
        root = put(root, key, true);
    }

    private Node put(Node node, Point2D p, boolean shouldUseXCoordinate) {
        if (node == null) {
            Node n = new Node(p);
            if (shouldUseXCoordinate)
                n.rect = new RectHV(0, 0, p.x(), 1);
            else
                n.rect = new RectHV(0, 0, 1, p.y());
            return n;
        }
        if (shouldUseXCoordinate) {
            if (p.x() < node.p.x()) {
                node.lb = put(node.lb, p, false);
            } else {
                node.rt = put(node.rt, p, false);
            }
        } else {
            if (p.y() < node.p.y()) {
                node.lb = put(node.lb, p, true);
            } else {
                node.rt = put(node.rt, p, true);
            }
        }

        return node;
    }

    private Node get(Point2D p, boolean shouldUseXCoordinate) {
        return get(root, p, shouldUseXCoordinate);
    }

    private Node get(Node node, Point2D p, boolean shouldUseXCoordinate) {
        if (node == null)
            return null;
        if (p.compareTo(node.p) == 0)
            return node;

        if (shouldUseXCoordinate) {
            if (p.x() < node.p.x()) {
                return get(node.lb, p, false);
            } else {
                return get(node.rt, p, false);
            }
        } else {
            if (p.y() < node.p.y()) {
                return get(node.lb, p, true);
            } else {
                return get(node.rt, p, true);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(this.root, true);
    }

    private void draw(Node node, boolean shouldUseXCoordinate) {
        if (node == null)
            return;
        if (shouldUseXCoordinate)
            StdDraw.setPenColor(StdDraw.RED);
        else
            StdDraw.setPenColor(StdDraw.BLUE);

        node.rect.draw();
        draw(node.lb, !shouldUseXCoordinate);
        draw(node.rt, !shouldUseXCoordinate);
    }

//    // all points that are inside the rectangle
//    public Iterable<Point2D> range(RectHV rect) {
//        if (rect == null)
//            throw new NullPointerException();
//        List<Point2D> pointsInRectangle = new LinkedList<>();
//        range(root, rect, X_ORDER, pointsInRectangle);
//        return pointsInRectangle;
//    }

//    // a nearest neighbor in the set to point p; null if the set is empty
//    public Point2D nearest(Point2D p) {
//        if (p == null)
//            throw new NullPointerException();
//        if (isEmpty())
//            return null;
//        RectHV rectHV = new RectHV(0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
//        return nearest(root, p, root.p, rectHV, X_ORDER);
//    }
//
//    // unit testing of the methods (optional)
//    public static void main(String[] args) {
//        KdTree pointSET = new KdTree();
//        pointSET.insert(new Point2D(0.1, 0.1));
//
//        pointSET.draw();
//    }
//
//    private int size(Node node) {
//        if (node == null)
//            return 0;
//        else
//            return node.N;
//    }
//
//    private void drawNode(ColoredNode coloredNode) {
//        StdDraw.setPenRadius(0.03);
//        if (shouldBeRed(coloredNode.color))
//            setPenRed();
//        else
//            setPenBlue();
//        StdDraw.point(coloredNode.node.p.x(), coloredNode.node.p.y());
//    }
//
//    private boolean shouldBeRed(boolean color) {
//        return color == true;
//    }
//
//    private void setPenRed() {
//        StdDraw.setPenColor(255, 0, 0);
//    }
//
//    private void setPenBlue() {
//        StdDraw.setPenColor(0, 0, 255);
//    }
//
//    private void range(Node node, RectHV rect, Comparator<Point2D> comparator, List<Point2D> points) {
//        Comparator<Point2D> newComparator = switchComparator(comparator);
//        if (node == null)
//            return;
//        if (rect.contains(node.p)) {
//            points.add(node.p);
//            searchBothChildren(node, rect, points, newComparator);
//        } else if (comparator.equals(X_ORDER)) {
//            searchPointsInRange(node, rect, points, newComparator, rect.xmin(), rect.xmax(), node.p.x());
//        } else if (comparator.equals(Y_ORDER)) {
//            searchPointsInRange(node, rect, points, newComparator, rect.ymin(), rect.ymax(), node.p.y());
//        }
//    }
//
//    private Comparator<Point2D> switchComparator(Comparator<Point2D> comparator) {
//        Comparator<Point2D> newComparator;
//        if (comparator == X_ORDER)
//            newComparator = Y_ORDER;
//        else
//            newComparator = X_ORDER;
//        return newComparator;
//    }
//
//    private void searchBothChildren(Node node, RectHV rect, List<Point2D> points, Comparator<Point2D> newComparator) {
//        range(node.leftOrBottom, rect, newComparator, points);
//        range(node.rightOrTop, rect, newComparator, points);
//    }
//
//    private void searchPointsInRange(Node node, RectHV rect, List<Point2D> points, Comparator<Point2D> newComparator,
//                                     double leftBoundary, double rightBoundary, double value) {
//        if (valueBetween(value, leftBoundary, rightBoundary)) {
//            searchBothChildren(node, rect, points, newComparator);
//        } else if (leftBoundary < value) {
//            range(node.leftOrBottom, rect, newComparator, points);
//        } else if (leftBoundary > value) {
//            range(node.rightOrTop, rect, newComparator, points);
//        } else {
//            searchBothChildren(node, rect, points, newComparator);
//        }
//    }
//
//    private boolean valueBetween(double value, double leftBoundary, double rightBoundary) {
//        return leftBoundary < value && value < rightBoundary;
//    }
//
//    private Point2D nearest(Node node, Point2D p, Point2D currentNearest, RectHV rectHV, Comparator<Point2D> comparator) {
//        if (node == null)
//            return currentNearest;
//        if (node.p.distanceSquaredTo(p) < currentNearest.distanceSquaredTo(p))
//            currentNearest = node.p;
//
//        if (comparator.equals(X_ORDER)) {
//            double pointX = node.p.x();
//            if (pointX > p.x()) {
//                RectHV leftRect = new RectHV(rectHV.xmin(), rectHV.ymin(), pointX, rectHV.ymax());
//                RectHV rightSubtreeRect = new RectHV(pointX, rectHV.ymin(), rectHV.xmax(), rectHV.ymax());
//
//                return nearestLeft(node, p, currentNearest, comparator, leftRect, rightSubtreeRect);
//            } else {
//                RectHV rightRect = new RectHV(pointX, rectHV.ymin(), rectHV.xmax(), rectHV.ymax());
//                RectHV leftSubtreeRect = new RectHV(rectHV.xmin(), rectHV.ymin(), pointX, rectHV.ymax());
//
//                return nearestRight(node, p, currentNearest, comparator, rightRect, leftSubtreeRect);
//            }
//        } else { //Y_ORDER
//            double pointY = node.p.y();
//            if (pointY < p.y()) {
//                RectHV leftRect = new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), pointY);
//                RectHV rightSubtreeRect = new RectHV(rectHV.xmin(), pointY, rectHV.xmax(), rectHV.ymax());
//
//                return nearestLeft(node, p, currentNearest, comparator, leftRect, rightSubtreeRect);
//            } else {
//                RectHV rightRect = new RectHV(rectHV.xmin(), pointY, rectHV.xmax(), rectHV.ymax());
//                RectHV leftSubtreeRect = new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), pointY);
//
//                return nearestRight(node, p, currentNearest, comparator, rightRect, leftSubtreeRect);
//            }
//        }
//    }
//
//    private Point2D nearestLeft(Node node, Point2D p, Point2D currentNearest, Comparator<Point2D> comparator, RectHV leftRect, RectHV rightSubtreeRect) {
//        Point2D nearestLeft = nearest(node.leftOrBottom, p, currentNearest, leftRect, switchComparator(comparator));
//
//        if (p.distanceSquaredTo(nearestLeft) <= rightSubtreeRect.distanceSquaredTo(p))
//            return nearestLeft;
//        else {
//            //search right subtree
//            return nearest(node.rightOrTop, p, nearestLeft, rightSubtreeRect, switchComparator(comparator));
//        }
//    }
//
//    private Point2D nearestRight(Node node, Point2D p, Point2D currentNearest, Comparator<Point2D> comparator, RectHV rightRect, RectHV leftSubtreeRect) {
//        Point2D nearestRight = nearest(node.rightOrTop, p, currentNearest, rightRect, switchComparator(comparator));
//
//        if (p.distanceSquaredTo(nearestRight) <= leftSubtreeRect.distanceSquaredTo(p))
//            return nearestRight;
//        else {
//            //search left subtree
//            return nearest(node.leftOrBottom, p, nearestRight, leftSubtreeRect, switchComparator(comparator));
//        }
//    }
}