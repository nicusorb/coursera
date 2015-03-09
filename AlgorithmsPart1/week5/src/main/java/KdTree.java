import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private Node root;             // root of BST

    /**
     * Compares two points by x-coordinate.
     */
    private static final Comparator<Point2D> X_ORDER = new Comparator<Point2D>() {
        public int compare(Point2D p, Point2D q) {
            if (p.x() < q.x()) return -1;
            if (p.x() > q.x()) return +1;
            return 0;
        }
    };

    /**
     * Compares two points by y-coordinate.
     */
    private static final Comparator<Point2D> Y_ORDER = new Comparator<Point2D>() {
        public int compare(Point2D p, Point2D q) {
            if (p.y() < q.y()) return -1;
            if (p.y() > q.y()) return +1;
            return 0;
        }
    };

    private class Node {
        private Point2D key;           // sorted by key
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Point2D key, int N) {
            this.key = key;
            this.N = N;
        }
    }

    private class ColoredNode {
        private Node node;
        private boolean color;

        public ColoredNode(Node node, boolean color) {
            this.node = node;
            this.color = color;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() {
        return size(root);
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        put(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        return contains(root, p, X_ORDER);
    }

    // draw all points to standard draw
    public void draw() {
        Queue<ColoredNode> queue = new Queue<>();
        queue.enqueue(new ColoredNode(root, true));

        while (!queue.isEmpty()) {
            ColoredNode coloredNode = queue.dequeue();
            if (coloredNode.node == null)
                continue;
            drawNode(coloredNode);
            queue.enqueue(new ColoredNode(coloredNode.node.left, !coloredNode.color));
            queue.enqueue(new ColoredNode(coloredNode.node.right, !coloredNode.color));
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();
        List<Point2D> pointsInRectangle = new LinkedList<>();
        range(root, rect, X_ORDER, pointsInRectangle);
        return pointsInRectangle;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();
        if (isEmpty())
            return null;
        RectHV rectHV = new RectHV(0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
        return nearest(root, p, root.key, rectHV, X_ORDER);
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree pointSET = new KdTree();
        pointSET.insert(new Point2D(0.1, 0.1));

        pointSET.draw();
    }

    private boolean contains(Node node, Point2D key, Comparator<Point2D> comparator) {
        if (node == null)
            return false;
        if (node.key.compareTo(key) == 0)
            return true;
        Comparator<Point2D> newComparator = switchComparator(comparator);
        int cmp = comparator.compare(key, node.key);
        if (cmp < 0)
            return contains(node.left, key, newComparator);
        else if (cmp > 0)
            return contains(node.right, key, newComparator);
        else
            return false;
    }

    private void put(Point2D key) {
        root = put(root, key, X_ORDER);
    }

    private Node put(Node node, Point2D key, Comparator<Point2D> comparator) {
        if (node == null)
            return new Node(key, 1);
        if (node.key.compareTo(key) == 0)
            return node;

        Comparator<Point2D> newComparator = switchComparator(comparator);
        int cmp = comparator.compare(key, node.key);
        if (cmp < 0)
            node.left = put(node.left, key, newComparator);
        else if (cmp >= 0)
            node.right = put(node.right, key, newComparator);
        node.N = 1 + size(node.left) + size(node.right);
        return node;
    }

    private int size(Node node) {
        if (node == null)
            return 0;
        else
            return node.N;
    }

    private void drawNode(ColoredNode coloredNode) {
        StdDraw.setPenRadius(0.03);
        if (shouldBeRed(coloredNode.color))
            setPenRed();
        else
            setPenBlue();
        StdDraw.point(coloredNode.node.key.x(), coloredNode.node.key.y());
    }

    private boolean shouldBeRed(boolean color) {
        return color == true;
    }

    private void setPenRed() {
        StdDraw.setPenColor(255, 0, 0);
    }

    private void setPenBlue() {
        StdDraw.setPenColor(0, 0, 255);
    }

    private void range(Node node, RectHV rect, Comparator<Point2D> comparator, List<Point2D> points) {
        Comparator<Point2D> newComparator = switchComparator(comparator);
        if (node == null)
            return;
        if (rect.contains(node.key)) {
            points.add(node.key);
            searchBothChildren(node, rect, points, newComparator);
        } else if (comparator.equals(X_ORDER)) {
            searchPointsInRange(node, rect, points, newComparator, rect.xmin(), rect.xmax(), node.key.x());
        } else if (comparator.equals(Y_ORDER)) {
            searchPointsInRange(node, rect, points, newComparator, rect.ymin(), rect.ymax(), node.key.y());
        }
    }

    private Comparator<Point2D> switchComparator(Comparator<Point2D> comparator) {
        Comparator<Point2D> newComparator;
        if (comparator == X_ORDER)
            newComparator = Y_ORDER;
        else
            newComparator = X_ORDER;
        return newComparator;
    }

    private void searchBothChildren(Node node, RectHV rect, List<Point2D> points, Comparator<Point2D> newComparator) {
        range(node.left, rect, newComparator, points);
        range(node.right, rect, newComparator, points);
    }

    private void searchPointsInRange(Node node, RectHV rect, List<Point2D> points, Comparator<Point2D> newComparator,
                                     double leftBoundary, double rightBoundary, double value) {
        if (valueBetween(value, leftBoundary, rightBoundary)) {
            searchBothChildren(node, rect, points, newComparator);
        } else if (leftBoundary < value) {
            range(node.left, rect, newComparator, points);
        } else if (leftBoundary > value) {
            range(node.right, rect, newComparator, points);
        }
    }

    private boolean valueBetween(double value, double leftBoundary, double rightBoundary) {
        return leftBoundary < value && value < rightBoundary;
    }

    private Point2D nearest(Node node, Point2D p, Point2D currentNearest, RectHV rectHV, Comparator<Point2D> comparator) {
        if (node == null)
            return currentNearest;
        if (node.key.distanceSquaredTo(p) < currentNearest.distanceSquaredTo(p))
            currentNearest = node.key;

        if (comparator.equals(X_ORDER)) {
            double pointX = node.key.x();
            if (pointX > p.x()) {
                RectHV leftRect = new RectHV(rectHV.xmin(), rectHV.ymin(), pointX, rectHV.ymax());
                RectHV rightSubtreeRect = new RectHV(pointX, rectHV.ymin(), rectHV.xmax(), rectHV.ymax());

                return nearestLeft(node, p, currentNearest, comparator, leftRect, rightSubtreeRect);
            } else {
                RectHV rightRect = new RectHV(pointX, rectHV.ymin(), rectHV.xmax(), rectHV.ymax());
                RectHV leftSubtreeRect = new RectHV(rectHV.xmin(), rectHV.ymin(), pointX, rectHV.ymax());

                return nearestRight(node, p, currentNearest, comparator, rightRect, leftSubtreeRect);
            }
        } else { //Y_ORDER
            double pointY = node.key.y();
            if (pointY < p.y()) {
                RectHV leftRect = new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), pointY);
                RectHV rightSubtreeRect = new RectHV(rectHV.xmin(), pointY, rectHV.xmax(), rectHV.ymax());

                return nearestLeft(node, p, currentNearest, comparator, leftRect, rightSubtreeRect);
            } else {
                RectHV rightRect = new RectHV(rectHV.xmin(), pointY, rectHV.xmax(), rectHV.ymax());
                RectHV leftSubtreeRect = new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), pointY);

                return nearestRight(node, p, currentNearest, comparator, rightRect, leftSubtreeRect);
            }
        }
    }

    private Point2D nearestLeft(Node node, Point2D p, Point2D currentNearest, Comparator<Point2D> comparator, RectHV leftRect, RectHV rightSubtreeRect) {
        Point2D nearestLeft = nearest(node.left, p, currentNearest, leftRect, switchComparator(comparator));

        if (p.distanceSquaredTo(nearestLeft) <= rightSubtreeRect.distanceSquaredTo(p))
            return nearestLeft;
        else {
            //search right subtree
            return nearest(node.right, p, nearestLeft, rightSubtreeRect, switchComparator(comparator));
        }
    }

    private Point2D nearestRight(Node node, Point2D p, Point2D currentNearest, Comparator<Point2D> comparator, RectHV rightRect, RectHV leftSubtreeRect) {
        Point2D nearestRight = nearest(node.right, p, currentNearest, rightRect, switchComparator(comparator));

        if (p.distanceSquaredTo(nearestRight) <= leftSubtreeRect.distanceSquaredTo(p))
            return nearestRight;
        else {
            //search left subtree
            return nearest(node.left, p, nearestRight, leftSubtreeRect, switchComparator(comparator));
        }
    }
}